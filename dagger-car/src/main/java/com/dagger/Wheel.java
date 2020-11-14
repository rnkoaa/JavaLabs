package com.dagger;

import javax.inject.Inject;

public class Wheel {

    @Inject
    public Wheel() {
    }

    public void run(){
        System.out.println("wheels running...");
    }
}
