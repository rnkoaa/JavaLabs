package com.richard.eventsourcing;


import com.richard.eventsourcing.domain.AggregateRoot;
import java.util.Optional;
import java.util.UUID;
import reactor.core.publisher.Mono;

public interface AggregateStore {

  Mono<WriteResult> save(AggregateRoot aggregate);

  Mono<AggregateRoot> load(UUID aggregateId);

  Mono<Long> size();

  Mono<Integer> countEvents(UUID aggregateId);
}
