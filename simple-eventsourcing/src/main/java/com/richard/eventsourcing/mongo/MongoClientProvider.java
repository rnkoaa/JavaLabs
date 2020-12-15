package com.richard.eventsourcing.mongo;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.richard.eventsourcing.context.mongo.EventStreamCodecProvider;
import org.bson.UuidRepresentation;
import org.bson.codecs.UuidCodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

public class MongoClientProvider {

  public static MongoClient provideMongoClient() {
    var connectionString = new ConnectionString("mongodb://localhost:27017");
    var settings = MongoClientSettings.builder()
        .applyConnectionString(connectionString)
        .codecRegistry(codecRegistries())
        .uuidRepresentation(UuidRepresentation.STANDARD)
        .build();
    return MongoClients.create(settings);
  }

  private static CodecRegistry codecRegistries() {
    return CodecRegistries.fromRegistries(
        MongoClientSettings.getDefaultCodecRegistry(),
        fromProviders(new UuidCodecProvider(UuidRepresentation.STANDARD),
            new EventStreamCodecProvider()),
        MongoClientSettings.getDefaultCodecRegistry(),
        fromProviders(PojoCodecProvider.builder().automatic(true).build())
    );
  }
}
