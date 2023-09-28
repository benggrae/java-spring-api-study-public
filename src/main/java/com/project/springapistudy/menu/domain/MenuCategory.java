package com.project.springapistudy.menu.domain;

import com.project.springapistudy.menu.exception.MenuIllegalArgumentException;

public enum MenuCategory {
    COFFEE,
    BEVERAGE,
    FOOD,
    NONE;

    public static void validate(MenuCategory category) {
        if (category == null){
            throw new MenuIllegalArgumentException(MenuErrorCode.MENU_IS_NOT_EMPTY_CATEGORY_NAME);
        }
    }
}
