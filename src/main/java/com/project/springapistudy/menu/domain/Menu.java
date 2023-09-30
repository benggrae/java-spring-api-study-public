package com.project.springapistudy.menu.domain;

import com.project.springapistudy.global.domain.BaseTimeEntity;
import com.project.springapistudy.menu.exception.MenuValidationException;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseTimeEntity {

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

    @Builder
    private Menu(String name, MenuCategory category, Price price) {
        validateMenuName(name);
        validateMenuCategory(category);

        this.name = name;
        this.price = price;
        this.category = category;
    }

    public void changeMenuName(String name) {
        validateMenuName(name);
        this.name = name;
    }

    public void changeMenuCategory(MenuCategory category) {
        this.category = category;
    }

    public void changePrice(Price price) {
        this.price = price;
    }


    private void validateMenuName(String name) {
        if (name == null || name.isBlank()) {
            throw new MenuValidationException(MenuErrorCode.MENU_IS_NOT_EMPTY_NAME);
        }
    }

    private void validateMenuCategory(MenuCategory menuCategory) {
        if (ObjectUtils.isEmpty(menuCategory)) {
            throw new MenuValidationException(MenuErrorCode.MENU_IS_NOT_EMPTY_CATEGORY_NAME);
        }
    }
}
