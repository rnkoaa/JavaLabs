package com.richard.eventsourcing;


import com.richard.eventsourcing.OperationResult.Failure;
import com.richard.eventsourcing.OperationResult.Success;
import com.richard.eventsourcing.event.Event;
import com.richard.eventsourcing.mongo.EventStream;
import java.util.List;
import reactor.core.publisher.Mono;

public interface EventStore<T> {

  /**
   * Load all events of the stream from the beginning
   *
   * @param streamId id of the stream to load
   * @return an {@link EventStream} of events. If there are no events, the stream will be empty
   */
  Mono<EventStream> load(String streamId);

  /**
   * Load all events of the stream ignoring all events prior to {@param fromVersion}
   *
   * @param streamId    id of the stream to which the event will be appended to
   * @param fromVersion load events from the eventstore excluding all events prior to this version
   * @return an {@link EventStream} of events. If there are no events, the stream will be empty
   */
  Mono<EventStream> load(String streamId, int fromVersion);

  /**
   * Adds a list of events to the end of the event stream
   *
   * @param streamId        id of the stream to which the event will be appended to
   * @param expectedVersion the last version of the stream before the event is appended. This is for concurrency check to ensure that same
   *                        event is not appended by multiple threads
   * @param events          the events to be appended
   * @return a {@link Success } if the append was successfull and a {@link Failure} if the append operation failed.
   */
  Mono<OperationResult> append(String streamId, int expectedVersion, List<T> events);

  /**
   * Adds an event to the end of the event stream
   *
   * @param streamId        id of the stream to which the event will be appended to
   * @param expectedVersion the last version of the stream before the event is appended. This is for concurrency check to ensure that same
   *                        event is not appended by multiple threads
   * @param event           the events to be appended
   * @return a {@link Success } if the append was successfull and a {@link Failure} if the append operation failed.
   */
  Mono<OperationResult> append(String streamId, int expectedVersion, T event);

  /**
   * The size of the event store, how many streams do we have, that is how many aggregates do we have
   *
   * @return of the current eventstore based on the number of eventstreams
   */
  Mono<Long> size();

  /**
   * The size of the stream we are interested in
   *
   * @param streamId id of the stream to query for size
   * @return size of the stream if it exists
   */
  Mono<Long> streamSize(String streamId);

  /**
   * The latest version of the stream we are interested in
   *
   * @param streamId id of the stream to query for version
   * @return size of the stream if it exists
   */
  Mono<Integer> getVersion(String streamId);

  Mono<Integer> nextVersion(String streamId);

  /**
   * publish the event to the event store then publish to any subscribers
   *
   * @param streamId the id of the stream to append to then publish onto
   * @param event    event being processed
   */
  Mono<OperationResult> publish(String streamId, Event event);

  /**
   * appends a list of events to the end of the event stream then publishes those events to any subscribers that may be listening for those
   * events. If there are any errors in appending to the event stream, the events are never published
   *
   * @param streamId        id of the stream to which the event will be appended to
   * @param expectedVersion the last version of the stream before the event is appended. This is for concurrency check to ensure that same
   *                        event is not appended by multiple threads
   * @param events          the events to be appended
   * @return a {@link Success } if the append was successfull and a {@link Failure} if the append operation failed.
   */
  Mono<OperationResult> publish(String streamId, int expectedVersion, List<T> events);

  /**
   * Appends an event to the end of the event stream then subsequently publishes the event to any subscriber,
   *
   * @param streamId        id of the stream to which the event will be appended to
   * @param expectedVersion the last version of the stream before the event is appended. This is for concurrency check to ensure that same
   *                        event is not appended by multiple threads
   * @param event           the events to be appended
   * @return a {@link Success } if the append was successfull and a {@link Failure} if the append operation failed.
   */
  Mono<OperationResult> publish(String streamId, int expectedVersion, T event);
}
