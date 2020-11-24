package com.marketplace.eventstoredb;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = StreamInfo.StreamInfoBuilder.class)
public class StreamInfo {
  String id;
  int version;

  @JsonPOJOBuilder(withPrefix = "")
  public static class StreamInfoBuilder {}
}
