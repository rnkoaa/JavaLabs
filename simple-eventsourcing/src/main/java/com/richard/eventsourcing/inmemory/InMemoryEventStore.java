package com.richard.eventsourcing.inmemory;

import com.richard.eventsourcing.EventStore;
import com.richard.eventsourcing.OperationResult;
import com.richard.eventsourcing.event.Event;
import com.richard.eventsourcing.mongo.EventStream;
import java.util.List;
import reactor.core.publisher.Mono;

public class InMemoryEventStore implements  EventStore<Event> {

  @Override
  public Mono<EventStream> load(String streamId) {
    return null;
  }

  @Override
  public Mono<EventStream> load(String streamId, int fromVersion) {
    return null;
  }

  @Override
  public Mono<OperationResult> append(String streamId, int expectedVersion, List<Event> events) {
    return null;
  }

  @Override
  public Mono<OperationResult> append(String streamId, int expectedVersion, Event event) {
    return null;
  }

  @Override
  public Mono<Long> size() {
    return null;
  }

  @Override
  public Mono<Long> streamSize(String streamId) {
    return null;
  }

  @Override
  public Mono<Integer> getVersion(String streamId) {
    return null;
  }

  @Override
  public Mono<Integer> nextVersion(String streamId) {
    return null;
  }

  @Override
  public Mono<OperationResult> publish(String streamId, Event event) {
    return null;
  }

  @Override
  public Mono<OperationResult> publish(String streamId, int expectedVersion, List<Event> events) {
    return null;
  }

  @Override
  public Mono<OperationResult> publish(String streamId, int expectedVersion, Event event) {
    return null;
  }
}
