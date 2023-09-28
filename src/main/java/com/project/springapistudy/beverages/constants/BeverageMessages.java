package com.project.springapistudy.beverages.constants;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BeverageMessages {
    BEVERAGE_NOT_FOUND(1000L, "음료를 찾을 수 없습니다"),
    BEVERAGE_ALREADY_NAME(1001L, "같은 이름의 음료가 있습니다.");

    private final long key;
    private final String name;

    public long getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}
