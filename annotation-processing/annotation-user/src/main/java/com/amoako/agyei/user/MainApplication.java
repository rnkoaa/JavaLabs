package com.amoako.agyei.user;

public class MainApplication {
    public static void main(String[] args) {
        System.out.println("Started.");
        PersonBuilder builder = new PersonBuilder();
        Person person = builder.setAge(0).setName("Richard").build();
        System.out.println(person);
    }
}
