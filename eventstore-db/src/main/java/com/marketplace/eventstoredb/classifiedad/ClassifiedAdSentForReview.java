package com.marketplace.eventstoredb.classifiedad;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.marketplace.eventstoredb.Event;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
@JsonDeserialize(builder = ClassifiedAdSentForReview.ClassifiedAdSentForReviewBuilder.class)
public class ClassifiedAdSentForReview implements Event {
  UUID id;
  UUID aggregateId;

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public UUID aggregateId() {
    return aggregateId;
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static class ClassifiedAdSentForReviewBuilder {}
}
