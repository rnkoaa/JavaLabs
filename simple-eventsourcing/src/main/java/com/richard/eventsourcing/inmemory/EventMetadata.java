package com.richard.eventsourcing.inmemory;

import org.immutables.value.Value;

@Value.Immutable
public interface EventMetadata {

  String getAggregate();

  String getType();

}
