package com.epam.vkneo4j.service;

import com.epam.vkneo4j.model.Person;
import com.epam.vkneo4j.queryresults.GraphDTO;
import com.epam.vkneo4j.queryresults.UserDTO;
import java.util.List;

public interface PersonService {

  void save(Person person);

  List<Person> getFriends(Integer vkId);

  GraphDTO getGroupsByVkIds(List<Integer> ids, boolean onlyCommon);

  List<UserDTO> getAllUsersInfo();

  Person getByVkId(Integer vkId);
}
