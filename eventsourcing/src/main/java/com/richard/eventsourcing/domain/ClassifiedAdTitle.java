package com.richard.eventsourcing.domain;

public record ClassifiedAdTitle(String value) {

    public ClassifiedAdTitle {
        if (!isValid(value)) {
            throw new IllegalArgumentException("value cannot be null, empty or greater than 100 chars");
        }
    }

    public static ClassifiedAdTitle fromString(String title) {
        return new ClassifiedAdTitle(title);
    }

    @Override
    public String toString() {
        return value;
    }

    public ClassifiedAdTitle fromHtmlString(String htmlString) {
//        var supportedTagsReplaced = htmlTitle
//                .Replace("<i>", "*")
//                .Replace("</i>", "*")
//                .Replace("<b>", "**")
//                .Replace("</b>", "**");
//
//        var value = Regex.Replace(supportedTagsReplaced, "<.*?>", string.Empty);
//        CheckValidity(value);
//
//        if (value == null || value.isEmpty() || value.length() > 100) {
        return new ClassifiedAdTitle("");
    }

    private boolean isValid(String value) {
        return value != null && !value.isEmpty() && !(value.length() > 100);
    }
}
