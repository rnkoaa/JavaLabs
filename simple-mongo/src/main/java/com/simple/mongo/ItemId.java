package com.simple.mongo;

import java.util.UUID;

public record ItemId(UUID id) {

    public static ItemId newItemId() {
        return new ItemId(UUID.randomUUID());
    }
    public static ItemId fromString(String id) {
        return new ItemId(UUID.fromString(id));
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
