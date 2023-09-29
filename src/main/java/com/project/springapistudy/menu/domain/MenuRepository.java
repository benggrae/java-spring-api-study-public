package com.project.springapistudy.menu.domain;

import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository {
    Menu save(Menu menu);
    Optional<Menu> findByName(String name);

    Optional<Menu> findById(Long id);

    boolean existsByNameAndIdNot(String name, Long id);

    void deleteAll();
}
