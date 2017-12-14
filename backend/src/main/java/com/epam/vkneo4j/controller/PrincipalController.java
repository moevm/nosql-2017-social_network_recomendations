package com.epam.vkneo4j.controller;

import com.epam.vkneo4j.util.Util;
import java.security.Principal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrincipalController {

  @GetMapping(value = "/user")
  public String user(Principal principal) {
    if (principal == null) {
      return "Not authorized";
    }
    return Util.getUserActor(principal).getId().toString();
  }
}
