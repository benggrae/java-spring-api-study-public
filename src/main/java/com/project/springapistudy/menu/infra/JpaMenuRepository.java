package com.project.springapistudy.menu.infra;

import com.project.springapistudy.menu.domain.Menu;
import com.project.springapistudy.menu.domain.MenuRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMenuRepository extends MenuRepository, JpaRepository<Menu, Long> {
}
