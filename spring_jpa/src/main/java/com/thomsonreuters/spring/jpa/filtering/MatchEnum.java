package com.thomsonreuters.spring.jpa.filtering;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by U0165547 on 7/23/2015.
 */
public enum MatchEnum {

    ALL("all"), ANY("any"), NONE("none");

    private String name;

    MatchEnum(final String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }
}
