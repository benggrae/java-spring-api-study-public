package com.project.springapistudy.beverage.acceptance;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("NonAsciiCharacters")
public class BeverageFixture {
    public static Map<String, String> 제조음료_등록_요청(final String name, final long price) {
        Map<String, String> 요청 = new HashMap<>();
        요청.put("name", name);
        요청.put("price", String.valueOf(price));
        return 요청;
    }

    public static Map<String, String> 제조음료_수정_요청(final String name, final long price) {
        Map<String, String> 요청 = new HashMap<>();
        요청.put("name", name);
        요청.put("price", String.valueOf(price));
        return 요청;
    }
}
