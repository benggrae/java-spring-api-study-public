package com.project.springapistudy.helper;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestHelper {
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    }

   public static String DTO를_JSON_문자열로_변환(Object dto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(dto);
    }

}
