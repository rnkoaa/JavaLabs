package com.richard.eventsourcing.domain;

public class InvalidStateException extends RuntimeException {

  public InvalidStateException(String message) {
    super(message);
  }
}
