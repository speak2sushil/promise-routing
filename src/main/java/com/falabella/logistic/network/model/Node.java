package com.falabella.logistic.network.model;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.neo4j.ogm.annotation.typeconversion.EnumString;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@NodeEntity
@Data
public class Node {

    @Id @GeneratedValue
    private Long nodeId;
    @Labels
    private Collection<String> labels;

    private String name;
    private String shortName;
    //@Relationship(type="HAS_OPERATOR",direction = Relationship.OUTGOING)
    @Convert(OperatorConverter.class)
    @Property
    private Operator operator;
    @EnumString(value = NodeType.class, lenient = true)
    private NodeType type;
    private boolean enabled ;
    private Integer ranking ;
    @Relationship(type = "HAS-SERVICE", direction = Relationship.OUTGOING)
    List<Service> services;
   /* @JsonIgnoreProperties
    @Relationship(type="LEG",direction = Relationship.UNDIRECTED)
    private Set<Leg> legs;*/

}
