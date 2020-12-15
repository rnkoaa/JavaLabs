package com.richard.eventsourcing.event;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.UUID;
import org.immutables.value.Value.Immutable;

@Immutable
@JsonSerialize(as = ImmutableClassifiedApproved.class)
@JsonDeserialize(as = ImmutableClassifiedApproved.class)
public interface ClassifiedApproved extends VersionedEvent {

  UUID getId();

  UUID getUserId();

}
