package com.richard.annotations.domain;

import com.richard.annotations.Factory;

/**
 * Created by rnkoaa on 4/22/15.
 */
@Factory(
        id = "Tiramisu",
        type = Meal.class
)
public class Tiramisu implements Meal {

    @Override
    public float getPrice() {
        return 4.5f;
    }
}