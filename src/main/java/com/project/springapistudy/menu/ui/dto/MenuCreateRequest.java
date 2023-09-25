package com.project.springapistudy.menu.ui.dto;

import com.project.springapistudy.menu.domain.MenuCategory;
import java.math.BigDecimal;

public record MenuCreateRequest(
        String name,
        MenuCategory category,
        BigDecimal price
) {

}
