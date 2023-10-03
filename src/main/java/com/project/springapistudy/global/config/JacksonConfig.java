package com.project.springapistudy.global.config;

import lombok.RequiredArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JacksonConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer objectMapper() {
        return jacksonObjectMapperBuilder -> {
          jacksonObjectMapperBuilder.modules(new JsonNullableModule());
        };
    }
}
