package com.falabella.logistic.network.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.typeconversion.AttributeConverter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ServiceOperationConverter implements AttributeConverter<List<ServiceOperation>, String> {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String toGraphProperty(List<ServiceOperation> ServiceOperation) {
        if (null != ServiceOperation) {
            try {
                return objectMapper.writeValueAsString(ServiceOperation);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("serialization failed");
            }
        }
        return null;
    }

    @Override
    public List<ServiceOperation> toEntityAttribute(String jsonString) {
        if (StringUtils.isNoneEmpty(jsonString)) {
            try {
                objectMapper.readValue(jsonString, List.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("deserialization failed");
            }
        }
        return null;

    }
}
