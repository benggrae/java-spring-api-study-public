package com.project.springapistudy.menu.controller;

import com.project.springapistudy.menu.object.MenuVo;
import com.project.springapistudy.menu.service.MenuService;
import com.project.springapistudy.common.util.ResponseData;
import com.project.springapistudy.menu.object.MenuDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuRestController {

    private final MenuService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData.ApiResult<MenuVo> saveMenuApi(@RequestBody @Valid MenuDto dto) {
        return ResponseData.success(service.saveMenu(dto), "저장 성공");
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseData.ApiResult<List<MenuVo>> getAllMenuApi() {
        return ResponseData.success(service.findAll(), "호출 성공");
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData.ApiResult<MenuVo> getMenuByIdApi(@PathVariable Long id) {
        return ResponseData.success(service.findById(id), "호출 성공");
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData.ApiResult<Void> updateMenuByIdApi(@PathVariable Long id, @RequestBody @Valid MenuDto dto) {
        service.updateMenu(id, dto);
        return ResponseData.success(null, "수정 성공");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData.ApiResult<?> deleteMenuByIdApi(@PathVariable Long id) {
        service.deleteMenu(id);
        return ResponseData.success("삭제 성공");
    }
}
