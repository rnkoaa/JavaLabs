package com.marketplace.mongo;

import com.marketplace.framework.mongo.annotations.MongoStringCodec;

@MongoStringCodec
public record FirstName(String name) {
    @Override
    public String toString() {
        return name;
    }
}
