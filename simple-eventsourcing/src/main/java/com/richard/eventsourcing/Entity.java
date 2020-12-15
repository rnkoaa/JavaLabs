package com.richard.eventsourcing;

import com.richard.eventsourcing.domain.EventApplier;
import com.richard.eventsourcing.domain.InternalEventHandler;
import com.richard.eventsourcing.event.VersionedEvent;

public abstract class Entity< U extends VersionedEvent> implements InternalEventHandler<U> {
  private final EventApplier applier;

  public Entity(EventApplier eventApplier) {
    this.applier = eventApplier;
  }

  public void apply(U event) {
    when(event);
    this.applier.apply(event);
  }

  public abstract void when(U event);

  @Override
  public void handle(U event) {
    when(event);
  }
}