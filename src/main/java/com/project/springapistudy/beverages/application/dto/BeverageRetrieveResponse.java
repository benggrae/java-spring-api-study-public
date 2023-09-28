package com.project.springapistudy.beverages.application.dto;

import com.project.springapistudy.beverages.domain.Beverage;

import java.time.LocalDateTime;

public class BeverageRetrieveResponse {

    private long id;

    private String name;

    private long price;

    private LocalDateTime created;

    private BeverageRetrieveResponse() {
    }

    public static BeverageRetrieveResponse of(Beverage findItem) {
        BeverageRetrieveResponse retrieveResponse = new BeverageRetrieveResponse();
        retrieveResponse.id = findItem.getId();
        retrieveResponse.name = findItem.getName();
        retrieveResponse.price = findItem.getPrice();
        retrieveResponse.created = findItem.getCreated();

        return retrieveResponse;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public LocalDateTime getCreated() {
        return created;
    }
}
