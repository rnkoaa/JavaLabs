package com.marketplace.eventstoredb;

import java.time.Instant;
import java.util.UUID;

public interface Event {
  default Instant createdAt() {
    return Instant.now();
  }

  UUID getId();

  UUID aggregateId();

  default String aggregateName() {
    return getClass().getSimpleName();
  }
}
