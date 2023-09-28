package com.project.springapistudy.menu.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.project.springapistudy.menu.exception.MenuIllegalArgumentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;


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
    @DisplayName("메뉴의 이름은 공백이면 안된다.")
    void menuNameIsNotEmpty(String menuName) {
        assertThatThrownBy(() ->
                Menu.builder()
                    .name(menuName)
                    .category(MenuCategory.BEVERAGE)
                    .build())
        .isInstanceOf(MenuIllegalArgumentException.class);

    }

    @Test
    @DisplayName("메뉴는 카테고리 가진다.")
    void menuHasCategory() {
        assertThatThrownBy(() ->
                Menu.builder()
                        .name("아메리카노")
                        .build())
                .isInstanceOf(MenuIllegalArgumentException.class);
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

}
