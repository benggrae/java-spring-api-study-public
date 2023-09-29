package com.project.springapistudy.menu.application;

import com.project.springapistudy.global.exception.DuplicationException;
import com.project.springapistudy.global.exception.NotFoundException;
import com.project.springapistudy.menu.domain.Menu;
import com.project.springapistudy.menu.domain.MenuCategory;
import com.project.springapistudy.menu.domain.MenuErrorCode;
import com.project.springapistudy.menu.domain.MenuRepository;
import com.project.springapistudy.menu.domain.Price;
import com.project.springapistudy.menu.dto.MenuChangeRequest;
import com.project.springapistudy.menu.dto.MenuCreateRequest;
import com.project.springapistudy.menu.dto.MenuSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {
    private final MenuRepository menuRepository;

    public Long registerMenu(MenuCreateRequest request) {
        if (menuRepository.findByName(request.name()).isPresent()) {
            throw new DuplicationException(MenuErrorCode.MENU_NAME_IS_EXIST);
        }

        final Menu menu = Menu.builder()
                .name(request.name())
                .price(Price.valueOf(request.price()))
                .category(MenuCategory.of(request.category()))
                .build();


        return menuRepository.save(menu).getId();
    }

    @Transactional(readOnly = true)
    public MenuSearchResponse searchMenu(Long id) {
        final Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MenuErrorCode.MENU_NOT_FOUND));

        return MenuSearchResponse.builder()
                .menuId(menu.getId())
                .name(menu.getName())
                .category(menu.getCategory().name())
                .price(menu.getPrice().getValue())
                .build();
    }

    public Long changeMenu(Long id, MenuChangeRequest request) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MenuErrorCode.MENU_NOT_FOUND));

        request.name().ifPresent((name) -> {
            if (menuRepository.existsByNameAndIdNot(name, id)) {
                throw new DuplicationException(MenuErrorCode.MENU_NAME_IS_EXIST);
            }
            menu.changeMenuName(name);
        });

        request.category().ifPresent((category) ->
                menu.changeMenuCategory(MenuCategory.of(category)));

        request.price().ifPresent((price) ->
                menu.changePrice(Price.valueOf(price)));

        return id;
    }
}
