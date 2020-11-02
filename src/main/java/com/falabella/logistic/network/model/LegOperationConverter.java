package com.falabella.logistic.network.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.neo4j.ogm.typeconversion.AttributeConverter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LegOperationConverter implements AttributeConverter<List<LegOperation>, String> {


    @Override
    public String toGraphProperty(List<LegOperation> serviceOperations) {
        if (null != serviceOperations) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.toJson(serviceOperations);
        }
        return null;
    }

    @Override
    public List<LegOperation> toEntityAttribute(String jsonString) {
        if (StringUtils.isNoneEmpty(jsonString)) {
            Gson gson = new Gson();
            Type legOperations = new TypeToken<ArrayList<LegOperation>>(){}.getType();
            return gson.fromJson(jsonString, legOperations);
        }
        return null;
    }
}
