package com.falabella.logistic.network.dto;

import com.falabella.logistic.network.model.Node;
import lombok.Data;

@Data
public class ItineraryLeg{
  private Long legId;
  private Integer sequence;
  private NodeDTO sourceNode;
  private NodeDTO targetNode;

}
