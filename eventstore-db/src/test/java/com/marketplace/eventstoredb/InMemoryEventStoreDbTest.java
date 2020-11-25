package com.marketplace.eventstoredb;

import com.marketplace.eventstoredb.classifiedad.ClassifiedAdCreated;
import com.marketplace.eventstoredb.classifiedad.ClassifiedAdTitleUpdated;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryEventStoreDbTest {
  private EventStore<Event> eventStore;

  @BeforeEach
  void setup() {
    eventStore = new InMemoryEventStoreDb();
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
            .id(UUID.fromString(classifiedAdId1))
            .ownerId(UUID.fromString(ownerId1))
            .build();

    String streamId = String.format("%s:%s", classifiedAdCreated.aggregateName(), classifiedAdId1);
    eventStore.append(streamId, 0, classifiedAdCreated);

    assertThat(eventStore.size()).isEqualTo(1);

    var classifiedAdTextUpdated =
        ClassifiedAdTitleUpdated.builder()
            .id(UUID.fromString(classifiedAdId1))
            .title("test title")
            .build();

    EventStream<Event> eventStream = eventStore.load(streamId);
    assertThat(eventStream.getVersion()).isEqualTo(1);

    eventStore.append(streamId, eventStream.getVersion(), classifiedAdTextUpdated);
    assertThat(eventStore.size()).isEqualTo(1);

    eventStream = eventStore.load(streamId);
    assertThat(eventStream.getVersion()).isEqualTo(2);
  }

  @Test
  void canCreateEventStoreWithNewEvent() {
    String classifiedAdId1 = "9d5d69ee-eadd-4352-942e-47935e194d22";
    String ownerId1 = "89b69f4f-e36e-4f2b-baa0-d47057e02117";
    var classifiedAdCreated =
        ClassifiedAdCreated.builder()
            .id(UUID.fromString(classifiedAdId1))
            .ownerId(UUID.fromString(ownerId1))
            .build();

    String streamId = String.format("%s:%s", classifiedAdCreated.aggregateName(), classifiedAdId1);
    eventStore.append(streamId, 0, classifiedAdCreated);

    assertThat(eventStore.size()).isEqualTo(1);
  }
}
