package com.project.springapistudy.menu.service;

import com.project.springapistudy.common.exception.DuplicationMenuException;
import com.project.springapistudy.common.exception.IdNotFoundException;
import com.project.springapistudy.menu.domain.Menu;
import com.project.springapistudy.menu.domain.MenuRepository;
import com.project.springapistudy.menu.object.MenuDto;
import com.project.springapistudy.menu.object.MenuVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    @Transactional
    public MenuVo saveMenu(MenuDto dto) {
        menuRepository.findByMenuName(dto.getMenuName())
                .ifPresent(menu -> {
                    throw new DuplicationMenuException(menu.getMenuName() + " 메뉴는 이미 등록되어 있습니다.");
                });
        return MenuVo.fromEntity(menuRepository.save(dto.toEntity()));
    }

    @Transactional(readOnly = true)
    public MenuVo findById(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(id + "번 음료를 찾을 수 없습니다."));
        return MenuVo.fromEntity(menu);
    }

    public List<MenuVo> findAll() {
        List<Menu> list = menuRepository.findAllByUseYN("N");
        return list.stream()
                .map(MenuVo::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public Menu findEntityById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(id + "번 음료를 찾을 수 없습니다."));
    }

    @Transactional
    public void updateMenu(Long id, MenuDto dto) {
        Menu currentMenu = findEntityById(id);
        currentMenu.updateBasicInfo(dto.getMenuName());
    }

    @Transactional
    public void deleteMenu(Long id) {
        findEntityById(id).remove();
    }
}
