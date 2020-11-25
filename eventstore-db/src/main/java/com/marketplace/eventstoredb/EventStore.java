package com.marketplace.eventstoredb;

import java.util.List;

public interface EventStore<T> {
  EventStream<T> load(String streamId);

  int version(String streamId);

  EventStream<T> load(String streamId, int fromVersion);

  boolean append(String streamId, int expectedVersion, List<Event> events);

  boolean append(String streamId, int expectedVersion, Event event);

  int size();
}
