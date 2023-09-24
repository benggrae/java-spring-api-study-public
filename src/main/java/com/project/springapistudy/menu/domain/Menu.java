package com.project.springapistudy.menu.domain;

import static com.project.springapistudy.core.VerifyUtils.verifyEmptySource;

import lombok.Builder;
import lombok.Getter;


@Getter
public class Menu {
    private String name;
    private Price price;
    private MenuCategory category;

    @Builder
    private Menu(String name, MenuCategory category, Price price) {
        verifyEmptySource(name, "메뉴의 이름은 비어 있으면 안됩니다.");
        MenuCategory.validate(category);

        this.name = name;
        this.price = price;
        this.category = category;
    }
}
