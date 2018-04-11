package com.richard.annotations.domain;

import com.richard.annotations.Factory;

/**
 * Created by rnkoaa on 4/22/15.
 */
@Factory(
        id = "Margherita",
        type = Meal.class
)
public class MargheritaPizza implements Meal {

    @Override public float getPrice() {
        return 6.0f;
    }
}