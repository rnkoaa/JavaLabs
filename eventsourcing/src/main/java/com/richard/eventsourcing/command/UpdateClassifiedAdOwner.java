package com.richard.eventsourcing.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.richard.eventsourcing.annotations.TargetAggregateIdentifier;
import java.util.UUID;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Style;

@Immutable
@JsonDeserialize(as = ImmutableUpdateClassifiedAdOwner.class)
@JsonSerialize(as = ImmutableUpdateClassifiedAdOwner.class)
@Style(passAnnotations = {
    com.richard.eventsourcing.annotations.Command.class,
    TargetAggregateIdentifier.class
})
@com.richard.eventsourcing.annotations.Command
public interface UpdateClassifiedAdOwner extends Command {

  @JsonProperty("classified_ad_id")
  @TargetAggregateIdentifier
  UUID getClassifiedAdId();

  UUID getOwnerId();
}
