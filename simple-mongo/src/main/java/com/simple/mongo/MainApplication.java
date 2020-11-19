package com.simple.mongo;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.result.InsertOneResult;
import com.simple.mongo.config.MongoConfig;

import java.util.UUID;

public class MainApplication {

    public static void main(String[] args) throws InterruptedException {
        var mongoDatabase = MongoConfig.mongoDatabase();
        var collection = mongoDatabase.getCollection("person", Person.class);
        Person person = new Person(UUID.randomUUID(),
                "Nana Kwame",
                30,
                new Address("123 main street", "Anywhere", "55123"),
                ItemId.newItemId()
                );
//
        InsertOneResult insertOneResult = collection.insertOne(person);
        if(insertOneResult.wasAcknowledged()){
            System.out.println("Successfully applied insert");
        }

        Person found = collection.find(eq("_id", person.id().toString())).first();
        System.out.println(found);
        System.out.println("Done.");
    }
}
