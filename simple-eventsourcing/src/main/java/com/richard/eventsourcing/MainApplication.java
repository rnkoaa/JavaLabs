package com.richard.eventsourcing;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoDatabase;
import com.richard.eventsourcing.command.ImmutableCreateClassifiedAd;
import com.richard.eventsourcing.command.ImmutableUpdateClassifiedAdText;
import com.richard.eventsourcing.domain.AggregateRoot;
import com.richard.eventsourcing.domain.ClassifiedAd;
import com.richard.eventsourcing.inmemory.InMemoryAggregateRootImpl;
import com.richard.eventsourcing.inmemory.InMemoryEventStore;
import com.richard.eventsourcing.mongo.AggregateStoreRepositoryImpl;
import com.richard.eventsourcing.mongo.MongoClientProvider;
import java.util.UUID;
import reactor.core.publisher.Mono;

public class MainApplication {

  public static void main(String[] args) {
    MongoClient mongoClient = MongoClientProvider.provideMongoClient();
    MongoDatabase eventstore = mongoClient.getDatabase("eventstore");
    AggregateStore aggregateStore = new AggregateStoreRepositoryImpl(eventstore, new InMemoryEventStore());
    UUID classifiedAdId = UUID.fromString("c8e9b910-83fb-4a4b-8400-42149f2e2fea");
    UUID ownerId = UUID.fromString("a86358d1-966d-41b2-809c-9b1e0acd3234");

//    AggregateStore aggregateStore = new InMemoryAggregateRootImpl();
    Mono<Long> sizePublisher = aggregateStore.size();
    Long size = sizePublisher.block();
    System.out.println("Initial Aggregate Size " + size);

    var classifiedAd =
        new ClassifiedAd(ImmutableCreateClassifiedAd.builder().classifiedAdId(classifiedAdId).ownerId(ownerId).build());

    System.out.println("Changes Size: " + classifiedAd.getChanges().size());

    aggregateStore.save(classifiedAd).block();

    sizePublisher = aggregateStore.size();
    size = sizePublisher.block();
    System.out.println("Initial Aggregate Size " + size);

    System.out.println("New Changes Size: " + classifiedAd.getChanges().size());

    aggregateStore.countEvents(classifiedAdId)
        .doOnNext(count -> {
          System.out.println("Total Events: " + count);
        })
        .block();

    AggregateRoot ad = aggregateStore.load(classifiedAdId).block();
    System.out.println(ad);
    if (ad != null) {
      ClassifiedAd cAdd = (ClassifiedAd) ad;
      cAdd.handleCommand(ImmutableUpdateClassifiedAdText.builder().classifiedAdId(ad.getAggregateId()).text("sample text").build());
      aggregateStore.save(cAdd).block();
    }

    aggregateStore.countEvents(classifiedAdId)
        .doOnNext(count -> {
          System.out.println("#2 Total Events: " + count);
        })
        .block();

//    aggregateStore.load(classifiedAdId).block()
//        .map(ad -> (ClassifiedAd) ad)
//        .ifPresent(System.out::println);

  }
}

