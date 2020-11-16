package com.marketplace.mongo;

import com.marketplace.framework.mongo.annotations.MongoStringCodec;
import com.marketplace.framework.mongo.annotations.MongoStringValueCodec;

import java.util.UUID;

@MongoStringCodec
public record UserId(@MongoStringValueCodec UUID id) {

    public static UserId newUserId() {
        return new UserId(UUID.randomUUID());
    }

    public static UserId fromString(String id) {
        return new UserId(UUID.fromString(id));
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
