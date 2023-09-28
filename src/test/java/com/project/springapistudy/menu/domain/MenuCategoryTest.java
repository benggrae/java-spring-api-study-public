package com.project.springapistudy.menu.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.project.springapistudy.menu.exception.MenuIllegalArgumentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuCategoryTest {
    @Test
    @DisplayName("메뉴 카테고리는 비어있으면 안된다.")
    void menuCategoryIsNotEmpty() {
        assertThatThrownBy(() -> MenuCategory.validate(null))
                .isInstanceOf(MenuIllegalArgumentException.class);

    }
}

