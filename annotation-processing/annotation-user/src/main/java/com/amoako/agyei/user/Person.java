package com.amoako.agyei.user;

import com.amoako.agyei.annotation.BuilderProperty;

public class Person {
    private int age;

    private String name;

    @BuilderProperty
    public void setAge(int age) {
        this.age = age;
    }

    @BuilderProperty
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Person{" + "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
