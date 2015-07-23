package com.thomsonreuters.spring.jpa.filtering;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by U0165547 on 7/23/2015.
 */
public enum MatchOperation {
    EQ("eq"), NEQ("neq"), LIKE("like"), LT("lt"), LTE("lte"), GT("gt"),
    GTE("gte"), BEFORE("before"), BETWEEN("between"), AFTER("after");

    private String name;

    MatchOperation(final String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }
}
