package com.falabella.logistic.network.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.typeconversion.AttributeConverter;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ServiceOperationConverter implements AttributeConverter<List<ServiceOperation>, String> {

    @Override
    public String toGraphProperty(List<ServiceOperation> serviceOperations) {
        if (null != serviceOperations) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.toJson(serviceOperations);
        }
        return null;
    }

    @Override
    public List<ServiceOperation> toEntityAttribute(String jsonString) {
        if (StringUtils.isNoneEmpty(jsonString)) {
            Gson gson = new Gson();
            Type serviceOperations = new TypeToken<ArrayList<ServiceOperation>>(){}.getType();
            return gson.fromJson(jsonString, serviceOperations);
        }
        return null;
    }
}
