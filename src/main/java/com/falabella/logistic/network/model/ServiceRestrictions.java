package com.falabella.logistic.network.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ServiceRestrictions {
    List<String> productTypes;
    double maxWeight;
    String weightUOM;
    double maxVolume;
    String volumeUOM;
}