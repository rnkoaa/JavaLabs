package com.marketplace.eventstoredb;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = EventWrapper.EventWrapperBuilder.class)
public class EventWrapper {
  private String id;
  private StreamInfo streamInfo;

  private String eventType;

  // json serialized eventData
  private String eventData;

  @JsonIgnore
  public Event getEvent() {
    // TODO - implement converting event data to event type
    //    Type eventType = eventTypeResolver.GetEventType(EventType);
    //    return (IEvent)EventData.ToObject(eventType);
    return null;
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static class EventWrapperBuilder {}
}
