package com.marketplace.eventstoredb.classifiedad;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.marketplace.eventstoredb.Event;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
@JsonDeserialize(builder = ClassifiedAdApproved.ClassifiedApprovedBuilder.class)
public class ClassifiedAdApproved implements Event {
    UUID id;
    UUID userId;

    @Override
    public UUID getId() {
        return null;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class ClassifiedApprovedBuilder {

    }
}