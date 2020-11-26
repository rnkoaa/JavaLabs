package com.marketplace.eventstoredb;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventEntity {

  // represents aggregateId
  private String id;

  // the id of the stream to which this event or aggregate belongs
  //  private String streamId;

  private UUID eventId;

  private String eventType;

  @JsonRawValue private Event event;

  private int version;

  public static EventEntity fromEvent(String streamId, Event event, int version) {
    return EventEntity.builder()
        .id(streamId)
        .event(event)
        .eventId(event.getId())
        .eventType(event.getClass().getCanonicalName())
        .version(version)
        .build();
  }
}
