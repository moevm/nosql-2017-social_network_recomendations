package com.epam.vkneo4j.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Data;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.neo4j.annotation.QueryResult;

@NodeEntity
@Data
public class Person {

  @GraphId
  private Long id;

  private String name;

  private String avatar_200;

  private Integer vkId;
  @Relationship(type = "FRIEND", direction = Relationship.UNDIRECTED)
  private List<Person> friends;
  @Relationship(type = "TAKES_PART_IN", direction = Relationship.UNDIRECTED)
  private List<Group> groups;

  private Person() {
  }

  public Person(Integer vkId, String name, String avatar_200) {
    this.name = name;
    this.avatar_200 = avatar_200;
    this.vkId = vkId;
  }

  public Person(Integer vkId, String name, String avatar_200, List<Person> friends,
      List<Group> groups) {
    this.name = name;
    this.avatar_200 = avatar_200;
    this.vkId = vkId;
    this.friends = friends;
    this.groups = groups;
  }

  public void friendsWith(Person person) {
    if (friends == null) {
      friends = new ArrayList<>();
    }
    friends.add(person);
  }

  public void takesPartIn(Group group) {
    if (groups == null) {
      groups = new ArrayList<>();
    }
    groups.add(group);
  }

  public String toString() {

    return this.name + "'s friends => "
        + Optional.ofNullable(this.friends).orElse(
        Collections.emptyList())
        .stream()
        .map(Person::getName).collect(Collectors.toList());
  }
}
