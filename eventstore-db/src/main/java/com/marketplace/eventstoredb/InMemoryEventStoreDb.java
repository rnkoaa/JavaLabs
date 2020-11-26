package com.marketplace.eventstoredb;

import com.marketplace.eventstoredb.AppendResult.Failure;
import com.marketplace.eventstoredb.AppendResult.Success;
import java.util.*;
import java.util.stream.Collectors;

// StreamInfoId : ClassifiedAdId:aggregateId
// eventId: Classified:aggregateId:eventVersion:eventId
public class InMemoryEventStoreDb implements EventStore<Event> {

  private final Map<String, List<EventEntity>> entityStore = new HashMap<>();

  @Override
  public EventStream<Event> load(String streamId) {
    List<EventEntity> eventStream = entityStore.get(streamId);
    if (eventStream == null || eventStream.size() == 0) {
      return new EventStreamImpl(streamId, "", 0, List.of());
    }

    eventStream.sort(Comparator.comparing(EventEntity::getVersion));

    List<Event> events =
        eventStream.stream().map(EventEntity::getEvent).collect(Collectors.toList());

    int version = eventStream.get(eventStream.size() - 1).getVersion();
    return new EventStreamImpl(streamId, "", version, events);
  }

  @Override
  public int getVersion(String streamId) {
    List<EventEntity> eventStream = entityStore.get(streamId);
    if (eventStream == null || eventStream.isEmpty()) {
      return 0;
    }

    eventStream.sort(Comparator.comparing(EventEntity::getVersion));
    return eventStream.get(eventStream.size() - 1).getVersion();
  }

  @Override
  public int nextVersion(String streamId) {
    int currentVersion = getVersion(streamId);
    return ++currentVersion;
  }

  @Override
  public EventStream<Event> load(String streamId, int fromVersion) {
    List<EventEntity> eventStream = entityStore.get(streamId);
    if (eventStream == null || eventStream.size() == 0) {
      return new EventStreamImpl(streamId, "", 0, List.of());
    }

    List<Event> events =
        eventStream.stream()
            .sorted(Comparator.comparing(EventEntity::getVersion))
            .filter(it -> it.getVersion() >= fromVersion)
            .map(EventEntity::getEvent)
            .collect(Collectors.toList());

    int version = eventStream.get(eventStream.size() - 1).getVersion();
    return new EventStreamImpl(streamId, "", version, events);
  }

  @Override
  public AppendResult append(String streamId, int expectedVersion, List<Event> events) {
    return new Failure("Not Implemented yet");
  }

  @Override
  public AppendResult append(String streamId, int expectedVersion, Event event) {
    int currentVersion = getVersion(streamId);
    int nextVersion = currentVersion + 1;

    // Concurrency check.
//        if ((!currentVersion && expectedVersion == 0) || (currentVersion == expectedVersion)) {}
    if ((expectedVersion == 0) || nextVersion == expectedVersion) {
      var streamInfo =
          StreamInfo.builder()
              .id(String.format("%s:%s", event.aggregateName(), event.getId()))
              .version(expectedVersion)
              .build();
      var entity = EventEntity.fromEvent(streamId, event, expectedVersion, streamInfo);
      List<EventEntity> eventStream = entityStore.get(streamId);
      if (eventStream == null) {
        eventStream = new ArrayList<>();
      }
      eventStream.add(entity);
      entityStore.put(streamId, eventStream);
      return new Success();
    }
    return new Failure(String.format("expected version: %d, actual: %d", expectedVersion, nextVersion));
  }

  @Override
  public int size() {
    return entityStore.size();
  }

  @Override
  public int streamSize(String streamId) {
    return load(streamId).size();
  }

  private static String serializeEvents(String streamId, int expectedVersion, List<Event> events) {
    //    var items = events.Select(e => new EventWrapper
    //    {
    //      Id = $"{streamId}:{++expectedVersion}:{e.GetType().Name}",
    //            StreamInfo = new StreamInfo
    //      {
    //        Id = streamId,
    //                Version = expectedVersion
    //      },
    //      EventType = e.GetType().Name,
    //              EventData = JObject.FromObject(e)
    //    });
    //
    //    return JsonConvert.SerializeObject(items);
    return "";
  }
}
/*
function appendToStream(streamId, expectedVersion, events) {

    var versionQuery =
    {
        'query' : 'SELECT Max(e.stream.version) FROM events e WHERE e.stream.id = @streamId',
        'parameters' : [{ 'name': '@streamId', 'value': streamId }]
    };

    const isAccepted = __.queryDocuments(__.getSelfLink(), versionQuery,
        function (err, items, options) {
            if (err) throw new Error("Unable to get stream version: " + err.message);

            if (!items || !items.length) {
                throw new Error("No results from stream version query.");
            }

            var currentVersion = items[0].$1;

            // Concurrency check.
            if ((!currentVersion && expectedVersion == 0)
                || (currentVersion == expectedVersion))
            {
                // Everything's fine, bulk insert the events.
                JSON.parse(events).forEach(event =>
                    __.createDocument(__.getSelfLink(), event));

                __.response.setBody(true);
            }
            else {
                __.response.setBody(false);
            }
        });

    if (!isAccepted) throw new Error('The query was not accepted by the server.');
}
 */
