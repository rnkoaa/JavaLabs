package com.richard.eventsourcing;

import com.richard.eventsourcing.domain.AggregateRoot;
import com.richard.eventsourcing.event.Event;
import com.richard.eventsourcing.event.VersionedEvent;
import com.richard.eventsourcing.inmemory.EventEntity;
import com.richard.eventsourcing.inmemory.TypeCache;
import io.vavr.control.Try;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public abstract class BaseAggregateStore implements AggregateStore {
private static final Logger LOGGER = LoggerFactory.getLogger(BaseAggregateStore.class);
  public static final TypeCache typeCache = TypeCache.getInstance();

  public Mono<AggregateRoot> generateAggregateRoot(List<Event> events, Constructor<?> constructor) {
    constructor.setAccessible(true);
    Try<Object> aggregateInstance = Try.of(constructor::newInstance);
    return aggregateInstance.map(instance -> {
      AggregateRoot aggregateRoot = (AggregateRoot) instance;
      events.stream()
          .map(event -> (VersionedEvent) event)
          .forEach(aggregateRoot::when);
      return Mono.just(aggregateRoot);
    }).getOrElseGet(Mono::error);
  }

  public Mono<? extends Constructor<?>> findDefaultConstructor(String aggregateType) {
    Optional<? extends Class<?>> aClass = typeCache.get(aggregateType);
    if (aClass.isEmpty()) {
      LOGGER.info("unable to retrieve class info for class '{}'", aggregateType);
      return Mono.error(new IllegalArgumentException("unable to find the class for type " + aggregateType));
    }
    Class<?> aggregateClass = aClass.get();
    Constructor<?>[] declaredConstructors = aggregateClass.getDeclaredConstructors();
    Optional<Constructor<?>> maybeDefaultConstructor = Arrays.stream(declaredConstructors)
        .filter(constructor -> constructor.getParameterCount() == 0)
        .findFirst();
//
    if (maybeDefaultConstructor.isEmpty()) {
      LOGGER.info("No default constructor found on class {}, please provide one ", aggregateType);
      return Mono.error(new IllegalArgumentException("AggregateRoot must have a default constructor"));
    }

    return maybeDefaultConstructor.map(Mono::just).orElse(Mono.error(new IllegalArgumentException(
        "constructor not found")));
  }
}
