package com.project.springapistudy.web.controller;

import com.project.springapistudy.web.dto.MenuDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
public class MenuRestController {

    @PostMapping("")
    public String saveMenuApi(@RequestBody MenuDto dto) {

        return dto.menu();
    }

}
