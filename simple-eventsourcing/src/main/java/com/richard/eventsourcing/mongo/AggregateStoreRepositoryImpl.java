package com.richard.eventsourcing.mongo;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.DuplicateKeyException;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.reactivestreams.client.FindPublisher;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import com.richard.eventsourcing.BaseAggregateStore;
import com.richard.eventsourcing.EventStore;
import com.richard.eventsourcing.WriteResult;
import com.richard.eventsourcing.context.mongo.EventStreamMetadataCodec;
import com.richard.eventsourcing.domain.AggregateRoot;
import com.richard.eventsourcing.event.Event;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class AggregateStoreRepositoryImpl extends BaseAggregateStore {

  private static final Logger LOGGER = LoggerFactory.getLogger(AggregateStoreRepositoryImpl.class);

  private final EventStore<Event> eventStore;
  private MongoCollection<EventStreamMetadata> eventStreamMetadataMongoCollection;

  public AggregateStoreRepositoryImpl(MongoDatabase mongoDatabase,
      EventStore<Event> eventStore) {
    eventStreamMetadataMongoCollection = mongoDatabase.getCollection("eventStreamMetadata", EventStreamMetadata.class);
    this.eventStore = eventStore;
  }

  @Override
  public Mono<WriteResult> save(AggregateRoot aggregate) {
    EventStreamMetadata eventStreamMetadata = EventStreamMetadata.from(aggregate);
    return saveMetadata(eventStreamMetadata);
  }

  private Mono<WriteResult> saveMetadata(EventStreamMetadata eventStreamMetadata) {
    Publisher<InsertOneResult> publisher = eventStreamMetadataMongoCollection.insertOne(eventStreamMetadata);
    return Mono.from(publisher)
        .flatMap(it -> {
          if (it.wasAcknowledged()) {
            return Mono.just(new WriteResult());
          }
          return Mono.empty();
        })
        .onErrorResume(throwable -> {
          if (throwable instanceof DuplicateKeyException) {
            return Mono.just(new WriteResult());
          }
          return Mono.empty();
        });
  }

  @Override
  public Mono<AggregateRoot> load(UUID aggregateId) {
    return findMetadata(aggregateId)
        .flatMap(eventStreamMetadata -> {
          String streamId = eventStreamMetadata.getStreamId();
          Mono<EventStream> load = eventStore.load(streamId);
          return load.flatMap(stream -> fromEventStream(eventStreamMetadata, stream));
        });
  }

  @Override
  public Mono<Long> size() {
    return Mono.just(0L);
  }

  @Override
  public Mono<Integer> countEvents(UUID aggregateId) {
    return Mono.just(0);
  }

  private Mono<EventStreamMetadata> findMetadata(UUID aggregateId) {
    FindPublisher<EventStreamMetadata> eventStreamPublisher = eventStreamMetadataMongoCollection
        .find(eq(EventStreamMetadataCodec.AGGREGATE_ID, aggregateId));

    return Mono.from(eventStreamPublisher)
        .map(it -> it)
        .switchIfEmpty(Mono.empty());
  }

  private Mono<AggregateRoot> fromEventStream(EventStreamMetadata eventStreamMetadata, EventStream eventStream) {
    List<Event> events = eventStream.getEvents();
    if (events == null || events.isEmpty()) {
      return Mono.empty();
    }

    String aggregateType = eventStreamMetadata.getAggregateType();
    Mono<? extends Constructor<?>> defaultConstructor = findDefaultConstructor(aggregateType);

    return defaultConstructor.flatMap(ctor -> generateAggregateRoot(events, ctor));
  }
}
