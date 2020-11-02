package com.falabella.logistic.network.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.typeconversion.AttributeConverter;

public class LegRestrictionsConverter implements AttributeConverter<LegRestrictions, String> {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String toGraphProperty(LegRestrictions legRestrictions) {
        if (null != legRestrictions) {
            try {
                return objectMapper.writeValueAsString(legRestrictions);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("serialization failed");
            }
        }
        return null;
    }

    @Override
    public LegRestrictions toEntityAttribute(String jsonString) {
        if (StringUtils.isNoneEmpty(jsonString)) {
            try {
                objectMapper.readValue(jsonString, LegRestrictions.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("deserialization failed");
            }
        }
        return null;

    }
}
