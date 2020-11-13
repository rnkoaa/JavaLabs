package com.breman;

import java.util.UUID;

public record UserId(UUID value) {
    public UserId {
        if(value == null) {
            throw new IllegalArgumentException("value cannot be null");
        }
    }
}
