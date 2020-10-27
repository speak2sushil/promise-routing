package com.falabella.logistic.network.util;

import com.falabella.logistic.network.dto.LegDTO;
import com.falabella.logistic.network.dto.NodeDTO;
import com.falabella.logistic.network.dto.OperationDTO;
import com.falabella.logistic.network.dto.OperatorDTO;
import com.falabella.logistic.network.model.NodeType;
import com.falabella.logistic.network.model.ServiceCategory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.File;
import java.util.*;

public class ConvertJavaObjectToJson {

    public static void main(String[] args) throws JsonProcessingException {
        LegDTO legDTO=new LegDTO();
        NodeDTO sourceNode=new NodeDTO();
        sourceNode.setId(6L);
        NodeDTO destinationNode=new NodeDTO();
        destinationNode.setId(4L);
        legDTO.setOriginNode(sourceNode);
        legDTO.setTargetNode(destinationNode);
        legDTO.setRanking(2001);
        List<LegDTO> legDTOList=List.of(legDTO);

        try {
            String jsonInput = new Gson().toJson(legDTOList, List.class);
            System.err.println(jsonInput);
//            mapper.writeValue(new File("leg.json"), leg);
//            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(leg);
//            System.err.println(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Person newPerson() {
        Person person = new Person();

        List<String> hobbies = new ArrayList<>();
        hobbies.add("Football");
        hobbies.add("Cooking");
        hobbies.add("Swimming");

        Map<String, String> languages = new HashMap<>();
        languages.put("French", "Beginner");
        languages.put("German", "Intermediate");
        languages.put("Spanish", "Advanced");

        person.setName("David");
        person.setAge(30);
        person.setHobbies(hobbies);
        person.setLanguages(languages);

        return person;
    }
}
class Person {

    String name;
    Integer age;
    List<String> hobbies;
    Map<String, String> languages;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    public Map<String, String> getLanguages() {
        return languages;
    }

    public void setLanguages(Map<String, String> languages) {
        this.languages = languages;
    }
}
