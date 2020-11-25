package com.marketplace.eventstoredb.classifiedad;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.marketplace.eventstoredb.Event;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = ClassifiedAdCreated.ClassifiedAdCreatedBuilder.class)
public class ClassifiedAdCreated implements Event {
  UUID id;
  UUID ownerId;

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public String aggregateName() {
    return ClassifiedAd.class.getSimpleName();
  }

  @JsonPOJOBuilder(withPrefix = "")
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ClassifiedAdCreatedBuilder {}
}
