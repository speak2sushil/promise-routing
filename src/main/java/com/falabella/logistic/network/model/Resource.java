package com.falabella.logistic.network.model;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
@Data
public class Resource {

    @Id
    @GeneratedValue
    private Long resourceId;
    private String resourceName;
}
