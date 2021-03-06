package com.marketplace.eventstoredb.classifiedad;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.marketplace.eventstoredb.Event;
import com.marketplace.eventstoredb.framework.ForAggregate;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
@JsonDeserialize(builder = ClassifiedAdTitleUpdated.ClassifiedAdTitleChangedBuilder.class)
@ForAggregate(ClassifiedAd.class)
public class ClassifiedAdTitleUpdated implements Event {
  UUID id;
  String title;
  UUID aggregateId;

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public UUID aggregateId() {
    return aggregateId;
  }

  public String getTitle() {
    return title;
  }

  @Override
  public String aggregateName() {
    return ClassifiedAd.class.getSimpleName();
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static class ClassifiedAdTitleChangedBuilder {}
}
