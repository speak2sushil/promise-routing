package com.falabella.logistic.network.dto;

import lombok.Data;

@Data
public class NodeDTO {
    private Long id;
    private String name;
    private String shortName;
    private OperatorDTO operator;
    private String type;
    private Boolean enabled;
    private Integer ranking;
}
