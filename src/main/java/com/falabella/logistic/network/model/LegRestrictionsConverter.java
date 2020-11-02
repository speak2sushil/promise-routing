package com.falabella.logistic.network.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.typeconversion.AttributeConverter;

import java.awt.print.Book;

public class LegRestrictionsConverter implements AttributeConverter<LegRestrictions, String> {

    @Override
    public String toGraphProperty(LegRestrictions legRestrictions) {
        if (null != legRestrictions) {
            return new Gson().toJson(legRestrictions);
        }
        return null;
    }

    @Override
    public LegRestrictions toEntityAttribute(String jsonString) {
        if (StringUtils.isNoneEmpty(jsonString)) {
            Gson gson = new Gson();
            return gson.fromJson(jsonString, LegRestrictions.class);
        }
        return null;
    }
}
