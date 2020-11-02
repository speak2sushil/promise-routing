package com.falabella.logistic.network.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.neo4j.ogm.typeconversion.AttributeConverter;

import java.util.List;

public class LegOperationConverter implements AttributeConverter<List<LegOperation>, String> {


    @Override
    public String toGraphProperty(List<LegOperation> legOperations) {
        JSONArray jsonArray = new JSONArray(legOperations);
        return jsonArray.toString();
    }

    @Override
    public List<LegOperation> toEntityAttribute(String jsonString) {
        if (StringUtils.isNotEmpty(jsonString) && !jsonString.contains("[]")) {
            Gson gson = new GsonBuilder().create();
            gson.fromJson(jsonString, List.class);
        }
        return null;

    }
}
