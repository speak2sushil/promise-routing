package com.falabella.logistic.network.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RelationshipEntity(type = "LEG")
@Data
public class Leg {

    @Id
    @GeneratedValue
    private Long legId;
    //@Relationship(type="HAS_OPERATOR",direction = Relationship.OUTGOING)
    @Convert(OperatorConverter.class)
    @Property
    private Operator operator;
    @JsonIgnore
    @StartNode
    private Node originNode;

    @JsonIgnore
    @EndNode
    private Node targetNode;

    @Convert(ServiceCategoryConverter.class)
    private Set<ServiceCategory> serviceCategory;
    private boolean enabled;
    private Integer ranking;
    private Integer preprocessingInMins;
    private Integer offeredDaysAhead;
    //restrictions
    private Double maxWeightInKg;
    private Double maxVolumeInDm3;
    private Double maxDimensionInCm;
    @Convert(NoPrefixPropertiesConverter.class)
    private Map<String, List<String>> productTypes; // INLCUDED, EXCLUDED
    //@Relationship(type="OPERATION",direction = Relationship.OUTGOING
    @Convert(OperationConverter.class)
    private List<Operation> operationList;

}
