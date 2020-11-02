package com.falabella.logistic.network.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LegRestrictions {
    Double maxWeightInKg;
    Double maxVolumeInDm3;
    Double maxDimensionInCm;
}
