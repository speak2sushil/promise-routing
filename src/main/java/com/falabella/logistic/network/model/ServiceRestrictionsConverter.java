package com.falabella.logistic.network.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.neo4j.ogm.typeconversion.AttributeConverter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ServiceRestrictionsConverter implements AttributeConverter<ServiceRestrictions, String> {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String toGraphProperty(ServiceRestrictions serviceRestrictions) {
        if (null != serviceRestrictions) {
            try {
                return objectMapper.writeValueAsString(serviceRestrictions);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("serialization failed");
            }
        }
        return null;
    }

    @Override
    public ServiceRestrictions toEntityAttribute(String jsonString) {
        if (StringUtils.isNoneEmpty(jsonString)) {
            try {
                objectMapper.readValue(jsonString, ServiceRestrictions.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("deserialization failed");
            }
        }
        return null;

    }
}
