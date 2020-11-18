package com.marketplace.mongo;

import java.util.UUID;

public class MainApplication {
    public static void main(String[] args) {
        UserId userId = new UserId(UUID.randomUUID());
        System.out.println(userId.toString());
    }
}
