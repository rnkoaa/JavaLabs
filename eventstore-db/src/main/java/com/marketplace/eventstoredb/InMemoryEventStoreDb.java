package com.marketplace.eventstoredb;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryEventStoreDb /*implements EventStore*/ {
//  private final Map<String, EventStream<EventEntity<Event, UUID>>> streams = new HashMap<>();

//  @Override
//  public EventStream<Event> load(String streamId) {
//    EventStream<Event> eventStream = streams.get(streamId);
//    if (eventStream == null || eventStream.getEvents() == null) {
////      return List.of();
//        return new EventStreamImpl(streamId, "", 0, List.of());
//    }
//
//    List<Event> events = eventStream.getEvents();
//    events.sort(Comparator.comparing(Event::getVersion));
//    int version = events.get(events.size() - 1).getVersion();
//    return new EventStreamImpl(eventStream.getId(), eventStream.getName(), version, events);
//  }

//  @Override
//  public EventStream<Event> load(String streamId, int fromVersion) {
//    EventStream<Event> eventStream = streams.get(streamId);
//    if (eventStream == null || eventStream.getEvents() == null) {
//      return new EventStreamImpl(streamId, "", 0, List.of());
//    }
//
//    List<Event> events = eventStream.getEvents();
//    events.sort(Comparator.comparing(Event::getVersion));
//
//    List<Event> eventsFromVersion = events.stream()
//            .filter(it -> it.getVersion() >= fromVersion)
//            .collect(Collectors.toList());
//
//    int version = events.get(events.size() - 1).getVersion();
//    return new EventStreamImpl(eventStream.getId(), eventStream.getName(), version, eventsFromVersion);
//      return null;
//  }
//
//  @Override
//  public boolean append(String streamId, int expectedVersion, List<Event> events) {
//    streamId,
//            expectedVersion,
//            SerializeEvents(streamId, expectedVersion, events)
//    return false;
//  }
//
//  @Override
//  public boolean append(String streamId, int expectedVersion, Event event) {
//    return false;
//  }
}
