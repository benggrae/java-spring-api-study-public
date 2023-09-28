package com.project.springapistudy.menu.domain;

import com.project.springapistudy.menu.exception.MenuIllegalArgumentException;

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
            throw new MenuIllegalArgumentException(MenuErrorCode.MENU_IS_NOT_EMPTY_CATEGORY_NAME);
        }
    }
}
