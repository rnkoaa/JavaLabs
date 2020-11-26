package com.marketplace.eventstoredb.classifiedad;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.marketplace.eventstoredb.Event;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder
@JsonDeserialize(builder = ClassifiedAdPriceUpdated.ClassifiedAdPriceUpdatedBuilder.class)
public class ClassifiedAdPriceUpdated implements Event {
  UUID id;
  BigDecimal price;
  String currencyCode;
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
  public static class ClassifiedAdPriceUpdatedBuilder {}
}
