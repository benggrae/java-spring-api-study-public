package com.project.springapistudy.menu.domain;

import com.project.springapistudy.menu.exception.MenuIllegalArgumentException;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.ObjectUtils;


@Getter
@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "price", nullable = false))
    private Price price;

    @Enumerated(EnumType.STRING)
    private MenuCategory category;
    protected Menu() {}

    @Builder
    private Menu(String name, MenuCategory category, Price price) {
        validateMenuName(name);
        validateMenuCategory(category);

        this.name = name;
        this.price = price;
        this.category = category;
    }

    private void validateMenuName(String name) {
        if (name == null || name.isBlank()) {
            throw new MenuIllegalArgumentException(MenuErrorCode.MENU_IS_NOT_EMPTY_NAME);
        }
    }

    private void validateMenuCategory(MenuCategory menuCategory) {
        if (ObjectUtils.isEmpty(menuCategory)) {
            throw new MenuIllegalArgumentException(MenuErrorCode.MENU_IS_NOT_EMPTY_CATEGORY_NAME);
        }
    }
}
