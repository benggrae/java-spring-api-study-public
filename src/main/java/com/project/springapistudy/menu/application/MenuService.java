package com.project.springapistudy.menu.application;

import com.project.springapistudy.core.exceptions.DuplicationException;
import com.project.springapistudy.menu.ui.dto.MenuCreateRequest;
import com.project.springapistudy.menu.domain.Menu;
import com.project.springapistudy.menu.domain.MenuRepository;
import com.project.springapistudy.menu.domain.Price;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    public Long registerMenu(MenuCreateRequest request) {
        if (menuRepository.findByName(request.name()).isPresent()) {
            throw new DuplicationException("중복된 메뉴가 있습니다");
        }

        final Menu menu = Menu.builder()
                .name(request.name())
                .price(Price.valueOf(request.price()))
                .category(request.category())
                .build();

        return menuRepository.save(menu).getId();
    }
}
