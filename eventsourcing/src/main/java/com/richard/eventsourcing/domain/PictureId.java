package com.richard.eventsourcing.domain;

import java.util.UUID;

public record PictureId(UUID value) {

  public PictureId {
    if (value == null) {
      throw new IllegalArgumentException("value cannot be null");
    }
  }

  public static PictureId newPictureId() {
    return new PictureId(UUID.randomUUID());
  }

  @Override
  public String toString() {
    return value.toString();
  }

  public static PictureId fromString(String value) {
    return new PictureId(UUID.fromString(value));
  }
}
