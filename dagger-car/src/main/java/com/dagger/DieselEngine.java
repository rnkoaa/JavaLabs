package com.dagger;

import javax.inject.Inject;
import javax.inject.Named;

import static java.lang.String.format;

public class DieselEngine implements Engine {
    private final int horsePower;
    private final int engineCapacity;

    @Inject
    public DieselEngine(@Named("horsePower") int horsePower,
                        @Named("engineCapacity") int engineCapacity) {
        this.horsePower = horsePower;
        this.engineCapacity = engineCapacity;
    }

    @Override
    public void start() {
        String message = format("Diesel Engine started, HorsePower: %d, engineCapacity: %d...",
                horsePower, engineCapacity);
        System.out.println(message);
    }
}
