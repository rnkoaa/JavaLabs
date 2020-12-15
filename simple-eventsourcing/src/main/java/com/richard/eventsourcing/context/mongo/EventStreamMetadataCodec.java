package com.richard.eventsourcing.context.mongo;

import com.richard.eventsourcing.mongo.ImmutableEventStreamMetadata;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import org.bson.BsonObjectId;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;

public class EventStreamMetadataCodec implements CollectibleCodec<ImmutableEventStreamMetadata> {

  public static final String AGGREGATE_ID = "aggregateId";
  public static final String AGGREGATE_TYPE = "aggregateType";
  public static final String STREAM_ID = "streamId";
  public static final String CREATED_AT = "createdAt";
  public static final String ID = "_id";
  private final Codec<Document> documentCodec;

  public EventStreamMetadataCodec(CodecRegistry registry) {
    documentCodec = registry.get(Document.class);
  }

  @Override
  public ImmutableEventStreamMetadata generateIdIfAbsentFromDocument(ImmutableEventStreamMetadata document) {
    if (!documentHasId(document)) {
      document = ImmutableEventStreamMetadata.copyOf(document)
          .withId(ObjectId.get());
    }
    return document;
  }


  @Override
  public boolean documentHasId(ImmutableEventStreamMetadata document) {
    return document.getId().isPresent();
  }

  @Override
  public BsonValue getDocumentId(ImmutableEventStreamMetadata document) {
    return new BsonObjectId(documentId(document));
  }

  private ObjectId documentId(ImmutableEventStreamMetadata document) {
    return document.getId().orElse(ObjectId.get());
  }

  @Override
  public ImmutableEventStreamMetadata decode(BsonReader reader, DecoderContext decoderContext) {
    Document document = documentCodec.decode(reader, decoderContext);
    Date createdAt = document.get(CREATED_AT, Date.class);

    return ImmutableEventStreamMetadata.builder()
        .id(document.get(ID, ObjectId.class))
        .aggregateType(document.getString(AGGREGATE_TYPE))
        .aggregateId(document.get(AGGREGATE_ID, UUID.class))
        .streamId(document.getString(STREAM_ID))
        .createdAt((createdAt != null) ? createdAt.toInstant() : Instant.now())
        .build();
  }

  @Override
  public void encode(BsonWriter writer, ImmutableEventStreamMetadata value, EncoderContext encoderContext) {
    Document document = new Document();
    document.put(ID, documentId(value));
    document.put(AGGREGATE_ID, value.getAggregateId());
    document.put(AGGREGATE_TYPE, value.getAggregateType());
    document.put(STREAM_ID, value.getStreamId());
    document.put(CREATED_AT, value.getCreatedAt());
    documentCodec.encode(writer, document, encoderContext);
  }

  @Override
  public Class<ImmutableEventStreamMetadata> getEncoderClass() {
    return ImmutableEventStreamMetadata.class;
  }

}
