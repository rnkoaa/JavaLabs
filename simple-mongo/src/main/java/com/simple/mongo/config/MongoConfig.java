package com.simple.mongo.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import org.bson.UuidRepresentation;
import org.bson.codecs.UuidCodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

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
        return CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()),
                // save uuids as UUID, instead of LUUID
                CodecRegistries.fromProviders(new UuidCodecProvider(UuidRepresentation.STANDARD),
                        new ItemIdCodecProvider()),
                MongoClientSettings.getDefaultCodecRegistry()
        );
    }

    public static MongoDatabase mongoDatabase() {
        return mongoClient().getDatabase("simple_mongo");
    }

    public static MongoCollection collection(String collectionName) {
        return mongoDatabase().getCollection(collectionName);
    }
}