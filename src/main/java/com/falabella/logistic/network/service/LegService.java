package com.falabella.logistic.network.service;

import com.falabella.logistic.network.dto.LegDTO;
import com.falabella.logistic.network.model.Leg;

import java.util.List;
import java.util.Map;

public interface LegService {

    public Leg createLegAndNode(LegDTO legDTO);

    public Iterable<Leg> linkNodes(List<LegDTO> legList);

}
