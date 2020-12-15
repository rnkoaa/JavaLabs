package com.richard.eventsourcing.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.richard.eventsourcing.annotations.TargetAggregateIdentifier;
import java.util.UUID;
import org.immutables.value.Value;
import org.immutables.value.Value.Style;

@Value.Immutable
@JsonSerialize(as = ImmutablePublishClassifiedAd.class)
@JsonDeserialize(as = ImmutablePublishClassifiedAd.class)
@Style(passAnnotations = {
    com.richard.eventsourcing.annotations.Command.class,
    TargetAggregateIdentifier.class
})
@com.richard.eventsourcing.annotations.Command
public interface PublishClassifiedAd extends Command {

  @JsonProperty("classified_ad_id")
  @TargetAggregateIdentifier
  UUID getClassifiedAdId();
}
