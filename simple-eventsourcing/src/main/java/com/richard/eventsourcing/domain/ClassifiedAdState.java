package com.richard.eventsourcing.domain;

public enum ClassifiedAdState {
  PENDING_REVIEW,
  ACTIVE,
  INACTIVE,
  MARKED_AS_SOLD,
  APPROVED;

  static ClassifiedAdState fromString(String name) {
    return switch (name) {
      case "PENDING_REVIEW" -> PENDING_REVIEW;
      case "ACTIVE" -> ACTIVE;
      case "INACTIVE" -> INACTIVE;
      case "MARKED_AS_SOLD" -> MARKED_AS_SOLD;
      default -> INACTIVE;
    };
  }
}
