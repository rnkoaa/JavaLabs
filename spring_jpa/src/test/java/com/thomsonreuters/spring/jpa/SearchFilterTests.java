package com.thomsonreuters.spring.jpa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thomsonreuters.spring.jpa.filtering.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by U0165547 on 7/23/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
public class SearchFilterTests {

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testObjectMapper() throws JsonProcessingException {
        assertNotNull(objectMapper);
        SearchFilter searchFilter = new SearchFilter();
        searchFilter.setMatch(MatchEnum.ALL);

        MatchFilter matchFilter = new MatchFilter();
        matchFilter.setOperation(MatchOperation.EQ);
        matchFilter.setTerm("judicialArtifactUuid");
        matchFilter.setValue("I2b792a30edd711e4a980a44e31ac9784");

        searchFilter.addFilter(matchFilter);



        RangeFilter rangeFilter = new RangeFilter();
        rangeFilter.setOperation(MatchOperation.BETWEEN);
        rangeFilter.setBegin("2015-04-01'T'17:00:00Z");
        rangeFilter.setEnd("2015-08-01'T'17:00:00Z");

        matchFilter = new MatchFilter();
        matchFilter.setTerm("creationTime");
        matchFilter.setRange(rangeFilter);

        searchFilter.addFilter(matchFilter);

        rangeFilter = new RangeFilter();
        rangeFilter.setOperation(MatchOperation.BEFORE);
        rangeFilter.setValue("2015-03-01'T'17:00:00Z");

        matchFilter = new MatchFilter();
        matchFilter.setTerm("endTime");
        matchFilter.setRange(rangeFilter);

        searchFilter.addFilter(matchFilter);

        String filterString = objectMapper.writeValueAsString(searchFilter);

        assertNotNull(filterString);
        assertTrue(filterString.contains("all"));
        System.out.println(filterString);

    }
}
