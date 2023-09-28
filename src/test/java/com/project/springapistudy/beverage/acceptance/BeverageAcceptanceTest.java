package com.project.springapistudy.beverage.acceptance;

import com.project.springapistudy.utils.SirenOrderApplicationTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("제조음료 인수 테스트")
public class BeverageAcceptanceTest extends SirenOrderApplicationTest {

    @DisplayName("제조음료 테스트")
    @Nested
    class Beverage {
        @DisplayName("제조음료 등록")
        @Nested
        class BeverageCreate {
            @DisplayName("제조음료를 등록할 수 있다.")
            @Test
            void 제조음료_등록() {
                // given
                var 요청 = BeverageFixture.제조음료_등록_요청("뜨거운 아이스 아메리카노", 2500L);

                // when
                var extract = BeverageSteps.제조음료_생성_API(요청);

                // then
                assertThat(extract.statusCode()).isEqualTo(HttpStatus.CREATED.value());
            }

            @DisplayName("동일한 이름의 제조음료를 등록할 수 없다.")
            @Test
            void 동일한_제조음료_등록() {
                // given
                var 요청 = BeverageFixture.제조음료_등록_요청("뜨거운 아이스 아메리카노", 2500L);

                // when
                BeverageSteps.제조음료_생성_API(요청);
                var extract = BeverageSteps.제조음료_생성_API(요청);

                // then
                assertThat(extract.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            }
        }

        @DisplayName("제조음료 조회")
        @Nested
        class BeverageRetrieve {

            private final String name = "아이스 도피오";
            ExtractableResponse<Response> response;

            @BeforeEach
            public void 제조음료_조회_설정() {
                var 요청 = BeverageFixture.제조음료_등록_요청(name, 3000L);
                response = BeverageSteps.제조음료_생성_API(요청);
            }

            @DisplayName("등록된 제조음료를 조회할 수 있다.")
            @Test
            void 제조음료_조회() {
                //given
                var 등록_리소스 = response.header("Location");

                // when
                var extract = BeverageSteps.제조음료_조회_API(등록_리소스);

                // then
                String 응답_이름 = extract.jsonPath().getString("name");
                assertThat(응답_이름).isEqualTo(name);
            }

            @DisplayName("등록되지 않은 제조음료는 조회할 수 없다.")
            @Test
            void 미등록_제조음료_조회() {
                //given
                var 등록_리소스 = "/beverage/999";

                // when
                var extract = BeverageSteps.제조음료_조회_API(등록_리소스);

                // then
                assertThat(extract.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
            }
        }

        @DisplayName("제조음료 수정")
        @Nested
        class BeverageUpdate {

            ExtractableResponse<Response> response;

            @BeforeEach
            public void 제조음료_수정_설정() {
                var 요청 = BeverageFixture.제조음료_등록_요청("아이스 카푸치노", 2500L);
                response = BeverageSteps.제조음료_생성_API(요청);
            }

            @DisplayName("등록된 제조음료의 이름과 가격을 수정할 수 있다.")
            @Test
            void 제조음료_수정() {
                // given
                final String 변경이름 = "미역국 라떼";
                final long 변경가격 = 5000L;
                String 제조음료_리소스 = response.header("Location");

                var 변경_요청 = BeverageFixture.제조음료_수정_요청(변경이름, 변경가격);

                // when
                var updateResponse = BeverageSteps.제조음료_수정_API(제조음료_리소스, 변경_요청);

                // then
                assertThat(updateResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
            }

            @DisplayName("등록되지 않은 제조음료는 수정할 수 없다.")
            @Test
            void 미등록_제조음료_수정() {
                // given
                final String 변경이름 = "미역국 라떼";
                final long 변경가격 = 5000L;

                var 변경_요청 = BeverageFixture.제조음료_수정_요청(변경이름, 변경가격);

                // when
                var updateResponse = BeverageSteps.제조음료_수정_API("/beverage/999", 변경_요청);

                // then
                assertThat(updateResponse.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
            }
        }

        @DisplayName("제조음료 삭제")
        @Nested
        class BeverageDelete {
            ExtractableResponse<Response> response;

            @BeforeEach
            public void 제조음료_삭제_설정() {
                var 요청 = BeverageFixture.제조음료_등록_요청("뜨거운 콜드브루", 4000L);
                response = BeverageSteps.제조음료_생성_API(요청);
            }

            @DisplayName("등록된 제조음료는 삭제할 수 있다.")
            @Test
            void 제조음료_삭제() {
                // given
                String 제조음료_리소스 = response.header("Location");

                // when
                var deleteResponse = BeverageSteps.제조음료_삭제_API(제조음료_리소스);

                // then
                assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
            }

            @DisplayName("삭제된 제조음료는 조회되지 않는다.")
            @Test
            void 제조음료_삭제_후_조회() {
                // given
                String 제조음료_리소스 = response.header("Location");

                // when
                BeverageSteps.제조음료_삭제_API(제조음료_리소스);
                var retrieveResponse = BeverageSteps.제조음료_조회_API(제조음료_리소스);

                // then
                assertThat(retrieveResponse.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
            }

            @DisplayName("등록되지 않은 제조음료는 삭제할 수 없다.")
            @Test
            void 미등록_제조음료_삭제() {
                // given/when
                var deleteResponse = BeverageSteps.제조음료_삭제_API("/beverage/999");

                // then
                assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
            }


        }

    }


}
