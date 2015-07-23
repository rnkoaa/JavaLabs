package com.thomsonreuters.spring.jpa.filtering;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by U0165547 on 7/23/2015.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RangeFilter {
    MatchOperation operation;
    String begin;
    String end;
    private String value;

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

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
