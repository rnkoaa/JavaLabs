package com.marketplace.eventstoredb.event;

import com.marketplace.eventstoredb.AppendResult;
import com.marketplace.eventstoredb.Event;
import java.util.List;

public interface EventStore<T> {

    EventStream<T> load(String streamId);

    EventStream<T> load(String streamId, int fromVersion);

    AppendResult append(String streamId, int expectedVersion, List<T> events);

    AppendResult append(String streamId, int expectedVersion, T event);

    /**
     * The size of the event store, how many streams do we have, that is how many aggregates do we
     * have
     *
     * @return
     */
    int size();

    /**
     * The size of the stream we are interested in
     *
     * @param streamId
     * @return
     */
    int streamSize(String streamId);

    int getVersion(String streamId);

    int nextVersion(String streamId);

    /**
     * append the event to the event store then publish to any subscribers
     *
     * @param streamId the id of the stream to append to then publish onto
     * @param event    event being processed
     */
    AppendResult publish(String streamId, Event event);

    AppendResult publish(String streamId, int expectedVersion, List<T> events);

    AppendResult publish(String streamId, int expectedVersion, T event);
}
