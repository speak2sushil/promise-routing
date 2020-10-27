package com.falabella.logistic.network.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.neo4j.ogm.typeconversion.AttributeConverter;

import java.util.List;

public class OperationConverter implements AttributeConverter<List<Operation>, String> {


    @Override
    public String toGraphProperty(List<Operation> operations) {
        JSONArray jsonArray = new JSONArray(operations);
        return jsonArray.toString();
    }

    @Override
    public List<Operation> toEntityAttribute(String jsonString) {
        if(StringUtils.isNotEmpty(jsonString) &&  !jsonString.contains("[]")){
            Gson gson = new GsonBuilder().create();
            List<Operation> operationList = (List<Operation>) gson.fromJson(jsonString, Operation.class);
            return operationList;
        }
        return null;

    }
}
