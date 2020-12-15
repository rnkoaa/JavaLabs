package com.richard.eventsourcing.domain;


import com.richard.eventsourcing.event.VersionedEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bson.codecs.pojo.annotations.BsonIgnore;

public abstract class AggregateRoot implements InternalEventHandler<VersionedEvent>, EventApplier {

  @BsonIgnore
  private final List<VersionedEvent> changes;
  private long version;

  protected AggregateRoot() {
    this.changes = new ArrayList<>();
    version = -1L;
  }

  public void apply(VersionedEvent event) {
    when(event);
    ensureValidState(event);
    changes.add(event);
    incrementVersion();
  }

  public void incrementVersion() {
    version++;
  }

  public abstract void ensureValidState(VersionedEvent event);

  public void clearChanges() {
    changes.clear();
  }

  public List<VersionedEvent> getChanges() {
    return changes;
  }

  public abstract void when(VersionedEvent event);

  protected void applyToEntity(InternalEventHandler<VersionedEvent> entity, VersionedEvent event) {
    entity.handle(event);
  }

  public long getVersion() {
    return version;
  }

  @Override
  public void handle(VersionedEvent event) {
    when(event);
  }

  public abstract UUID getAggregateId();

}
