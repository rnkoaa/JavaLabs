package com.richard.eventsourcing.domain;

public class PictureRules {
    public static boolean hasCorrectSize(Picture picture) {
        return picture != null
                && picture.getSize().width() >= 800
                && picture.getSize().height() >= 600;
    }
}
