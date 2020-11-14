package com.dagger;

import javax.inject.Inject;

import static java.lang.String.format;

public class PetrolEngine implements Engine {
    private final int horsePower;

    @Inject
    public PetrolEngine(int horsePower) {
        this.horsePower = horsePower;
    }

    @Override
    public void start() {
        String message = format("Petrol Engine started, HorsePower: %s...", horsePower);
        System.out.println(message);
    }
}
