package com.richard.annotations.domain;

import com.richard.annotations.Factory;

/**
 * Created by rnkoaa on 4/22/15.
 */
@Factory(
        id = "Calzone",
        type = Meal.class
)
public class CalzonePizza implements Meal {

    @Override public float getPrice() {
        return 8.5f;
    }
}