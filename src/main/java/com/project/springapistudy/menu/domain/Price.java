package com.project.springapistudy.menu.domain;

import java.math.BigDecimal;
import java.util.Objects;


public class Price {
    private static final BigDecimal LIMIT_PRICE = new BigDecimal("0");
    private final BigDecimal value;

    private Price(BigDecimal value) {
        if (isLimitPriceIsLessThen(value)) {
            throw new IllegalArgumentException("가격은 0 보다 작을 수 없습니다.");
        }

        this.value = value;
    }

    private boolean isLimitPriceIsLessThen(BigDecimal value) {
        return LIMIT_PRICE.compareTo(value) > 0;
    }

    private Price(long value) {
        this(BigDecimal.valueOf(value));
    }

    private Price(double value) {
        this(BigDecimal.valueOf(value));
    }

    public static Price valueOf(long value) {
        return new Price(value);
    }

    public static Price valueOf(double value) {
        return new Price(value);
    }

    public static Price valueOf(BigDecimal value) {
        return new Price(value);
    }

    public BigDecimal getValue() {
        return value;
    }

    public boolean isLessThen(Price price) {
        return getValue().compareTo(price.getValue()) == -1;
    }

    public boolean isBigThen(Price price) {
        return getValue().compareTo(price.getValue()) == 1;
    }

    public boolean isEqualThen(Price price) {
        return getValue().compareTo(price.getValue()) == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Price price)) {
            return false;
        }

        return this.isEqualThen(price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
