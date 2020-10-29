package com.falabella.logistic.network.model;

import lombok.Builder;
import lombok.Value;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Labels;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.List;
import java.util.Set;

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
    Restrictions restrictions;
    List<ServiceOperation> operation;

    public static class Restrictions {
        List<String> productTypes;
        double maxWeight;
        String weightUOM;
        double maxVolume;
        String volumeUOM;
    }


    public static class ServiceOperation {
        List<String> dayOfWeek;
        boolean enabled;
        String deadLine;
        //TODO Resources mapping
    }

}