package com.marketplace.eventstoredb;

public interface EventType<T> {

    Class<T> getClass(String className);
}
