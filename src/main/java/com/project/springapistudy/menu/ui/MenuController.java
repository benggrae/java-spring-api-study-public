package com.project.springapistudy.menu.ui;


import static org.springframework.web.util.UriComponentsBuilder.fromPath;

import com.project.springapistudy.menu.application.MenuService;
import com.project.springapistudy.menu.dto.MenuCreateRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/menu")
public class MenuController {
    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<Void> createMenu(@Valid @RequestBody MenuCreateRequest request) {
        final Long menuId = menuService.registerMenu(request);

        return ResponseEntity.created(fromPath("/menu/{id}")
                .build(menuId))
                .build();
    }
}
