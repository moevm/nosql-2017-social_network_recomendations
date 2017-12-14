package com.epam.vkneo4j.service;

import java.security.Principal;

public interface SocialService {

  void getUserInfoWithFriendsAndTheirGroups(Principal principal);
}
