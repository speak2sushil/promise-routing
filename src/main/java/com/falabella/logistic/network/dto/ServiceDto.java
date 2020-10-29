package com.falabella.logistic.network.dto;

import com.falabella.logistic.network.model.ServiceCategory;
import com.falabella.logistic.network.model.ServiceType;
import lombok.Data;

import java.util.List;

@Data
public class ServiceDto {
    ServiceType serviceType;
    ServiceCategory serviceCategory;
    boolean enabled;
    int offeredDaysAhead;
}
