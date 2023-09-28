package com.project.springapistudy.menu.ui;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.project.springapistudy.helper.TestHelper;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("메뉴 등록")
    class MenuRegister {

        @ParameterizedTest
        @DisplayName("가격은 음수이면 안된다.")
        @ValueSource(doubles = {-0.1, -1})
        void priceIsNotPositive(double value) throws Exception {
            final String requestBody = TestHelper.DTO를_JSON_문자열로_변환(MenuCreateRequest.builder()
                    .name("메뉴")
                    .category(MenuCategory.NONE.name())
                    .price(BigDecimal.valueOf(value)));

            mockMvc.perform(post("/menu")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("가격은 0원을 허용한다.")
        void priceIsZero() throws Exception {
            final String requestBody = TestHelper.DTO를_JSON_문자열로_변환(MenuCreateRequest.builder()
                    .name("가격이0원인메뉴")
                    .category(MenuCategory.NONE.name())
                    .price(BigDecimal.ZERO));

            mockMvc.perform(post("/menu")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andDo(print())
                    .andExpect(status().isCreated());
        }


        @Test
        @DisplayName("메뉴가 등록된다.")
        void registerMenu() throws Exception {
            final String requestBody = TestHelper.DTO를_JSON_문자열로_변환(MenuCreateRequest.builder()
                    .name("메뉴")
                    .category(MenuCategory.NONE.name())
                    .price(BigDecimal.TEN));

            mockMvc.perform(post("/menu")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", "/menu/1"));
        }
    }

}
