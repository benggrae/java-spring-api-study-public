package com.project.springapistudy.helper;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.mock.web.MockHttpServletResponse;

public class TestHelper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

    }

   public static String toJsonString(Object dto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(dto);
    }

    public static <T> T toObject(String body, Class<T> tClass) throws JsonProcessingException {
        return objectMapper.readValue(body, tClass);
    }

    public static <T> T toObject(MockHttpServletResponse response, Class<T> tClass) throws IOException {
        return toObject(response.getContentAsString(StandardCharsets.UTF_8), tClass);
    }
}
