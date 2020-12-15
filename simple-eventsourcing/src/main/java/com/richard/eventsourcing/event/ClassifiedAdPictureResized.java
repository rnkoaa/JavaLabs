package com.richard.eventsourcing.event;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.UUID;
import org.immutables.value.Value.Immutable;

@Immutable
@JsonDeserialize(as = ImmutableClassifiedAdPictureResized.class)
@JsonSerialize(as = ImmutableClassifiedAdPictureResized.class)
public interface ClassifiedAdPictureResized extends VersionedEvent {

  UUID getClassifiedAdId();

  UUID getPictureId();

  int getHeight();

  int getWidth();

}
