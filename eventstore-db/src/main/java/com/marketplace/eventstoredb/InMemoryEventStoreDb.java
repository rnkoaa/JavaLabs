package com.marketplace.eventstoredb;

import com.marketplace.eventstoredb.AppendResult.Failure;
import com.marketplace.eventstoredb.AppendResult.Success;

import com.marketplace.eventstoredb.event.EventPublisher;
import com.marketplace.eventstoredb.event.EventStore;
import com.marketplace.eventstoredb.event.EventStream;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryEventStoreDb implements EventStore<Event> {

    private final Map<String, List<EventEntity>> entityStore = new HashMap<>();
    private final EventPublisher<Event> eventPublisher;

    public InMemoryEventStoreDb(EventPublisher<Event> eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

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
    public AppendResult publish(String streamId, Event event) {
        var appendResult = append(streamId, 0, event);
        if (!appendResult.equals(new Success())) {
            return appendResult;
        }
        eventPublisher.publish(streamId, event);
        return appendResult;
    }

    @Override
    public AppendResult publish(String streamId, int expectedVersion, List<Event> events) {
        var appendResult = append(streamId, expectedVersion, events);
        if (!appendResult.equals(new Success())) {
            return appendResult;
        }
        eventPublisher.publish(streamId, events);
        return appendResult;
    }

    @Override
    public AppendResult publish(String streamId, int expectedVersion, Event event) {
        var appendResult = append(streamId, expectedVersion, event);
        if (Success.matches(appendResult)) {
            eventPublisher.publish(streamId, event);
        }
        return appendResult;
    }

    @Override
    public EventStream<Event> load(String streamId, int fromVersion) {
        List<EventEntity> eventStream = entityStore.get(streamId);
        if (eventStream == null || eventStream.size() == 0) {
            return new EventStreamImpl(streamId, "", 0, List.of());
        }

        eventStream.sort(Comparator.comparing(EventEntity::getVersion));
        int version = eventStream.get(eventStream.size() - 1).getVersion();

        List<Event> events =
            eventStream.stream()
                .dropWhile(it -> it.getVersion() < fromVersion)
                .map(EventEntity::getEvent)
                .collect(Collectors.toList());

        return new EventStreamImpl(streamId, "", version, events);
    }

    @Override
    public AppendResult append(String streamId, int expectedVersion, List<Event> events) {
        int currentVersion = getVersion(streamId);
        int nextVersion = currentVersion + 1;
        if ((expectedVersion == 0) || nextVersion == expectedVersion) {
            int numberOfEvents = events.size();
            List<EventEntity> eventStream = entityStore.get(streamId);
            if (eventStream == null) {
                eventStream = new ArrayList<>();
            }
            for (int index = 0; index < numberOfEvents; index++) {
                Event event = events.get(index);
                EventEntity eventEntity = EventEntity
                    .fromEvent(streamId, event, expectedVersion + index);
                eventStream.add(eventEntity);
            }
            entityStore.put(streamId, eventStream);
            return new Success();
        }
        return new Failure(
            String.format("expected version: %d, actual: %d", expectedVersion, nextVersion));
    }

    @Override
    public AppendResult append(String streamId, int expectedVersion, Event event) {
        int currentVersion = getVersion(streamId);
        int nextVersion = currentVersion + 1;

        // Concurrency check.
        if ((expectedVersion == 0) || nextVersion == expectedVersion) {
            var entity = EventEntity.fromEvent(streamId, event, expectedVersion);
            List<EventEntity> eventStream = entityStore.get(streamId);
            if (eventStream == null) {
                eventStream = new ArrayList<>();
            }
            eventStream.add(entity);
            entityStore.put(streamId, eventStream);
            return new Success();
        }
        return new Failure(
            String.format("expected version: %d, actual: %d", expectedVersion, nextVersion));
    }

    @Override
    public int size() {
        return entityStore.size();
    }

    @Override
    public int streamSize(String streamId) {
        return load(streamId).size();
    }

    private static String serializeEvents(String streamId, int expectedVersion,
        List<Event> events) {
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
