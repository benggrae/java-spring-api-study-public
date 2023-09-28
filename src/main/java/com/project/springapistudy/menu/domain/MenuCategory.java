package com.project.springapistudy.menu.domain;

import com.project.springapistudy.global.exception.NotFoundException;

public enum MenuCategory {
    COFFEE,
    BEVERAGE,
    FOOD,
    NONE;

    public static MenuCategory of(String category) {
        if (category == null || category.isEmpty()) {
            return MenuCategory.NONE;
        }
        try {
            return MenuCategory.valueOf(category);
        } catch (IllegalArgumentException e) {
            throw new NotFoundException(MenuErrorCode.MENU_CATEGORY_IS_NOT_FOUND);
        }
    }
}
