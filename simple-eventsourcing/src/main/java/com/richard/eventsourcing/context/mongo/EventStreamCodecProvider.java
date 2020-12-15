package com.richard.eventsourcing.context.mongo;

import com.richard.eventsourcing.mongo.EventStreamMetadata;
import com.richard.eventsourcing.mongo.ImmutableEventStreamMetadata;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class EventStreamCodecProvider implements CodecProvider {

  @SuppressWarnings("unchecked")
  @Override
  public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
    if (clazz == EventStreamMetadata.class || clazz == ImmutableEventStreamMetadata.class) {
      return (Codec<T>) new EventStreamMetadataCodec(registry);
    }
    return null;
  }
}
