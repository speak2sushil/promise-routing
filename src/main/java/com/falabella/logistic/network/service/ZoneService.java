package com.falabella.logistic.network.service;

import com.falabella.logistic.network.dto.ItineraryDTO;

import java.util.List;

public interface ZoneService {

    public List<ItineraryDTO>  findItinerary(List<Long> zoneIds);

}
