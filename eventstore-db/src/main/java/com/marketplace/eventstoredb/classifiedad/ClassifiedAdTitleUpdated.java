package com.marketplace.eventstoredb.classifiedad;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.marketplace.eventstoredb.Event;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
@JsonDeserialize(builder = ClassifiedAdTitleUpdated.ClassifiedAdTitleChangedBuilder.class)
public class ClassifiedAdTitleUpdated implements Event {
  UUID id;
  String title;

  public ClassifiedAdTitleUpdated(UUID id, String title) {
    this.id = id;
    this.title = title;
  }

  @Override
  public UUID getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static class ClassifiedAdTitleChangedBuilder {}
}
