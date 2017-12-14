package com.epam.vkneo4j.queryresults;

import com.epam.vkneo4j.model.Group;
import com.epam.vkneo4j.model.Person;
import java.util.List;
import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

@Data
@QueryResult
public class UserDTO {

  private Long id;
  private String name;
  private String avatar_200;
  private Integer vkId;
  private List<Person> friends;
  private List<Group> groups;
}
