package com.marketplace.eventstoredb;

import com.marketplace.eventstoredb.classifiedad.ClassifiedAdCreated;
import com.marketplace.eventstoredb.event.EventProcessor;
import com.marketplace.eventstoredb.query.ClassifiedAdQueryModel;

public class ClassifiedAdEventProcessor implements EventProcessor {

    public ClassifiedAdQueryModel create(ClassifiedAdCreated event) {
       return new ClassifiedAdQueryModel(event.aggregateId(), event.getOwnerId());
    }
}
