package com.falabella.logistic.network.dto;


import com.falabella.logistic.network.model.Node;
import lombok.Data;

import java.util.List;

@Data
public class ItineraryDTO {
    private NodeDTO originNode;
    private NodeDTO destinationNode;
    private List<ItineraryLeg> legs;

}

