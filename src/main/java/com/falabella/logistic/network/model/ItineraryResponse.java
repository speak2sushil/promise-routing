package com.falabella.logistic.network.model;


import lombok.Builder;
import lombok.Data;
import lombok.Value;
import org.neo4j.ogm.model.Result;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.List;
import java.util.Set;

@QueryResult
@Value
@Builder
public class ItineraryResponse {
    Node sourceNode;
    Node destinationNode;
    Integer noOfEdges;
    List<Node> nodes;
    List<Leg> rels;
    Set<String> excludedProductTypes;
    Restrictions restrictions;

    @Value
    @Builder
    public static class Restrictions {
        Double maxWeightInKg;
        Double maxVolumeInDm3;
        Double maxDimensionInCm;
    }
}
