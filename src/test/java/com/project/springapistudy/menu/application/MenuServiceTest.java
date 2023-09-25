package com.project.springapistudy.menu.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.project.springapistudy.core.exceptions.DuplicationException;
import com.project.springapistudy.menu.ui.dto.MenuCreateRequest;
import com.project.springapistudy.menu.domain.Menu;
import com.project.springapistudy.menu.domain.MenuCategory;
import com.project.springapistudy.menu.domain.MenuRepository;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@DisplayName("메뉴 관리")
class MenuServiceTest {

    @InjectMocks
    private MenuService menuService;

    @Mock
    private MenuRepository mockRepository;

    @Nested
    @DisplayName("메뉴 등록")
    class MenuRegister {
        @Test
        @DisplayName("메뉴가 등록된다.")
        void menuRegister() {
            //given
            MenuCreateRequest request = new MenuCreateRequest("메뉴", MenuCategory.NONE, BigDecimal.TEN);
            Menu createMenu = Menu.builder().category(MenuCategory.NONE).name(request.name()).build();
            ReflectionTestUtils.setField(createMenu, "id", 1L);

            given(mockRepository.findByName(request.name())).willReturn(Optional.empty());
            given(mockRepository.save(any())).willReturn(createMenu);

            //when
            Long menuId = menuService.registerMenu(request);

            //then
            assertThat(menuId).isEqualTo(1L);
        }
        @Test
        @DisplayName("이미 등록된 메뉴가 있으면 예외가 발생한다.")
        void existMenu() {
            //given
            MenuCreateRequest request = new MenuCreateRequest("메뉴", MenuCategory.NONE, BigDecimal.TEN);
            Menu 등록된_메뉴 = Menu.builder()
                    .category(MenuCategory.NONE)
                    .name(request.name())
                    .build();

            given(mockRepository.findByName(request.name())).willReturn(Optional.of(등록된_메뉴));

            //when & then
            assertThrows(DuplicationException.class, () -> {
                menuService.registerMenu(request);
            });
        }
    }


}
