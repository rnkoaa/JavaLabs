package com.marketplace.mongo;

import com.marketplace.framework.mongo.annotations.MongoStringCodec;
import com.marketplace.framework.mongo.annotations.MongoStringValueCodec;

import java.util.UUID;

@MongoStringCodec
public record ClassifiedAdId(@MongoStringValueCodec UUID id) {

    public static ClassifiedAdId newUserId() {
        return new ClassifiedAdId(UUID.randomUUID());
    }

    public static ClassifiedAdId fromString(String id) {
        return new ClassifiedAdId(UUID.fromString(id));
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
