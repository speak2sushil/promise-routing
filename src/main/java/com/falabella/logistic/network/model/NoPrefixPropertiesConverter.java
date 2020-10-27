package com.falabella.logistic.network.model;

import org.apache.commons.collections4.MapUtils;
import org.neo4j.ogm.typeconversion.CompositeAttributeConverter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class NoPrefixPropertiesConverter implements CompositeAttributeConverter<Map<String, Object>> {


    @Override
    public Map<String, ?> toGraphProperties(Map<String, Object> value) {
        Map<String, ?> map= Collections
                .emptyMap();
        if(MapUtils.isNotEmpty(value)){
            return value.entrySet()
                    .stream()
                    .collect(Collectors.toMap(e->removeStart(e.getKey(),"properties."), Map.Entry::getValue));
        }
        return map;

    }

    @Override
    public Map<String, Object> toEntityAttribute(Map<String, ?> value) {
        Map<String, Object> map= Collections
                .emptyMap();
        if(MapUtils.isNotEmpty(value)) {
            return value.entrySet()
                    .stream()
                    .collect(Collectors.toMap(e -> removeStart(e.getKey(), "properties."), Map.Entry::getValue));
        }
        return map;
    }

    public static String removeStart(final String str, final String remove) {

        if (str.startsWith(remove)){
            return str.substring(remove.length());
        }
        return str;
    }

}