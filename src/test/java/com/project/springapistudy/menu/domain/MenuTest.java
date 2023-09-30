package com.project.springapistudy.menu.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.project.springapistudy.menu.exception.MenuValidationException;
import com.project.springapistudy.menu.fixture.MenuFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;


/**
 `메뉴`는 음료, 빵, 커피 등이 될 수 있다. V
 `메뉴`는 `카테고리`를 가진다 `메뉴`는 `가격` 가진다. V
 `메뉴의` 가격은 0원 이상 이어야 한다. `메뉴`는 `이름`을 가진다. V
 `메뉴`는 `이름`은 공백 일수가 없다. V
 **/

@DisplayName("메뉴 도메인 테스트")
class MenuTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    @DisplayName("메뉴의 이름은 공백이면 안된다.")
    void menuNameIsNotBlank(String menuName) {
        assertThatThrownBy(() -> Menu.builder()
                    .name(menuName)
                    .category(MenuCategory.BEVERAGE)
                    .build())
        .isInstanceOf(MenuValidationException.class);

    }

    @Test
    @DisplayName("메뉴는 카테고리 가진다.")
    void menuHasCategory() {
        assertThatThrownBy(() -> Menu.builder()
                        .name("아메리카노")
                        .build())
                .isInstanceOf(MenuValidationException.class);
    }

    @Test
    @DisplayName("메뉴가 생성 된다.")
    void createMenu() {
        //given
        final var price = Price.valueOf(10);
        final var name  = "메뉴 1호기";
        final var category  = MenuCategory.COFFEE;

        //when
        final Menu menu = Menu.builder()
                .price(price)
                .name(name)
                .category(category)
                .build();

        //then
        assertSoftly((it) -> {
            it.assertThat(menu.getName()).isEqualTo(name);
            it.assertThat(menu.getCategory()).isEqualTo(category);
            it.assertThat(menu.getPrice()).isEqualTo(price);
        });
    }


    @ParameterizedTest
    @DisplayName("메뉴이름이 공백이면 이름이 변경되지 않는다.")
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    void changeNotMenu(String menuName) {
        //given
        Menu menu = MenuFixture.createMenu("이름", MenuCategory.BEVERAGE, Price.valueOf(10));

        //when & then
        assertThatThrownBy(() -> menu.changeMenuName(menuName))
                .isInstanceOf(MenuValidationException.class);
    }

    @Test
    @DisplayName("메뉴이름이 변경이 된다.")
    void changeMenu() {
        Menu menu = MenuFixture.createMenu("이름", MenuCategory.BEVERAGE, Price.valueOf(10));

        menu.changeMenuName("변경된이름");

        assertThat(menu.getName()).isEqualTo("변경된이름");
    }

    @Test
    @DisplayName("메뉴 카테고리가 변경이 된다.")
    void changeMenuCategory() {
        Menu menu = MenuFixture.createMenu("이름", MenuCategory.BEVERAGE, Price.valueOf(10));

        menu.changeMenuCategory(MenuCategory.FOOD);

        assertThat(menu.getCategory()).isEqualTo(MenuCategory.FOOD);
    }

    @Test
    @DisplayName("메뉴 가격이 변경이 된다.")
    void changeMenuPrice() {
        Menu menu = MenuFixture.createMenu("이름", MenuCategory.BEVERAGE, Price.valueOf(10));

        menu.changePrice(Price.valueOf(100));

        assertThat(menu.getPrice()).isEqualTo(Price.valueOf(100));
    }


}
