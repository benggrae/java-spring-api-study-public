package com.project.springapistudy.menu.ui;


import static org.springframework.web.util.UriComponentsBuilder.fromPath;

import com.project.springapistudy.menu.application.MenuService;
import com.project.springapistudy.menu.dto.MenuChangeRequest;
import com.project.springapistudy.menu.dto.MenuCreateRequest;
import com.project.springapistudy.menu.dto.MenuSearchResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

        return ResponseEntity.created(
                    fromPath("/menu/{id}").build(menuId))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuSearchResponse> searchMenu(@PathVariable("id") Long id) {
        return ResponseEntity.ok()
                .body(menuService.searchMenu(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> changeMenu(@PathVariable("id") Long id, @RequestBody MenuChangeRequest request) {
        final Long changeId = menuService.changeMenu(id, request);

        return ResponseEntity.created(
                    fromPath("/menu/{id}").build(changeId))
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> changeMenu(@PathVariable("id") Long id) {
        menuService.deleteMenu(id);

        return ResponseEntity.ok()
                .build();
    }
}
