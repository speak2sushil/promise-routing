package com.falabella.logistic.network.dto;

import com.falabella.logistic.network.model.Resource;
import lombok.Data;


import java.util.Set;

@Data
public class OperationDTO {

    private String departureFrequency;
    private String departureETD;
    private Integer transitDays;
    private String arrivalETD;
    private Boolean  collect;
    private Boolean  enabled;
    private Set<Resource> resources;
}
