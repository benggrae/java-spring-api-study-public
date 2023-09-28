package com.project.springapistudy.menu.dto;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record MenuSearchResponse (
    Long menuId,
    String name,
    String category,
    BigDecimal price
){}
