package com.project.springapistudy.menu.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.project.springapistudy.global.exception.DuplicationException;
import com.project.springapistudy.global.exception.NotFoundException;
import com.project.springapistudy.menu.domain.Menu;
import com.project.springapistudy.menu.domain.MenuCategory;
import com.project.springapistudy.menu.domain.MenuRepository;
import com.project.springapistudy.menu.domain.Price;
import com.project.springapistudy.menu.dto.MenuChangeRequest;
import com.project.springapistudy.menu.dto.MenuCreateRequest;
import com.project.springapistudy.menu.dto.MenuSearchResponse;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@DisplayName("메뉴 관리")
class MenuServiceTest {

    @InjectMocks
    private MenuService menuService;

    @Mock
    private MenuRepository menuRepository;

    @Nested
    @DisplayName("메뉴 등록")
    class MenuRegister {
        @Test
        @DisplayName("메뉴가 등록된다.")
        void menuRegister() {
            //given
            MenuCreateRequest request = MenuCreateRequest.builder()
                            .name("메뉴")
                            .category(MenuCategory.NONE.name())
                            .price(BigDecimal.TEN)
                            .build();

            Menu createMenu = Menu.builder().category(MenuCategory.NONE).name(request.name()).build();
            ReflectionTestUtils.setField(createMenu, "id", 1L);

            given(menuRepository.findByName(request.name())).willReturn(Optional.empty());
            given(menuRepository.save(any())).willReturn(createMenu);

            //when
            Long menuId = menuService.registerMenu(request);

            //then
            assertThat(menuId).isEqualTo(1L);
        }
        @Test
        @DisplayName("이미 등록된 메뉴가 있으면 예외가 발생한다.")
        void existMenu() {
            //given
            MenuCreateRequest request =  MenuCreateRequest.builder()
                    .name("메뉴")
                    .category(MenuCategory.NONE.name())
                    .price(BigDecimal.TEN)
                    .build();

            Menu 등록된_메뉴 = Menu.builder()
                    .category(MenuCategory.NONE)
                    .name(request.name())
                    .build();

            given(menuRepository.findByName(request.name())).willReturn(Optional.of(등록된_메뉴));

            //when & then
            assertThrows(DuplicationException.class, () -> {
                menuService.registerMenu(request);
            });
        }
    }

    @DisplayName("메뉴 조회")
    @Nested
    class MenuSearch {
        @DisplayName("없는 메뉴를 조회한다.")
        @Test
        void noExistMenu() {
            given(menuRepository.findById(0L)).willReturn(Optional.empty());

            assertThatThrownBy(() -> {
                menuService.searchMenu(0L);
            }).isInstanceOf(NotFoundException.class);
        }

        @DisplayName("메뉴를 조회한다")
        @Test
        void search() {
            //given
            Menu menu = Menu.builder().name("메뉴")
                            .category(MenuCategory.BEVERAGE)
                            .price(Price.valueOf(10))
                            .build();

            given(menuRepository.findById(1L)).willReturn(Optional.of(menu));
            ReflectionTestUtils.setField(menu, "id", 1L);

            //when
            MenuSearchResponse response = menuService.searchMenu(1L);

            //then
            assertSoftly((it) -> {
                it.assertThat(response.name()).isEqualTo("메뉴");
                it.assertThat(response.category()).isEqualTo("BEVERAGE");
                it.assertThat(response.price()).isEqualTo(BigDecimal.TEN);
            });
        }
    }

    @DisplayName("메뉴를 변경한다")
    @Nested
    class MenuChange {
        @DisplayName("메뉴가 존재하지 않는다.")
        @Test
        void noExistMenu() {
            //given
            given(menuRepository.findById(1L)).willReturn(Optional.empty());
            MenuChangeRequest request = new MenuChangeRequest(
                    JsonNullable.of("메뉴1"),
                    JsonNullable.of("카테고리"),
                    JsonNullable.undefined()
            );

            //when & then
            assertThatThrownBy(() -> menuService.changeMenu(1L, request))
                    .isInstanceOf(NotFoundException.class);
        }

        @DisplayName("중복된 메뉴이름이 존재하면 변경이 되지 않는다.")
        @Test
        void existMenuName() {
            //given
            Menu changeMenu = Menu.builder()
                    .name("메뉴")
                    .category(MenuCategory.BEVERAGE)
                    .price(Price.valueOf(10))
                    .build();

            MenuChangeRequest request = new MenuChangeRequest(
                    JsonNullable.of("중복된메뉴"),
                    JsonNullable.of("카테고리"),
                    JsonNullable.undefined()
            );

            ReflectionTestUtils.setField(changeMenu, "id", 1L);
            given(menuRepository.findById(1L)).willReturn(Optional.of(changeMenu));
            given(menuRepository.existsByNameAndIdNot("중복된메뉴", 1L)).willReturn(true);


            //when & then
            assertThatThrownBy(() -> menuService.changeMenu(1L, request))
                    .isInstanceOf(DuplicationException.class);
        }

