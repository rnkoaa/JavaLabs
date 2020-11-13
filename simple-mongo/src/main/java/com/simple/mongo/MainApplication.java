package com.simple.mongo;

import com.mongodb.client.result.InsertOneResult;
import com.simple.mongo.config.MongoConfig;
import org.bson.UuidRepresentation;
import org.mongojack.JacksonMongoCollection;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MainApplication {

    public static void main(String[] args) throws InterruptedException {
        var mongoDatabase = MongoConfig.mongoDatabase();
        var mongoClient = MongoConfig.mongoClient();
//        JacksonMongoCollection<Person> collection = JacksonMongoCollection.builder()
//                .build(mongoClient, "simple_mongo", "persons2", Person.class, UuidRepresentation.STANDARD);
        var collection = mongoDatabase.getCollection("persons3", Person.class);
        Person ada = new Person("Ada Byron", 20, new Address("St James Square", "London", "W1"));
        ada.setId(UUID.randomUUID());
//
//        collection.insertOne(ada).subscribe(new SubscriberHelpers.OperationSubscriber<>());
        collection.insertOne(ada);
//        Document doc =
//                new Document("name", "MongoDB")
//                        .append("type", "database")
//                        .append("count", 1)
//                        .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
//                        .append("info", new Document("x", 203).append("y", 102));
//
//        collection.insertOne(doc)
//                .subscribe(new SubscriberHelpers.PrintDocumentSubscriber());

//        TimeUnit.SECONDS.sleep(5);
        System.out.println("Hello, World!");
    }
}
