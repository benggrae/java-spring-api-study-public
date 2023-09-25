package com.project.springapistudy.menu.domain;

import java.util.Optional;

public interface MenuRepository {
    Menu save(Menu menu);
    Optional<Menu> findByName(String name);
}
