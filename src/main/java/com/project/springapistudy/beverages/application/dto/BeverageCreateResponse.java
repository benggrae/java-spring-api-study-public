package com.project.springapistudy.beverages.application.dto;

import com.project.springapistudy.beverages.domain.Beverage;

public class BeverageCreateResponse {
    private long id;

    private BeverageCreateResponse() {
    }

    public static BeverageCreateResponse of(Beverage beverage) {
        BeverageCreateResponse response = new BeverageCreateResponse();
        response.id = beverage.getId();
        return response;
    }

    public long getId() {
        return id;
    }
}
