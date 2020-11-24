package com.marketplace.eventstoredb;

public class EventEntity {
  String id;

  private StreamInfo streamInfo;

  private String eventType;

  Event event;

  private byte[] eventData;
}
