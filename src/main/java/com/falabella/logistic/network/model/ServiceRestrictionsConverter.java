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

    @Override
    public String toGraphProperty(ServiceRestrictions serviceRestrictions) {
        if (null != serviceRestrictions) {
            return new Gson().toJson(serviceRestrictions);
        }
        return null;
    }

    @Override
    public ServiceRestrictions toEntityAttribute(String jsonString) {
        if (StringUtils.isNoneEmpty(jsonString)) {
            Gson gson = new Gson();
            return gson.fromJson(jsonString, ServiceRestrictions.class);
        }
        return null;
    }
}
