package com.richard.eventsourcing;


import java.util.UUID;

public record UserId(UUID value) {

  public UserId {
    if (value == null) {
      throw new IllegalArgumentException("value cannot be null");
    }
  }

  public static UserId newId() {
    return new UserId(UUID.randomUUID());
  }

  public static UserId from(UUID value) {
    return new UserId(value);
  }

  @Override
  public String toString() {
    return value.toString();
  }

  public static UserId fromString(String value) {
    return new UserId(UUID.fromString(value));
  }
}
