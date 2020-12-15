package com.richard.eventsourcing.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.Instant;
import org.immutables.value.Value;

public interface VersionedEvent extends Event {

  @JsonIgnore
  @Value.Default
  @Override
  default long getVersion() {
    return 0;
  }

  @Value.Default
  @Override
  default String getAggregateName() {
    return "ClassifiedAd";
  }

  @Value.Default
  @Override
  default Instant getCreatedAt() {
    return Instant.now();
  }

  @Value.Default
  default String type() {
    return getClass().getCanonicalName();
  }

}
