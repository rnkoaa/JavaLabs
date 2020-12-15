package com.richard.eventsourcing.domain;

public record Price(Money money) {
    public Price {
        if (money == null || money.amount().doubleValue() < 0) {
            throw new IllegalArgumentException("Price cannot be less than 0");
        }
    }

    @Override
    public String toString() {
        return money.toString();
    }
}
