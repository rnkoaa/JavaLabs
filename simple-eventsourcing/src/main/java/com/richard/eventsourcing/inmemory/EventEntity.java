package com.richard.eventsourcing.inmemory;

import com.richard.eventsourcing.event.Event;
import org.immutables.value.Value;

@Value.Immutable
public interface EventEntity {

  EventMetadata getMetadata();

  Event getEvent();

}
