package com.project.springapistudy.web.controller;

import com.project.springapistudy.service.MenuService;
import com.project.springapistudy.util.ResponseData;
import com.project.springapistudy.web.object.menu.MenuDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuRestController {

    private final MenuService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData.ApiResult<?> saveMenuApi(@RequestBody @Valid MenuDto dto) {
        return ResponseData.success(service.saveMenu(dto).of(), "저장 성공");
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData.ApiResult<?> getMenuByIdApi(@PathVariable Long id) {
        return ResponseData.success(service.findById(id).of(), "호출 성공");
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData.ApiResult<?> updateMenuByIdApi(@PathVariable Long id, @RequestBody @Valid MenuDto dto) {
        return ResponseData.success(service.updateMenu(id, dto), "수정 성공");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData.ApiResult<?> deleteMenuByIdApi(@PathVariable Long id) {
        service.deleteMenu(id);
        return ResponseData.success("삭제 성공");
    }
}
