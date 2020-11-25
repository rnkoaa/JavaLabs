package com.marketplace.eventstoredb;

public class UnExpectedVersionException extends RuntimeException {
    public UnExpectedVersionException(String message) {
        super(message);
    }
}
