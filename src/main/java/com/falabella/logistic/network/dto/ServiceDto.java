package com.falabella.logistic.network.dto;

import com.falabella.logistic.network.model.ServiceCategory;
import com.falabella.logistic.network.model.ServiceOperation;
import com.falabella.logistic.network.model.ServiceRestrictions;
import com.falabella.logistic.network.model.ServiceType;
import lombok.Data;

import java.util.List;

@Data
public class ServiceDto {
    ServiceType serviceType;
    ServiceCategory serviceCategory;
    boolean enabled;
    int offeredDaysAhead;
    ServiceRestrictions serviceRestrictions;
    List<ServiceOperation> serviceOperation;
}
