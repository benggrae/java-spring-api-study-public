package com.project.springapistudy.menu.dto;

import java.math.BigDecimal;
import org.openapitools.jackson.nullable.JsonNullable;

public record MenuChangeRequest (
        JsonNullable<String> name,
        JsonNullable<String> category,
        JsonNullable<BigDecimal> price
){

}

