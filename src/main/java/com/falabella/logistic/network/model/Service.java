package com.falabella.logistic.network.model;

import lombok.Builder;
import lombok.Value;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import java.util.List;

@Value
@Builder(toBuilder = true)
@NodeEntity
public class Service {
    @Id
    @GeneratedValue
    Long id;
    String name;
    ServiceType serviceType;
    ServiceCategory serviceCategory;
    boolean enabled;
    int offeredDaysAhead;
    @Convert(value = ServiceRestrictionsConverter.class)
    ServiceRestrictions serviceRestrictions;
    @Convert(value = ServiceOperationConverter.class)
    List<ServiceOperation> operation;
}