package com.richard.eventsourcing.event;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.UUID;
import org.immutables.value.Value.Immutable;

@Immutable
@JsonDeserialize(as = ImmutableClassifiedAdCreated.class)
@JsonSerialize(as = ImmutableClassifiedAdCreated.class)
public interface ClassifiedAdCreated extends VersionedEvent {

  UUID getId();

  UUID getOwnerId();

}
