package com.marketplace.eventstoredb.event;

import java.util.List;

public interface EventPublisher<T> {

    void publish(String streamId, T event);

    void publish(String streamId, List<T> event);
}
