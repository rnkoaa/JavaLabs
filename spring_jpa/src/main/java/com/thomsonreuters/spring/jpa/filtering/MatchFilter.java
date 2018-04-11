package com.thomsonreuters.spring.jpa.filtering;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by U0165547 on 7/23/2015.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchFilter {
    private String term;
    private String value;
    MatchOperation operation;
    RangeFilter range;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MatchOperation getOperation() {
        return operation;
    }

    public void setOperation(MatchOperation operation) {
        this.operation = operation;
    }

    public RangeFilter getRange() {
        return range;
    }

    public void setRange(RangeFilter range) {
        this.range = range;
    }
}
