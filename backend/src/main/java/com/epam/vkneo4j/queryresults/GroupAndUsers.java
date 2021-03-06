package com.epam.vkneo4j.queryresults;

import com.epam.vkneo4j.model.Group;
import com.epam.vkneo4j.model.Person;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.annotation.QueryResult;

@Data
@QueryResult
@AllArgsConstructor
@NoArgsConstructor
public class GroupAndUsers {
  private Group group;
  private List<Person> users;
}
