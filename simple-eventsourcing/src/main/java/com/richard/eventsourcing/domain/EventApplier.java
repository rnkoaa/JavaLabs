package com.richard.eventsourcing.domain;

import com.richard.eventsourcing.event.VersionedEvent;

@FunctionalInterface
public interface EventApplier {

  void apply(VersionedEvent event);
}
