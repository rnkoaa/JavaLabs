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

  @Override
  public UUID getId() {
    return classifiedAdId;
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static class PictureAddedToAClassifiedAdBuilder {}
}
