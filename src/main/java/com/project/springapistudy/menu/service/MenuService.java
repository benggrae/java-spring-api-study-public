package com.project.springapistudy.menu.service;

import com.project.springapistudy.menu.domain.Menu;
import com.project.springapistudy.menu.domain.MenuRepository;
import com.project.springapistudy.menu.object.MenuDto;
import com.project.springapistudy.common.exception.DuplicationMenuException;
import com.project.springapistudy.common.exception.IdNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    @Transactional
    public Menu saveMenu(MenuDto dto) {
        menuRepository.findByMenuName(dto.getMenuName())
                .ifPresent(menu -> {
                    throw new DuplicationMenuException(menu.getMenuName() + " 메뉴는 이미 등록되어 있습니다.");
                });
        return menuRepository.save(Menu.builder()
                .menuName(dto.getMenuName())
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
        return menuRepository.save(currentMenu.updateBasicInfo(dto.getMenuName()));
    }

    @Transactional
    public void deleteMenu(Long id) {
        menuRepository.delete(findById(id));
    }
}
