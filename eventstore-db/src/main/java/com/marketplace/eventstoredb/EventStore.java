package com.marketplace.eventstoredb;

import java.util.List;

public interface EventStore<T> {

  EventStream<T> load(String streamId);

  EventStream<T> load(String streamId, int fromVersion);

  AppendResult append(String streamId, int expectedVersion, List<T> events);

  AppendResult append(String streamId, int expectedVersion, T event);

  int size();

  int streamSize(String streamId);

  int getVersion(String streamId);

  int nextVersion(String streamId);
}
