package com.marketplace.eventstoredb;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.google.common.eventbus.EventBus;
import com.marketplace.eventstoredb.AppendResult.Success;
import com.marketplace.eventstoredb.classifiedad.ClassifiedAdCreated;
import com.marketplace.eventstoredb.classifiedad.ClassifiedAdTextUpdated;
import com.marketplace.eventstoredb.classifiedad.ClassifiedAdTitleUpdated;
import com.marketplace.eventstoredb.event.EventListener;
import com.marketplace.eventstoredb.event.EventStore;
import com.marketplace.eventstoredb.event.EventStream;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@SuppressWarnings("UnstableApiUsage")
class InMemoryEventStoreDbTest {

    private EventStore<Event> eventStore;
    private final ClassifiedAdEventProcessor eventProcessor = Mockito
        .mock(ClassifiedAdEventProcessor.class);
    private final EventListener classifiedAdEventListener = new ClassifiedAdEventListener(
        eventProcessor);

    InMemoryEventPublisher eventPublisher;

    @BeforeEach
    void setup() {
        var eventBus = new EventBus();
        eventPublisher = new InMemoryEventPublisher(eventBus);
        eventStore = new InMemoryEventStoreDb(eventPublisher);
        eventPublisher.registerListener(classifiedAdEventListener);
    }

    @AfterEach
    void cleanup() {
        eventPublisher.close();
    }

    @Test
    void emptyEventStoreWithoutEvents() {
        assertThat(eventStore.size()).isEqualTo(0);
    }

    @Test
    void createAndUpdateTitleInEventStore() {
        String classifiedAdId1 = "9d5d69ee-eadd-4352-942e-47935e194d22";
        String ownerId1 = "89b69f4f-e36e-4f2b-baa0-d47057e02117";
        var classifiedAdCreated =
            ClassifiedAdCreated.builder()
                .aggregateId(UUID.fromString(classifiedAdId1))
                .ownerId(UUID.fromString(ownerId1))
                .build();

        String streamId = String
            .format("%s:%s", classifiedAdCreated.aggregateName(), classifiedAdId1);
        eventStore.append(streamId, 0, classifiedAdCreated);

        assertThat(eventStore.size()).isEqualTo(1);

        var classifiedAdTextUpdated =
            ClassifiedAdTitleUpdated.builder()
                .aggregateId(UUID.fromString(classifiedAdId1))
                .title("test title")
                .build();

        EventStream<Event> eventStream = eventStore.load(streamId);
        assertThat(eventStream.getVersion()).isEqualTo(0);

        int expectedVersion = eventStream.getVersion() + 1;
        var appendResult = eventStore.append(streamId, expectedVersion, classifiedAdTextUpdated);
        assertThat(eventStore.size()).isEqualTo(1);
        assertThat(appendResult).isInstanceOf(Success.class);

        eventStream = eventStore.load(streamId);
        assertThat(eventStream.getVersion()).isEqualTo(1);
        assertThat(eventStream.size()).isEqualTo(2);
    }

    @Test
    void multipleEventsCanBeAdded() {
        String classifiedAdId1 = "9d5d69ee-eadd-4352-942e-47935e194d22";
        String ownerId1 = "89b69f4f-e36e-4f2b-baa0-d47057e02117";
        var classifiedAdCreated =
            ClassifiedAdCreated.builder()
                .aggregateId(UUID.fromString(classifiedAdId1))
                .ownerId(UUID.fromString(ownerId1))
                .build();

        String streamId = String
            .format("%s:%s", classifiedAdCreated.aggregateName(), classifiedAdId1);

        var classifiedAdTextUpdated =
            ClassifiedAdTitleUpdated.builder()
                .aggregateId(UUID.fromString(classifiedAdId1))
                .title("test title")
                .build();

        var result =
            eventStore.append(streamId, 0, List.of(classifiedAdCreated, classifiedAdTextUpdated));
        assertThat(result).isInstanceOf(Success.class);
        assertThat(eventStore.size()).isEqualTo(1);

        EventStream<Event> eventStream = eventStore.load(streamId);
        assertThat(eventStream.size()).isEqualTo(2);
        assertThat(eventStream.getVersion()).isEqualTo(1);
    }

