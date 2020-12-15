package com.richard.eventsourcing.inmemory;

import com.richard.eventsourcing.BaseAggregateStore;
import com.richard.eventsourcing.WriteResult;
import com.richard.eventsourcing.domain.AggregateRoot;
import com.richard.eventsourcing.event.Event;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class InMemoryAggregateRootImpl extends BaseAggregateStore {

  private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryAggregateRootImpl.class);
  Map<UUID, List<EventEntity>> store = new HashMap<>();

  @Override
  public Mono<WriteResult> save(AggregateRoot aggregate) {
    Objects.requireNonNull(aggregate, "aggregate cannot be null");

    UUID aggregateId = aggregate.getAggregateId();
//    List<EventEntity> eventEntities = store.get(aggregateId);
    List<EventEntity> eventEntities = store.computeIfAbsent(aggregateId, uuid -> List.of());

    List<EventEntity> entities = aggregate.getChanges()
        .stream()
        .map(change -> ImmutableEventEntity.builder()
            .event(change)
            .metadata(ImmutableEventMetadata.builder()
                .aggregate(aggregate.getClass().getCanonicalName())
                .type(change.getClass().getCanonicalName())
                .build())
            .build())
        .collect(Collectors.toList());

    List<EventEntity> modifiableList = new ArrayList<>(eventEntities);
    modifiableList.addAll(entities);

    store.put(aggregateId, Collections.unmodifiableList(modifiableList));

    aggregate.clearChanges();

    return Mono.just(new WriteResult());
  }

  @Override
  public Mono<AggregateRoot> load(UUID aggregateId) {
    List<EventEntity> eventEntities = store.get(aggregateId);
    if (eventEntities == null || eventEntities.isEmpty()) {
      return Mono.empty();
    }
    EventEntity eventEntity = eventEntities.get(0);
    EventMetadata metadata = eventEntity.getMetadata();
    Mono<? extends Constructor<?>> defaultConstructor = findDefaultConstructor(metadata.getAggregate());
    List<Event> events = eventEntities.stream().map(EventEntity::getEvent).collect(Collectors.toList());
    return defaultConstructor.flatMap(ctor -> generateAggregateRoot(events, ctor));
  }

  @Override
  public Mono<Long> size() {
    return Mono.just((long) store.size());
  }

  @Override
  public Mono<Integer> countEvents(UUID aggregateId) {
    int size = store.computeIfAbsent(aggregateId, key -> List.of())
        .size();
    return Mono.just(size);
  }
}
