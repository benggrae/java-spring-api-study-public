package com.project.springapistudy.helper;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestHelper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    }

   public static String toJsonString(Object dto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(dto);
    }

}
