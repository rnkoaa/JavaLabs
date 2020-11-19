package com.simple.mongo.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.simple.mongo.ItemId;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.Codec;
import org.bson.codecs.UuidCodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromCodecs;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;

public class MongoConfig {

  public static MongoClient mongoClient() {
    var connectionString = new ConnectionString("mongodb://localhost:27017");
    MongoClientSettings settings = MongoClientSettings.builder()
        .applyConnectionString(connectionString)
        .codecRegistry(codecRegistries())

//                .co
        .uuidRepresentation(UuidRepresentation.STANDARD)
        .build();
    return MongoClients.create(settings);
  }

  private static CodecRegistry codecRegistries() {
    CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
    CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
        MongoClientSettings.getDefaultCodecRegistry(),
        CodecRegistries.fromProviders(pojoCodecRegistry),
        fromProviders(new UuidCodecProvider(UuidRepresentation.STANDARD)),
        fromCodecs(new AddressCodec(MongoClientSettings.getDefaultCodecRegistry())),
        fromProviders(new ItemIdCodecProvider()),
        fromProviders(new PersonCodecProvider())
    );
    return codecRegistry;
  }

  public static MongoDatabase mongoDatabase() {
    return mongoClient().getDatabase("simple_mongo");
  }

  public static MongoCollection collection(String collectionName) {
    return mongoDatabase().getCollection(collectionName);
  }
}
