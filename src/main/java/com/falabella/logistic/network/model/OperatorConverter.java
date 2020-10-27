package com.falabella.logistic.network.model;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.neo4j.ogm.typeconversion.AttributeConverter;



public class OperatorConverter implements AttributeConverter<Operator, String> {


    @Override
    public String toGraphProperty(Operator operator) {

        if(operator!=null && StringUtils.isNoneEmpty(operator.getOperatorName())){
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("operatorName",operator.getOperatorName());
            return jsonObject.toString();
        }
        return null;
    }

    @Override
    public Operator toEntityAttribute(String jsonString) {
        if(StringUtils.isNoneEmpty(jsonString)){
            Gson gson = new GsonBuilder().create();
            Operator operator = gson.fromJson(jsonString,  Operator.class);
            return operator;
        }
        return null;

    }
}
