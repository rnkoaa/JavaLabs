package com.afrancho.application;

import com.afrancho.annotations.Logger;

@Logger(values = "hello")
public class Greeter {
    public void greet() {
        System.out.println("Hello, World!");
    }
}
