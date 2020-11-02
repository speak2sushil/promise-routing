package com.falabella.logistic.network.dto;



import com.falabella.logistic.network.model.LegOperation;
import com.falabella.logistic.network.model.LegRestrictions;
import com.falabella.logistic.network.model.ServiceCategory;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class LegDTO {

    private Long legId;

    private NodeDTO originNode;

    private NodeDTO targetNode;

    private OperatorDTO operator;

    private Set<ServiceCategory> serviceCategory;
    private boolean enabled ;
    private Integer ranking ;
    private Integer preprocessingInMins ;
    private Integer offeredDaysAhead ;
    private Integer cost;
    private LegRestrictions legRestrictions;
    private List<LegOperation> operationList;
    private List<String> excludedProductTypes;
}
