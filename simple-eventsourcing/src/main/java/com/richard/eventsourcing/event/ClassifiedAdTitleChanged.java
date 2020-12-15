package com.richard.eventsourcing.event;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.UUID;
import org.immutables.value.Value.Immutable;

@Immutable
@JsonDeserialize(as = ImmutableClassifiedAdTitleChanged.class)
@JsonSerialize(as = ImmutableClassifiedAdTitleChanged.class)
public interface ClassifiedAdTitleChanged extends VersionedEvent {

  UUID getId();

  String getTitle();

}
