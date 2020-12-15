package com.richard.eventsourcing.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.richard.eventsourcing.annotations.TargetAggregateIdentifier;
import com.richard.eventsourcing.command.UpdateClassifiedAd.PictureDto;
import com.richard.eventsourcing.command.UpdateClassifiedAd.PriceDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.immutables.value.Value;
import org.immutables.value.Value.Style;

@Value.Immutable
@JsonSerialize(as = ImmutableCreateClassifiedAd.class)
@JsonDeserialize(as = ImmutableCreateClassifiedAd.class)
@Style(passAnnotations = {
    com.richard.eventsourcing.annotations.Command.class,
    TargetAggregateIdentifier.class
})
@com.richard.eventsourcing.annotations.Command
public interface CreateClassifiedAd extends Command {

  @JsonProperty("classified_ad_id")
  @TargetAggregateIdentifier
  Optional<UUID> getClassifiedAdId();

  Optional<String> getText();

  Optional<String> getTitle();

  Optional<PriceDto> getPrice();

  @JsonProperty("owner")
  UUID getOwnerId();

  @JsonProperty("approver")
  Optional<UUID> getApprovedBy();

  Optional<List<PictureDto>> getPictures();

  Optional<ClassifiedAdState> getState();


}
