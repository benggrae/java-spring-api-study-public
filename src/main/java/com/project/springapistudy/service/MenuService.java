package com.project.springapistudy.service;

import com.project.springapistudy.domain.menu.Menu;
import com.project.springapistudy.domain.menu.MenuRepository;
import com.project.springapistudy.web.dto.MenuDto;
import com.project.springapistudy.web.exception.DuplicationMenuException;
import com.project.springapistudy.web.exception.IdNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    @Transactional
    public Menu saveMenu(MenuDto dto) {
        menuRepository.findByMenuName(dto.menuName())
                .ifPresent(menu -> {
                    throw new DuplicationMenuException(menu.getMenuName() + " 메뉴는 이미 등록되어 있습니다.");
                });
        return menuRepository.save(Menu.builder()
                .menuName(dto.menuName())
                .build());
    }

    @Transactional(readOnly = true)
    public Menu findById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(id + "번 음료를 찾을 수 없습니다."));
    }

    @Transactional
    public Menu updateMenu(Long id, MenuDto dto) {
        Menu currentMenu = findById(id);
        return menuRepository.save(currentMenu.updateBasicInfo(dto.menuName()));
    }
}
