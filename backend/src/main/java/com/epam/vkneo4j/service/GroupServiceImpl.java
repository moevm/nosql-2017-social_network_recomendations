package com.epam.vkneo4j.service;

import com.epam.vkneo4j.model.Group;
import com.epam.vkneo4j.queryresults.GroupData;
import com.epam.vkneo4j.repository.GroupRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GroupServiceImpl implements GroupService {

  private GroupRepository groupRepository;

  @Autowired
  public GroupServiceImpl(GroupRepository personRepository) {
    this.groupRepository = personRepository;
  }

  @Override
  public void save(Group group) {
    this.groupRepository.save(group);
  }

  @Override
  public Group findGroupByVkId(Integer vkId) {
    return this.groupRepository.findGroupByVkId(vkId);
  }

  @Override
  public List<GroupData> getRecommendationList(Integer userVkId, Integer limit,
      boolean withFriendWeight) {
    if (withFriendWeight) {
      return this.groupRepository.getRecommendationListBasedOnFriends(userVkId, limit);
    } else {
      return this.groupRepository.getRecommendationList(userVkId, limit);
    }
  }

}
