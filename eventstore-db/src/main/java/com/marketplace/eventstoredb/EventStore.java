package com.marketplace.eventstoredb;

import java.util.List;
import java.util.UUID;

public interface EventStore<T> {
  EventStream<EventEntity> load(String streamId);

  EventStream<Event> load(String streamId, int fromVersion);

  boolean append(String streamId, int expectedVersion, List<Event> events);

  boolean append(String streamId, int expectedVersion, Event event);
}
