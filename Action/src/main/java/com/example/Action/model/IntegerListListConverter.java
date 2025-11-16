package com.example.Action.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.fasterxml.jackson.core.type.TypeReference;


@Converter
public class IntegerListListConverter implements AttributeConverter<List<List<Integer>>, String> {

	private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<List<Integer>> attribute) {
        if (attribute == null || attribute.isEmpty()) return "[]";
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting List<List<Integer>> to JSON", e);
        }
    }

    @Override
    public List<List<Integer>> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) return new ArrayList<>();
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<List<Integer>>>() {});
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting JSON to List<List<Integer>>", e);
        }
    }
}
