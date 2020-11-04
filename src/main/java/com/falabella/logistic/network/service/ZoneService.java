package com.falabella.logistic.network.service;

import com.falabella.logistic.network.dto.ItineraryDTO;
import com.falabella.logistic.network.model.ItineraryResponse;

import java.util.List;

public interface ZoneService {

    public List<ItineraryDTO>  findItinerary(List<Long> zoneIds);

    List<ItineraryResponse> findItinerariesForAZone(List<Long> zoneIds);
}
