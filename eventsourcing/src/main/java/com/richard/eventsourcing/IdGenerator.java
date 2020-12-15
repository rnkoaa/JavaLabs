package com.richard.eventsourcing;

import java.util.UUID;

public interface IdGenerator {

  default UUID newUUID() {
    return UUID.randomUUID();
  }

}
