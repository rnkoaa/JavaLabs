package com.thomsonreuters.spring.jpa.filtering;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by U0165547 on 7/23/2015.
 */
//only include non null objects
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchFilter {
    private MatchEnum match;
    private List<MatchFilter> filters = new ArrayList<>(0);

    public MatchEnum getMatch() {
        return match;
    }

    public void setMatch(MatchEnum match) {
        this.match = match;
    }

    public List<MatchFilter> getFilters() {
        return filters;
    }

    public void addFilter(MatchFilter filter) {
        filters.add(filter);
    }

    public void setFilters(List<MatchFilter> filters) {
        this.filters = filters;
    }
}
