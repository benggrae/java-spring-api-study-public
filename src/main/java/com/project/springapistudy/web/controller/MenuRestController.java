package com.project.springapistudy.web.controller;

import com.project.springapistudy.domain.menu.Menu;
import com.project.springapistudy.service.MenuService;
import com.project.springapistudy.util.ResponseData;
import com.project.springapistudy.web.dto.MenuDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuRestController {

    private final MenuService service;

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData.ApiResult<?> saveMenuApi(@RequestBody MenuDto dto) {
        return ResponseData.success(service.saveMenu(dto), "저장 성공");
    }

}
