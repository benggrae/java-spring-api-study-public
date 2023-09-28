package com.project.springapistudy.beverage.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.util.Map;

@SuppressWarnings("NonAsciiCharacters")
public class BeverageSteps {
    public static ExtractableResponse<Response> 제조음료_생성_API(final Map<String, String> request) {
        return RestAssured.given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/beverage")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 제조음료_조회_API(String location) {
        return RestAssured.given().log().all()
                .when().get(location)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 제조음료_수정_API(String location, final Map<String, String> request) {
        return RestAssured.given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put(location)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 제조음료_삭제_API(String location) {
        return RestAssured.given().log().all()
                .when().delete(location)
                .then().log().all()
                .extract();
    }
}
