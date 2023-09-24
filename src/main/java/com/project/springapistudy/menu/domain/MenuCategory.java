package com.project.springapistudy.menu.domain;

public enum MenuCategory {
    COFFEE,
    BEVERAGE,
    FOOD,
    NONE;

    public static void validate(MenuCategory category) {
        if (category == null){
            throw new IllegalArgumentException("카테고리는 비어 있으면 안됩니다.");
        }
    }
}
