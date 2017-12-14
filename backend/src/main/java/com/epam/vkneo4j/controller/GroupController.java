package com.epam.vkneo4j.controller;

import com.epam.vkneo4j.queryresults.GraphDTO;
import com.epam.vkneo4j.queryresults.GroupData;
import com.epam.vkneo4j.service.GroupService;
import com.epam.vkneo4j.service.PersonService;
import com.epam.vkneo4j.util.Util;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GroupController {

  private GroupService groupService;
  private PersonService personService;

  @Autowired
  public GroupController(GroupService groupService, PersonService personService) {
    this.groupService = groupService;
    this.personService = personService;
  }

  @GetMapping("/getRecommendations")
  List<GroupData> getRecommendations(
      Principal principal,
      @RequestParam(value = "limit", defaultValue = "10") Integer limit,
      @RequestParam(value = "withFriendWeight", defaultValue = "false") boolean withFriendWeight
  ) {
    return groupService
        .getRecommendationList(Util.getUserActor(principal).getId(), limit, withFriendWeight);
  }

  @GetMapping("/getGroupsByIds")
  GraphDTO getGroupsByIds(
      @RequestParam(value = "ids") String ids,
      @RequestParam(value = "onlyCommon") boolean onlyCommon
  ) {
    return personService.getGroupsByVkIds(
        Arrays.stream(ids.split(",")).map(Integer::parseInt).collect(Collectors.toList()),
        onlyCommon);
  }
}
