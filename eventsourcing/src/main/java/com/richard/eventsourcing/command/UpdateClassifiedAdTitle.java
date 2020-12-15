package com.richard.eventsourcing.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.richard.eventsourcing.annotations.TargetAggregateIdentifier;
import java.util.UUID;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Style;

@Immutable
@JsonSerialize(as = ImmutableUpdateClassifiedAdTitle.class)
@JsonDeserialize(as = ImmutableUpdateClassifiedAdTitle.class)
@Style(passAnnotations = {
    com.richard.eventsourcing.annotations.Command.class,
    TargetAggregateIdentifier.class
})
@com.richard.eventsourcing.annotations.Command
public interface UpdateClassifiedAdTitle extends Command {

  @JsonProperty("classified_ad_id")
  @TargetAggregateIdentifier
  UUID getClassifiedAdId();

  String getTitle();
}
