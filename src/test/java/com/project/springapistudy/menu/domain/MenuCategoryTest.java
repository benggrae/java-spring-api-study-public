package com.project.springapistudy.menu.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.project.springapistudy.global.exception.NotFoundException;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("메뉴 카테고리를 검증한다.")
class MenuCategoryTest {
    @ParameterizedTest
    @DisplayName("메뉴 카테고리가 지정되지 않았으면 NONE이다.")
    @NullAndEmptySource
    void menuCategoryIsNone(String category) {
        //given
        MenuCategory menuCategory = MenuCategory.of(category);

        //when & then
        assertThat(menuCategory).isEqualTo(MenuCategory.NONE);
    }

    @ParameterizedTest
    @DisplayName("메뉴 카테고리를 찾는다.")
    @MethodSource("메뉴_카테고리를_검증_파라미터")
    void menuCategoryIsNone(String category, MenuCategory result) {
        //given
        MenuCategory menuCategory = MenuCategory.of(category);

        //when & then
        assertThat(menuCategory).isEqualTo(result);
    }

    @ParameterizedTest
    @DisplayName("메뉴 카테고리를 찾을때 존재하지 않으면 예외가 발생한다.")
    @ValueSource(strings = {"co", "Bv", "food"})
    void menuNoExist(String category) {
        assertThatThrownBy(
                () -> MenuCategory.of(category)
        ).isInstanceOf(NotFoundException.class);
    }

    private static Stream<Arguments> 메뉴_카테고리를_검증_파라미터() {
        return Stream.of(
                Arguments.of("COFFEE", MenuCategory.COFFEE),
                Arguments.of("BEVERAGE", MenuCategory.BEVERAGE),
                Arguments.of("FOOD", MenuCategory.FOOD)
        );
    }

}