    @Test
    void canCreateEventStoreWithNewEvent() {
        String classifiedAdId1 = "9d5d69ee-eadd-4352-942e-47935e194d22";
        String ownerId1 = "89b69f4f-e36e-4f2b-baa0-d47057e02117";
        var classifiedAdCreated =
            ClassifiedAdCreated.builder()
                .aggregateId(UUID.fromString(classifiedAdId1))
                .ownerId(UUID.fromString(ownerId1))
                .build();

        String streamId = String
            .format("%s:%s", classifiedAdCreated.aggregateName(), classifiedAdId1);
        eventStore.append(streamId, 0, classifiedAdCreated);

        assertThat(eventStore.size()).isEqualTo(1);
    }

    @Test
    void differentAggregatesWillCreateDifferentStreams() {
        String classifiedAdId2 = "582a7769-f08a-470d-af65-85ef7ff7969f";
        String classifiedAdId1 = "9d5d69ee-eadd-4352-942e-47935e194d22";
        String ownerId1 = "89b69f4f-e36e-4f2b-baa0-d47057e02117";

        String streamId = String
            .format("%s:%s", "ClassifiedAd", classifiedAdId1);

        var result = eventStore
            .append(streamId, 0,
                createEventsAggregate(UUID.fromString(classifiedAdId1), UUID.fromString(ownerId1)));

        String streamId2 = String
            .format("%s:%s", "ClassifiedAd", classifiedAdId2);
        result = eventStore
            .append(streamId2, 0,
                createEventsAggregate(UUID.fromString(classifiedAdId2), UUID.fromString(ownerId1)));

        assertThat(result).isInstanceOf(Success.class);
        assertThat(eventStore.size()).isEqualTo(2);
    }

    @Test
    void eventsCanBeLoadedFromVersion() {
        String classifiedAdId1 = "9d5d69ee-eadd-4352-942e-47935e194d22";
        String ownerId1 = "89b69f4f-e36e-4f2b-baa0-d47057e02117";

        String streamId = String
            .format("%s:%s", "ClassifiedAd", classifiedAdId1);

        var result = eventStore
            .append(streamId, 0,
                createEventsAggregate(UUID.fromString(classifiedAdId1), UUID.fromString(ownerId1)));
        assertThat(result).isInstanceOf(Success.class);
        assertThat(eventStore.size()).isEqualTo(1);

        EventStream<Event> eventStream = eventStore.load(streamId);
        assertThat(eventStream.size()).isEqualTo(3);
        assertThat(eventStream.getVersion()).isEqualTo(2);
    }

    @Test
    void publishEventSavesEventAndPublishesEvent() {
        String classifiedAdId = "9d5d69ee-eadd-4352-942e-47935e194d22";
        String ownerId = "89b69f4f-e36e-4f2b-baa0-d47057e02117";

        String streamId = String
            .format("%s:%s", "ClassifiedAd", classifiedAdId);

        var classifiedAdCreated =
            ClassifiedAdCreated.builder()
                .id(UUID.randomUUID())
                .aggregateId(UUID.fromString(classifiedAdId))
                .ownerId(UUID.fromString(ownerId))
                .build();

        var result = eventStore.publish(streamId, 0, classifiedAdCreated);
        assertThat(result).isInstanceOf(Success.class);
        assertThat(eventStore.size()).isEqualTo(1);

        EventStream<Event> eventStream = eventStore.load(streamId);
        assertThat(eventStream.size()).isEqualTo(1);
        assertThat(eventStream.getVersion()).isEqualTo(0);

        verify(eventProcessor, times(1)).create(classifiedAdCreated);
    }

    List<Event> createEventsAggregate(UUID aggregateId, UUID ownerId) {
        var classifiedAdCreated1 =
            ClassifiedAdCreated.builder()
                .id(UUID.randomUUID())
                .aggregateId(aggregateId)
                .ownerId(ownerId)
                .build();
        var classifiedAdTextUpdated1 =
            ClassifiedAdTextUpdated.builder()
                .aggregateId(aggregateId)
                .text("test classified ad text")
                .build();
        return List.of(classifiedAdCreated1, classifiedAdTextUpdated1);
    }
}
