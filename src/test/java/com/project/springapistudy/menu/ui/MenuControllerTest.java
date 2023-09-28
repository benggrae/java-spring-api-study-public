package com.project.springapistudy.menu.ui;

import static com.project.springapistudy.helper.TestHelper.toJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.project.springapistudy.menu.domain.MenuCategory;
import com.project.springapistudy.menu.dto.MenuCreateRequest;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@DisplayName("메뉴를 관리 한다.")
class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("메뉴 등록 후 조회를 한다.")
    class MenuRegister {

        @ParameterizedTest
        @DisplayName("가격은 음수이면 안된다.")
        @ValueSource(doubles = {-0.1, -1})
        void priceIsNotPositive(double value) throws Exception {
            MenuCreateRequest request = MenuCreateRequest.builder()
                    .name("메뉴")
                    .category(MenuCategory.NONE.name())
                    .price(BigDecimal.valueOf(value))
                    .build();

            mockMvc.perform(post("/menu")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJsonString(request)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("가격은 0원을 허용한다.")
        void priceIsZero() throws Exception {
            MenuCreateRequest request = MenuCreateRequest.builder()
                    .name("가격이 0원인 메뉴")
                    .category(MenuCategory.NONE.name())
                    .price(BigDecimal.ZERO)
                    .build();

            mockMvc.perform(post("/menu")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJsonString(request)))
                    .andDo(print())
                    .andExpect(status().isCreated());
        }

        @Test
        @DisplayName("중복된 메뉴 등록")
        void duplicateMenuRegister() throws Exception {
            //given
            MenuCreateRequest request = MenuCreateRequest.builder()
                    .name("중복된 메뉴")
                    .category(MenuCategory.NONE.name())
                    .price(BigDecimal.ZERO)
                    .build();

            //when & then
            mockMvc.perform(post("/menu")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJsonString(request)))
                    .andExpect(status().isCreated());

            //when & then
            mockMvc.perform(post("/menu")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJsonString(request)))
                    .andExpect(status().isConflict());
        }


        @Test
        @DisplayName("메뉴가 등록 된다.")
        void registerMenu() throws Exception {
            //given
            MenuCreateRequest request = MenuCreateRequest.builder()
                    .name("메뉴")
                    .category(MenuCategory.NONE.name())
                    .price(BigDecimal.TEN)
                    .build();

            // when & then
            final MvcResult result = mockMvc.perform(post("/menu")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJsonString(request)))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andReturn();

            // when
            final String location = result.getResponse().getHeader(HttpHeaders.LOCATION);


            //when & then - 등록된 결과를 조회
            mockMvc.perform(get(location))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name" ).value("메뉴"))
                    .andExpect(jsonPath("$.category" ).value("NONE"))
                    .andExpect(jsonPath("$.price").value(10));

        }

    }

}
