package com.falabella.logistic.network.model;

import org.neo4j.ogm.typeconversion.AttributeConverter;

import java.util.HashSet;
import java.util.Set;

public class ServiceCategoryConverter implements AttributeConverter<Set<ServiceCategory>, String[]> {
    public String[] toGraphProperty(Set<ServiceCategory> serviceCategory) {
        String[] result = new String[serviceCategory.size()];
        int i = 0;
        for (ServiceCategory service : serviceCategory) {
            result[i++] = service.name();
        }
        return result;
    }

    public Set<ServiceCategory> toEntityAttribute(String[] rights) {
        Set<ServiceCategory> result = new HashSet<>();
        for (String right : rights) {
            result.add(ServiceCategory.valueOf(right));
        }
        return result;
    }
}
