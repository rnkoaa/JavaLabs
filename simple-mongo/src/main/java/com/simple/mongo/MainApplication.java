package com.simple.mongo;

import com.mongodb.client.result.InsertOneResult;
import com.simple.mongo.config.MongoConfig;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.mongodb.client.model.Filters.eq;

public class MainApplication {

    public static void main(String[] args) throws InterruptedException {
        var mongoDatabase = MongoConfig.mongoDatabase();
        var mongoClient = MongoConfig.mongoClient();
//        JacksonMongoCollection<Person> collection = JacksonMongoCollection.builder()
//                .build(mongoClient, "simple_mongo", "persons2", Person.class, UuidRepresentation.STANDARD);
        var collection = mongoDatabase.getCollection("persons3", Person.class);
        Person ada = new Person("Ada Byron", 20, new Address("St James Square", "London", "W1"));
        ada.setId(UUID.randomUUID());
        ada.setItemId(ItemId.newItemId());


        collection.find(eq("itemId", "a36be44e-c1ad-444c-b165-6f6015ec7da2")).first()
                .subscribe(new Subscriber<Person>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(1);
                    }

                    @Override
                    public void onNext(Person person) {
                        System.out.println(person);
                        System.out.println(person.getItemId());
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println(t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete.");
                    }
                });

//        publisher.subscribe(new Subscriber<Success>() {
//            @Override
//            public void onSubscribe(final Subscription s) {
//                s.request(1);  // <--- Data requested and the insertion will now occur
//            }
//
//            @Override
//            public void onNext(final Success success) {
//                System.out.println("Inserted");
//            }
//
//            @Override
//            public void onError(final Throwable t) {
//                System.out.println("Failed");
//            }
//
//            @Override
//            public void onComplete() {
//                System.out.println("Completed");
//            }
//        });
//
//        collection.insertOne(ada).subscribe(new SubscriberHelpers.OperationSubscriber<>());
//        collection.find()
//        collection.insert

//        collection.insertOne(ada);
//        Document doc =
//                new Document("name", "MongoDB")
//                        .append("type", "database")
//                        .append("count", 1)
//                        .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
//                        .append("info", new Document("x", 203).append("y", 102));
//
//        collection.insertOne(doc)
//                .subscribe(new SubscriberHelpers.PrintDocumentSubscriber());

        TimeUnit.SECONDS.sleep(5);
        System.out.println("Hello, World!");
    }
}
