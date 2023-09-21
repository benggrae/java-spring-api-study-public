package com.project.springapistudy.service;

import com.project.springapistudy.domain.menu.Menu;
import com.project.springapistudy.domain.menu.MenuRepository;
import com.project.springapistudy.web.dto.MenuDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    @Transactional
    public Menu saveMenu(MenuDto dto) {
        return menuRepository.save(Menu.builder()
                .menuName(dto.menuName())
                .build());
    }

}
