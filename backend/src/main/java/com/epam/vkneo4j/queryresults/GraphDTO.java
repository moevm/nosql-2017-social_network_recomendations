package com.epam.vkneo4j.queryresults;

import com.epam.vkneo4j.model.Group;
import com.epam.vkneo4j.model.Person;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GraphDTO {

  List<Person> users;
  List<Group> groups;
  List<Link> links;
}
