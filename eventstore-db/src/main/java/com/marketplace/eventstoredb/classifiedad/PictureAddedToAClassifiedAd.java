package com.marketplace.eventstoredb.classifiedad;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.marketplace.eventstoredb.Event;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
@JsonDeserialize(builder = PictureAddedToAClassifiedAd.PictureAddedToAClassifiedAdBuilder.class)
public class PictureAddedToAClassifiedAd implements Event {
  UUID classifiedAdId;
  UUID pictureId;
  String url;
  int height;
  int width;
  int order;
  UUID aggregateId;

  @Override
  public UUID getId() {
    return classifiedAdId;
  }

  @Override
  public UUID aggregateId() {
    return aggregateId;
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static class PictureAddedToAClassifiedAdBuilder {}
}
