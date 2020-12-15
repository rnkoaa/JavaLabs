package com.richard.eventsourcing.domain;

public record PictureSize(int width, int height) {
    public PictureSize {
        if (height <= 0) {
            throw new IllegalArgumentException("Picture width must be a positive number");
        }
        if (width <= 0) {
            throw new IllegalArgumentException("Picture width must be a positive number");
        }
    }
}
