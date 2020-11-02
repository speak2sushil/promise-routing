package com.falabella.logistic.network.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@JsonDeserialize(builder = ServiceOperation.ServiceOperationBuilder.class)
public class ServiceOperation {
    List<String> dayOfWeek;
    boolean enabled;
    String deadLine;
    List<String> resources;
}