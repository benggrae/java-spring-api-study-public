package com.project.springapistudy.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.springapistudy.web.dto.MenuDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MenuRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(MenuRestController.class).build();
    }

    MenuDto makeDto() {
        return new MenuDto("아이스 아메리카노");
    }

    @Test
    @DisplayName("메뉴 저장 테스트")
    void saveMenuTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .post("/menu")
                .content(mapper.writeValueAsBytes(makeDto()))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

}