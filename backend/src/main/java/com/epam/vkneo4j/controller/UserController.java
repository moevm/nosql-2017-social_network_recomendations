package com.epam.vkneo4j.controller;

import com.epam.vkneo4j.model.Person;
import com.epam.vkneo4j.service.PersonService;
import com.epam.vkneo4j.service.VKService;
import com.epam.vkneo4j.util.Util;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

  private VKService vkService;
  private PersonService personService;

  @Autowired
  public UserController(VKService vkService, PersonService personService) {
    this.vkService = vkService;
    this.personService = personService;
  }

  @GetMapping("/retrieveDataFromVk")
  void retrieveDataFromVk(Principal principal) {
    if (personService.getByVkId(Util.getUserActor(principal).getId()) == null) {
      vkService.getUserInfoWithFriendsAndTheirGroups(principal);
    }
  }

  @GetMapping("/friends")
  List<Person> friends(Principal principal) {
    return this.personService.getFriends(Util.getUserActor(principal).getId());
  }

  @GetMapping("/getAllUsersInfo")
  void getAllUsersInfo(HttpServletResponse response) throws IOException {
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("cp1251");
    response.setHeader("Content-Disposition", "attachment;filename=mock.json");
    PrintWriter out = response.getWriter();
    out.println(new Gson().toJson(this.personService.getAllUsersInfo()));
    out.flush();
    out.close();
  }

}
