package com.epam.vkneo4j.service;

import com.epam.vkneo4j.model.Group;
import com.epam.vkneo4j.model.Person;
import com.epam.vkneo4j.queryresults.GraphDTO;
import com.epam.vkneo4j.queryresults.GroupAndUsers;
import com.epam.vkneo4j.queryresults.Link;
import com.epam.vkneo4j.queryresults.UserAndHisGroups;
import com.epam.vkneo4j.queryresults.UserDTO;
import com.epam.vkneo4j.repository.PersonRepository;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PersonServiceImpl implements PersonService {

  private PersonRepository personRepository;

  @Autowired
  public PersonServiceImpl(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  @Override
  public void save(Person person) {
    this.personRepository.save(person);
  }

  @Override
  public List<Person> getFriends(Integer vkId) {
    return this.personRepository.getFriends(vkId);
  }

  @Override
  public GraphDTO getGroupsByVkIds(List<Integer> ids, boolean onlyCommon) {
    if (onlyCommon) {
      return getGraphDTOForCommonGroups(ids);
    } else {
      return getGraphDTOForAllGroups(ids);
    }

  }

  private GraphDTO getGraphDTOForAllGroups(List<Integer> ids) {
    List<UserAndHisGroups> usersAndTheirGroups = this.personRepository.getGroupsByVkIds(ids);
    List<Person> friends = usersAndTheirGroups.stream()
        .map(UserAndHisGroups::getUser)
        .collect(Collectors.toList());
    List<Group> groups = usersAndTheirGroups.stream()
        .map(UserAndHisGroups::getGroups)
        .flatMap(Collection::stream)
        .distinct()
        .collect(Collectors.toList());
    List<Link> links = usersAndTheirGroups.stream()
        .map(userAndHisGroups -> {
          Person user = userAndHisGroups.getUser();
          return userAndHisGroups.getGroups().stream()
              .map(group -> new Link(user.getVkId(), group.getVkId()))
              .collect(Collectors.toList());
        }).flatMap(Collection::stream).collect(Collectors.toList());
    return new GraphDTO(friends, groups, links);
  }

  private GraphDTO getGraphDTOForCommonGroups(List<Integer> ids) {
    List<GroupAndUsers> groupAndUsers = this.personRepository.getCommonGroupsByVkIds(ids);
    List<Group> groups = groupAndUsers.stream()
        .map(GroupAndUsers::getGroup)
        .collect(Collectors.toList());
    List<Person> friends = groupAndUsers.stream()
        .map(GroupAndUsers::getUsers)
        .flatMap(Collection::stream)
        .distinct()
        .collect(Collectors.toList());
    List<Link> links = groupAndUsers.stream()
        .map(groupAndFriends -> {
          Group group = groupAndFriends.getGroup();
          return groupAndFriends.getUsers().stream()
              .map(person -> new Link(person.getVkId(), group.getVkId()))
              .collect(Collectors.toList());
        })
        .flatMap(Collection::stream).collect(Collectors.toList());
    return new GraphDTO(friends, groups, links);
  }

  @Override
  public List<UserDTO> getAllUsersInfo() {
    return this.personRepository.getAllUsersInfo();
  }

  @Override
  public Person getByVkId(Integer vkId) {
    return this.personRepository.getByVkId(vkId);
  }
}
