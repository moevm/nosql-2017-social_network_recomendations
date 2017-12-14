package com.epam.vkneo4j.service;

import static com.epam.vkneo4j.util.Util.jsonArrayToStream;

import com.epam.vkneo4j.model.Group;
import com.epam.vkneo4j.model.Person;
import com.epam.vkneo4j.util.Util;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.util.Pair;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VKService implements SocialService {

  private PersonServiceImpl personService;
  private GroupServiceImpl groupService;

  @Autowired
  public VKService(PersonServiceImpl personService, GroupServiceImpl groupService) {
    this.personService = personService;
    this.groupService = groupService;
  }

  @Override
  public void getUserInfoWithFriendsAndTheirGroups(Principal principal) {

    UserActor actor = Util.getUserActor(principal);
    TransportClient transportClient = HttpTransportClient.getInstance();
    VkApiClient vk = new VkApiClient(transportClient);

    JsonArray userAndFriendsAndUserGroups = getUserInfoWithGroupsAndFriends(actor, vk);

    Person user = getUserInfoFromArray(userAndFriendsAndUserGroups);
    List<Person> friendsList = getFriendListFromArray(userAndFriendsAndUserGroups);
    List<Group> usersGroupsList = getUserGroupsFromArray(userAndFriendsAndUserGroups);
    Map<Integer, List<Group>> friendsListGroups = getFriendsGroups(actor, vk, friendsList);
    friendsListGroups.put(user.getVkId(), usersGroupsList);
    List<Group> uniqueGroups = getUniqueGroups(friendsListGroups);
    uniqueGroups.forEach(groupService::save);
    friendsList.forEach(person -> {
          if (friendsListGroups.containsKey(person.getVkId())) {
            friendsListGroups.get(person.getVkId())
                .forEach(group -> person.takesPartIn(groupService.findGroupByVkId(group.getVkId())));
          }
          personService.save(person);
          user.friendsWith(person);
        }
    );
    usersGroupsList
        .forEach(group -> user.takesPartIn(groupService.findGroupByVkId(group.getVkId())));
    personService.save(user);
  }

  private List<Group> getUniqueGroups(Map<Integer, List<Group>> friendsListGroups) {
    return friendsListGroups.values().stream()
        .flatMap(Collection::stream)
        .distinct()
        .collect(Collectors.toList());
  }

  private JsonArray getUserInfoWithGroupsAndFriends(UserActor actor, VkApiClient vk) {
    try {
      return vk
          .execute()
          .storageFunction(actor, "user_and_friends").unsafeParam("user", actor.getId())
          .execute()
          .getAsJsonArray();
    } catch (ApiException | ClientException e) {
      e.printStackTrace();
    }
    return new JsonArray();
  }

  private Person getUserInfoFromArray(JsonArray userAndFriendsAndUserGroups) {
    JsonObject userJsonObject = userAndFriendsAndUserGroups.get(0).getAsJsonObject();
    return new Person(
        userJsonObject.get("id").getAsInt(),
        userJsonObject.get("first_name").getAsString() + " " + userJsonObject.get("last_name")
            .getAsString(),
        userJsonObject.get("photo_200").getAsString()
    );
  }

  private Map<Integer, List<Group>> getFriendsGroups(UserActor actor, VkApiClient vk,
      List<Person> friendsList) {
    return ListUtils.partition(friendsList, 10)
        .stream()
        .map(people -> people.stream()
            .map(person -> person.getVkId().toString())
            .collect(Collectors.joining(","))
        )
        .map(
            list -> {
              try {
                Thread.sleep(1000);
                return getGroupsByVkIds(actor, vk, list);
              } catch (ApiException | ClientException | InterruptedException e) {
                e.printStackTrace();
              }
              return null;
            }
        )
        .flatMap(integerListMap -> integerListMap.entrySet().stream())
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  private Map<Integer, List<Group>> getGroupsByVkIds(UserActor actor, VkApiClient vk, String list)
      throws ApiException, ClientException {
    return jsonArrayToStream(
        vk
            .execute()
            .storageFunction(actor, "friends_and_groups")
            .unsafeParam("friends", list)
            .execute()
            .getAsJsonArray()
    )
        .filter(item -> !item.getAsJsonObject().get("items").isJsonNull())
        .map(
            item -> new Pair<>(
                item.getAsJsonObject().get("user_id").getAsInt(),
                jsonArrayToStream(item.getAsJsonObject().getAsJsonArray("items"))
                    .map(JsonElement::getAsJsonObject)
                    .map(friendGroups -> new Group(
                            friendGroups.get("id").getAsInt(),
                            friendGroups.get("name").getAsString(),
                            friendGroups.get("photo_200").getAsString()
                        )
                    )
                    .collect(Collectors.toList())))
        .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
  }

  private List<Group> getUserGroupsFromArray(JsonArray userAndFriendsAndUsersGroups) {
    return jsonArrayToStream(
        userAndFriendsAndUsersGroups.get(2).getAsJsonObject().getAsJsonArray("items"))
        .map(JsonElement::getAsJsonObject)
        .map(item -> new Group(
            item.get("id").getAsInt(),
            item.get("name").getAsString(),
            item.get("photo_200").getAsString()
        ))
        .collect(Collectors.toList());
  }

  private List<Person> getFriendListFromArray(JsonArray userAndFriendsAndUsersGroups) {
    return jsonArrayToStream(
        userAndFriendsAndUsersGroups.get(1).getAsJsonObject().getAsJsonArray("items"))
        .map(JsonElement::getAsJsonObject)
        .map(friend -> new Person(
            friend.get("id").getAsInt(),
            friend.get("first_name").getAsString() + " " + friend.get("last_name").getAsString(),
            friend.get("photo_200_orig").getAsString()
        ))
        .collect(Collectors.toList());
  }

}
