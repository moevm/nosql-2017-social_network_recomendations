package com.epam.vkneo4j.model;

import lombok.Data;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.QueryResult;

@NodeEntity
@Data
public class Group {

  @GraphId
  private Long id;

  private String name;

  private String photo_200;

  private Integer vkId;

  private Group() {
  }

  public Group(Integer vkId, String name, String photo_200) {
    this.name = name;
    this.photo_200 = photo_200;
    this.vkId = vkId;
  }

}
