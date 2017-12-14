package com.epam.vkneo4j.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.vk.api.sdk.client.actors.UserActor;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

public class Util {

  private Util() {
  }

  public static Stream<JsonElement> jsonArrayToStream(JsonArray array) {
    return StreamSupport.stream(array.spliterator(), false);
  }

  public static UserActor getUserActor(Principal principal) {
    OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) ((OAuth2Authentication) principal)
        .getDetails();
    Map<String, List<Map<String, Object>>> userAuthDetails = (Map<String, List<Map<String, Object>>>) ((OAuth2Authentication) principal)
        .getUserAuthentication().getDetails();
    return new UserActor(
        Integer.parseInt(userAuthDetails.get("response").get(0).get("uid").toString()),
        oauthDetails.getTokenValue());
  }
}
