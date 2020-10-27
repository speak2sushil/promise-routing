package com.falabella.logistic.network.model;


import lombok.Data;
import org.neo4j.ogm.model.Result;
import org.springframework.data.neo4j.annotation.QueryResult;
import java.util.List;

@QueryResult
@Data
public class ItineraryResponse {
    private List<String>  legs;
    private Node  sourceNode;
    private Node  destinationNode;
    private Integer  noOfEdges;
    private List<Node> nodes;
    private List<Leg>  rels;

}
