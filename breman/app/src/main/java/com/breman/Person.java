package com.breman;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@ToString
public class Person {

    @JsonProperty
    UserId userId;

    @JsonProperty
    String firstName;

    public Person() {
    }

    public Person(UserId userId, String firstName) {
        this.userId = userId;
        this.firstName = firstName;
    }
}
