package com.falabella.logistic.network.dto;

import lombok.Data;

import java.util.List;

@Data
public class ItineraryRequest {
    List<Long> zoneIds;
}
