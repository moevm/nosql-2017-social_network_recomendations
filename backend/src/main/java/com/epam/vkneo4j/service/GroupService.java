package com.epam.vkneo4j.service;

import com.epam.vkneo4j.model.Group;
import com.epam.vkneo4j.queryresults.GroupData;
import java.util.List;

public interface GroupService {

  void save(Group group);

  Group findGroupByVkId(Integer vkId);

  List<GroupData> getRecommendationList(Integer userVkId, Integer limit, boolean withFriendWeight);
}
