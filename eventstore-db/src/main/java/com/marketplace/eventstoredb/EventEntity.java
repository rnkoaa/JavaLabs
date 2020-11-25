package com.marketplace.eventstoredb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventEntity {

  private String id;

  private StreamInfo streamInfo;

  private String eventType;

  private Event event;

  private int version;

  public static EventEntity fromEvent(
      String streamId, Event event, int version, StreamInfo streamInfo) {
    return EventEntity.builder()
        .id(streamId)
        .event(event)
        .eventType(event.getClass().getCanonicalName())
        .version(version)
        .streamInfo(streamInfo)
        .build();
  }
}
