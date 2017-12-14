package com.epam.vkneo4j.repository;

import com.epam.vkneo4j.model.Person;
import com.epam.vkneo4j.queryresults.GroupAndUsers;
import com.epam.vkneo4j.queryresults.UserAndHisGroups;
import com.epam.vkneo4j.queryresults.UserDTO;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.map.SingletonMap;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface PersonRepository extends GraphRepository<Person> {


  @Query("MATCH (p:Person)-[:FRIEND]-(p1:Person) "
      + "WHERE p.vkId={0} "
      + "RETURN p1")
  List<Person> getFriends(Integer vkId);

  @Query("MATCH (p:Person), (p)-[:TAKES_PART_IN]-(g:Group) "
      + "WHERE p.vkId in {0} "
      + "RETURN p as user, COLLECT(g) as groups")
  List<UserAndHisGroups> getGroupsByVkIds(List<Integer> ids);


  @Query("MATCH (g:Group), (p:Person)-[:TAKES_PART_IN]-(g:Group) "
      + "WHERE p.vkId in {0} "
      + "WITH g as group, COLLECT(p) as users "
      + "WHERE SIZE(users) > 1 "
      + "RETURN group, users")
  List<GroupAndUsers> getCommonGroupsByVkIds(List<Integer> ids);


  @Query("MATCH (p1:Person), (p2:Person), (p1)-[:FRIEND]-(p2) "
      + "WITH p1 AS user, COLLECT(p2) AS friends "
      + "OPTIONAL MATCH (user)-[:TAKES_PART_IN]-(g:Group) "
      + "RETURN id(user) AS id, user.name AS name, "
      + "user.avatar_200 AS avatar_200, user.vkId as vkId, friends, COLLECT(g) AS groups;")
  List<UserDTO> getAllUsersInfo();


  @Query("MATCH (p:Person) "
      + "WHERE p.vkId={0}"
      + "RETURN p")
  Person getByVkId(Integer vkId);
}
