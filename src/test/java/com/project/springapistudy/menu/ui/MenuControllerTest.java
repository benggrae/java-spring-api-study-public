package com.project.springapistudy.menu.ui;

import static com.project.springapistudy.helper.TestHelper.toJsonString;
import static com.project.springapistudy.helper.TestHelper.toObject;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.project.springapistudy.menu.domain.MenuCategory;
import com.project.springapistudy.menu.domain.MenuErrorCode;
import com.project.springapistudy.menu.domain.MenuRepository;
import com.project.springapistudy.menu.dto.MenuCreateRequest;
import com.project.springapistudy.menu.dto.MenuSearchResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    @Autowired
    private MenuRepository menuRepository;

    @Nested
    @DisplayName("메뉴 등록")
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
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.code").value(MenuErrorCode.MENU_NAME_IS_EXIST.getCode()))
            ;

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

    @Nested
    @DisplayName("메뉴 변경")
    class MenuChange {
        private MenuSearchResponse 메뉴1;
        private MenuSearchResponse 메뉴2;


        @BeforeEach
        void setUp() throws Exception{
            //given
            메뉴1 = 메뉴를_등록한다(MenuCreateRequest.builder()
                    .name("메뉴1")
                    .category(MenuCategory.NONE.name())
                    .price(BigDecimal.TEN)
                    .build());

            메뉴2 = 메뉴를_등록한다(MenuCreateRequest.builder()
                    .name("메뉴2")
                    .category(MenuCategory.NONE.name())
                    .price(BigDecimal.TEN)
                    .build());

        }
        @AfterEach
        void clear() {
            menuRepository.deleteAll();
        }




        @Test
        @DisplayName("메뉴의 이름이 기존의 이름과 중복되면 변경이 되지 않는다.")
        public void menuNameIsDuplicated() throws Exception {
            Map<String, Object> data = new HashMap<>(){{
                put("name", 메뉴2.name());
            }};

            mockMvc.perform(patch("/menu/" + 메뉴1.menuId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJsonString(data)))
                    .andExpect(status().isConflict());

        }
        @Test
        @DisplayName("없는 메뉴 카테고리")
        public void notFoundMenCategory() throws Exception {
            Map<String, Object> data = new HashMap<>(){{
                put("category", "없는 카테고리");
            }};

            mockMvc.perform(patch("/menu/" + 메뉴1.menuId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJsonString(data)))
                    .andExpect(status().isNotFound());

        }

        @Test
        @DisplayName("메뉴의 이름이 변경된다.")
        public void changeMenuName() throws Exception {
            Map<String, Object> data = new HashMap<>(){{
                put("name", "변경된메뉴");
            }};

            final MvcResult mvcResult = mockMvc.perform(patch("/menu/" + 메뉴1.menuId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJsonString(data)))
                    .andExpect(status().isCreated())
                    .andReturn();

            // when
            final String location = mvcResult.getResponse().getHeader(HttpHeaders.LOCATION);


            //when & then - 등록된 결과를 조회
            mockMvc.perform(get(location))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name" ).value("변경된메뉴"));
        }

        @Test
        @DisplayName("메뉴의 카테고리가 변경된다.")
        public void changeMenuCategory() throws Exception {
            Map<String, Object> data = new HashMap<>(){{
                put("category", MenuCategory.FOOD.name());
            }};

            final MvcResult mvcResult = mockMvc.perform(patch("/menu/" + 메뉴1.menuId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJsonString(data)))
                    .andExpect(status().isCreated())
                    .andReturn();

            // when
            final String location = mvcResult.getResponse().getHeader(HttpHeaders.LOCATION);


            //when & then - 등록된 결과를 조회
            mockMvc.perform(get(location))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.category" ).value(MenuCategory.FOOD.name()));
        }

        @Test
        @DisplayName("메뉴의 가격이 변경된다.")
        public void changeMenuPrice() throws Exception {
            Map<String, Object> data = new HashMap<>(){{
                put("price", 10);
            }};

            final MvcResult mvcResult = mockMvc.perform(patch("/menu/" + 메뉴1.menuId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJsonString(data)))
                    .andExpect(status().isCreated())
                    .andReturn();

            // when
            final String location = mvcResult.getResponse().getHeader(HttpHeaders.LOCATION);


            //when & then - 등록된 결과를 조회
            mockMvc.perform(get(location))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.price" ).value(10));
        }

        @Test
        @DisplayName("메뉴의 정보를 변경된다.")
        public void changeMenu() throws Exception {
            Map<String, Object> data = new HashMap<>(){{
                put("category", MenuCategory.FOOD.name());
                put("name", "변경된메뉴");
                put("price", 10);
            }};

            final MvcResult mvcResult = mockMvc.perform(patch("/menu/" + 메뉴1.menuId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJsonString(data)))
                    .andExpect(status().isCreated())
                    .andReturn();

            // when
            final String location = mvcResult.getResponse().getHeader(HttpHeaders.LOCATION);


            //when & then - 등록된 결과를 조회
            mockMvc.perform(get(location))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.category" ).value(data.get("category")))
                    .andExpect(jsonPath("$.name" ).value(data.get("name")))
                    .andExpect(jsonPath("$.price" ).value(data.get("price")));
        }


        private MenuSearchResponse 메뉴를_등록한다(MenuCreateRequest createRequest) throws Exception {
            // when & then
            var registerMenu = mockMvc.perform(post("/menu")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJsonString(createRequest)))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andReturn();

            var location = registerMenu.getResponse().getHeader(HttpHeaders.LOCATION);

            return toObject(mockMvc.perform(get(location))
                    .andReturn().getResponse(), MenuSearchResponse.class);
        }

    }

}
