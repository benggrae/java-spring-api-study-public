package com.project.springapistudy.menu.domain;

import static com.project.springapistudy.core.VerifyUtils.verifyEmptySource;

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
        verifyEmptySource(name, "메뉴의 이름은 비어 있으면 안됩니다.");
        MenuCategory.validate(category);

        this.name = name;
        this.price = price;
        this.category = category;
    }
}
