package com.project.springapistudy.menu.fixture;

import com.project.springapistudy.menu.domain.Menu;
import com.project.springapistudy.menu.domain.MenuCategory;
import com.project.springapistudy.menu.domain.Price;

public class MenuFixture {

    public static Menu createMenu(String menuName, MenuCategory category, Price price) {
        return Menu.builder()
                .name(menuName)
                .category(category)
                .price(price)
                .build();
    }
}
