package com.falabella.logistic.network.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@JsonDeserialize(builder = ServiceRestrictions.ServiceRestrictionsBuilder.class)
public class ServiceRestrictions {
    List<String> productTypes;
    double maxWeight;
    String weightUOM;
    double maxVolume;
    String volumeUOM;
}