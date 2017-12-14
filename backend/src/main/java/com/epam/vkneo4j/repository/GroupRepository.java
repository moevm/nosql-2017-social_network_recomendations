package com.epam.vkneo4j.repository;

import com.epam.vkneo4j.model.Group;
import com.epam.vkneo4j.queryresults.GroupData;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface GroupRepository extends GraphRepository<Group> {

  @Query("MATCH (g:Group), (g)-[r]-(p2:Person), (p:Person) "
      + "WHERE NOT (g)-[:TAKES_PART_IN]-(p) AND p.vkId = {0} "
      + "RETURN g as group, COLLECT(p2) as friends, COUNT(r) AS rating "
      + "ORDER BY rating DESC "
      + "LIMIT {1};")
  List<GroupData> getRecommendationList(Integer userVkId, Integer limit);

  @Query("MATCH (g:Group), (g)-[r]-(p3:Person), (p:Person) "
      + "WHERE NOT (g)-[:TAKES_PART_IN]-(p) and p.vkId = {0} "
      + "WITH g, p3 "
      + "MATCH (p1:Person)-[:TAKES_PART_IN]-(g1:Group)-[:TAKES_PART_IN]-(p3)"
      + "WHERE p1.vkId = {0} " + "WITH g, p3, COUNT(g1) as weight "
      + "RETURN g as group, COLLECT(p3) as friends, SUM(weight) as rating "
      + "ORDER BY rating DESC "
      + "LIMIT {1};"
  )
  List<GroupData> getRecommendationListBasedOnFriends(Integer userVkId, Integer limit);

  Group findGroupByVkId(Integer vkId);
}