        @DisplayName("메뉴의 이름이 변경된다.")
        @Test
        void changeMenuName() {
            //given
            Menu changeMenu = mock(Menu.class);

            MenuChangeRequest request = new MenuChangeRequest(
                    JsonNullable.of("메뉴2"),
                    JsonNullable.undefined(),
                    JsonNullable.undefined()
            );

            given(menuRepository.findById(1L)).willReturn(Optional.of(changeMenu));
            given(menuRepository.existsByNameAndIdNot(request.name().get(), 1L)).willReturn(false);

            //when
            final Long id = menuService.changeMenu(1L, request);

            //then
            assertSoftly((it) -> {
                verify(changeMenu).changeMenuName("메뉴2");
                it.assertThat(id).isEqualTo(1L);
            });
        }

        @Test
        @DisplayName("메뉴의 카테고리가 존재하지 않으면 변경이되지 않는다")
        void notFoundCategory() {
            //given
            Menu changeMenu = mock(Menu.class);

            MenuChangeRequest request = new MenuChangeRequest(
                    JsonNullable.undefined(),
                    JsonNullable.of("말도 안되는 카테고리"),
                    JsonNullable.undefined()
            );

            given(menuRepository.findById(1L)).willReturn(Optional.of(changeMenu));

            //when & then
            assertThatThrownBy(
                    () -> menuService.changeMenu(1L, request))
            .isInstanceOf(NotFoundException.class);
        }

        @Test
        @DisplayName("메뉴의 카테고리가 변경이 된다.")
        void changeMenuCategory() {
            //given
            Menu changeMenu = mock(Menu.class);

            MenuChangeRequest request = new MenuChangeRequest(
                    JsonNullable.undefined(),
                    JsonNullable.of(MenuCategory.FOOD.name()),
                    JsonNullable.undefined()
            );

            given(menuRepository.findById(1L)).willReturn(Optional.of(changeMenu));

            //when
            final Long menuId = menuService.changeMenu(1L, request);

            //then
            assertSoftly((it) -> {
                verify(changeMenu).changeMenuCategory(MenuCategory.FOOD);
                it.assertThat(menuId).isEqualTo(1L);
            });
        }

        @Test
        @DisplayName("메뉴의 가격이 상이하면 변경이 되지 않는다")
        void priceIsNotValidChange() {
            //given
            Menu changeMenu = mock(Menu.class);

            MenuChangeRequest request = new MenuChangeRequest(
                    JsonNullable.undefined(),
                    JsonNullable.undefined(),
                    JsonNullable.of(BigDecimal.valueOf(-1))
            );

            given(menuRepository.findById(1L)).willReturn(Optional.of(changeMenu));


            //when & then
            assertThatThrownBy(
                    () -> menuService.changeMenu(1L, request))
            .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("메뉴의 가격이 변경 된다")
        void menuPriceChange() {
            //given
            Menu changeMenu = mock(Menu.class);

            MenuChangeRequest request = new MenuChangeRequest(
                    JsonNullable.undefined(),
                    JsonNullable.undefined(),
                    JsonNullable.of(BigDecimal.valueOf(100))
            );

            given(menuRepository.findById(1L)).willReturn(Optional.of(changeMenu));

            //when
            final Long menuId = menuService.changeMenu(1L, request);

            //then
            assertSoftly((it) -> {
                verify(changeMenu).changePrice(Price.valueOf(BigDecimal.valueOf(100)));
                it.assertThat(menuId).isEqualTo(1L);
            });
        }

        @Test
        @DisplayName("메뉴의 정보가 변경 된다")
        void menuChange() {
            //given
            Menu changeMenu = mock(Menu.class);

            MenuChangeRequest request = new MenuChangeRequest(
                    JsonNullable.of("변경메뉴"),
                    JsonNullable.of(MenuCategory.COFFEE.name()),
                    JsonNullable.of(BigDecimal.valueOf(100))
            );

            given(menuRepository.findById(1L)).willReturn(Optional.of(changeMenu));
            given(menuRepository.existsByNameAndIdNot(request.name().get(), 1L)).willReturn(false);

            //when
            final Long menuId = menuService.changeMenu(1L, request);

            //then
            assertSoftly((it) -> {
                verify(changeMenu).changePrice(Price.valueOf(request.price().get()));
                verify(changeMenu).changeMenuName(request.name().get());
                verify(changeMenu).changeMenuCategory(MenuCategory.COFFEE);
                it.assertThat(menuId).isEqualTo(1L);
            });
        }
    }

}
