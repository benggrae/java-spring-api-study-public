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

        if (existsByMenuName(dto.getMenuName())) {
            throw new DuplicationMenuException();
        }

        return MenuVo.fromEntity(menuRepository.save(dto.toEntity()));
    }

    @Transactional(readOnly = true)
    public boolean existsByMenuName(String menuName) {
        return menuRepository.existsByMenuName(menuName);
    }

    @Transactional(readOnly = true)
    public MenuVo findById(Long id) {
        return MenuVo.fromEntity(findEntityById(id));
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
                .orElseThrow(IdNotFoundException::new);
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
