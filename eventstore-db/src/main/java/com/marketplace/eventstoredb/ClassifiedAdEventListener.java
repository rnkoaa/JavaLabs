package com.marketplace.eventstoredb;

import com.google.common.eventbus.Subscribe;
import com.marketplace.eventstoredb.classifiedad.ClassifiedAdCreated;
import com.marketplace.eventstoredb.event.EventListener;

@SuppressWarnings("UnstableApiUsage")
public class ClassifiedAdEventListener implements EventListener {

    private final ClassifiedAdEventProcessor classifiedAdEventProcessor;

    public ClassifiedAdEventListener(ClassifiedAdEventProcessor eventProcessor) {
        this.classifiedAdEventProcessor = eventProcessor;
    }

    @Subscribe
    void on(ClassifiedAdCreated event) {
        var classifiedAd = classifiedAdEventProcessor.create(event);
        System.out.println(event);
    }

}
