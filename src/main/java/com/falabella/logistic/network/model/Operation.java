package com.falabella.logistic.network.model;


import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

//@NodeEntity
@Data
public class Operation {

//    @Id
//    @GeneratedValue
//    private Long operationId;
    private String departureFrequency;
    private String departureETD;
    private Integer transitDays;
    private String arrivalETD;
    private Boolean  collect;
    private Boolean  enabled;
    @Relationship(type="HAS_RESOURCE",direction = Relationship.OUTGOING)
    private Set<Resource> resources;

}
