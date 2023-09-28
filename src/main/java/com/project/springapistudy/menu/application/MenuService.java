package com.project.springapistudy.menu.application;

import com.project.springapistudy.global.exception.DuplicationException;
import com.project.springapistudy.menu.domain.Menu;
import com.project.springapistudy.menu.domain.MenuErrorCode;
import com.project.springapistudy.menu.domain.MenuRepository;
import com.project.springapistudy.menu.domain.Price;
import com.project.springapistudy.menu.dto.MenuCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    public Long registerMenu(MenuCreateRequest request) {
        if (menuRepository.findByName(request.name()).isPresent()) {
            throw new DuplicationException(MenuErrorCode.MENU_IS_NOT_EMPTY_NAME);
        }

        final Menu menu = Menu.builder()
                .name(request.name())
                .price(Price.valueOf(request.price()))
                .category(request.category())
                .build();

        return menuRepository.save(menu).getId();
    }
}
