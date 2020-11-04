package com.falabella.logistic.network.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Labels;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.neo4j.ogm.annotation.typeconversion.EnumString;

import java.util.Collection;
import java.util.List;

@NodeEntity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Node {

    @Id @GeneratedValue
    private Long nodeId;
    @Labels
    private Collection<String> labels;

    private String name;
    private String shortName;
    @Convert(OperatorConverter.class)
    @Property
    private Operator operator;
    @EnumString(value = NodeType.class, lenient = true)
    private NodeType type;
    private boolean enabled ;
    private Integer ranking ;
    @Relationship(type = "HAS-SERVICE", direction = Relationship.OUTGOING)
    List<Service> services;

}
