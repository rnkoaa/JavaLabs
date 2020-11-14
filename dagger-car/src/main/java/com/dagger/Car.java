package com.dagger;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Car {

    private final Wheel wheel;
    private final Engine engine;

    @Inject
    Car(Wheel wheel, Engine engine) {
        this.wheel = wheel;
        this.engine = engine;
    }

    public void drive() {
        wheel.run();
        engine.start();
        System.out.println(this + " Started.");

    }
}
