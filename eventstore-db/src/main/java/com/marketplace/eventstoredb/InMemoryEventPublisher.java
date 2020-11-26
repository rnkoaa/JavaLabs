package com.marketplace.eventstoredb;

import com.google.common.eventbus.EventBus;
import com.marketplace.eventstoredb.event.EventListener;
import com.marketplace.eventstoredb.event.EventPublisher;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class InMemoryEventPublisher implements EventPublisher<Event>, AutoCloseable {

    private final EventBus eventBus;
    private final List<EventListener> listeners;

    public InMemoryEventPublisher(EventBus eventBus) {
        this.eventBus = eventBus;
        this.listeners = new ArrayList<>();
    }

    void registerListener(EventListener eventListener) {
        this.eventBus.register(eventListener);
        this.listeners.add(eventListener);
    }

    @Override
    public void publish(String streamId, Event event) {
        eventBus.post(event);
    }

    @Override
    public void publish(String streamId, List<Event> event) {
        event.forEach(eventBus::post);
    }

    @Override
    public void close() {
        this.listeners.forEach(eventBus::unregister);
    }
}
