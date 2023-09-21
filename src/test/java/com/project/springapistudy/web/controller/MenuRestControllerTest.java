package com.project.springapistudy.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.project.springapistudy.domain.menu.Menu;
import com.project.springapistudy.domain.menu.MenuRepository;
import com.project.springapistudy.service.MenuService;
import com.project.springapistudy.util.ResponseData;
import com.project.springapistudy.web.dto.MenuDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(MenuRestController.class)
@MockBean(JpaMetamodelMappingContext.class)
class MenuRestControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private MenuService menuService;

    @MockBean
    private MenuRepository menuRepository;

    @Autowired
    private Gson gson;

    @Test
    @DisplayName("메뉴 저장 컨트롤러 로직 확인 테스트")
    void postMenuTest() throws Exception {
        // given
        MenuDto dto = new MenuDto(1L, "아이스 아메리카노");
        String content = gson.toJson(dto);
        Menu menu = Menu.builder()
                .id(1L)
                .menuName(dto.menuName())
                .build();

        given(menuService.saveMenu(dto)).willReturn(menu);

        // when
        ResultActions actions = mockMvc.perform(
                post("/menu")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        // then
        actions
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.data.menuName").value(dto.menuName()));
    }
}