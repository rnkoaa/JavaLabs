package com.marketplace.eventstoredb.event;

import java.time.Instant;
import java.util.List;

public interface EventStream<T> {
  String getName();

  String getId();

  int getVersion();

  List<T> getEvents();

  default Instant createdAt() {
    return Instant.now();
  }

  default Instant updatedAt() {
    return Instant.now();
  }

  void append(T entity, int expectedVersion);

  int size();
}
