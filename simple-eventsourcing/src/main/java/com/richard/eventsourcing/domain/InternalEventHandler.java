package com.richard.eventsourcing.domain;

public interface InternalEventHandler<T> {

  void handle(T event);
}
