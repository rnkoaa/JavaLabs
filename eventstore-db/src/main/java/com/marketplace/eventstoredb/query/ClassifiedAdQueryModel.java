package com.marketplace.eventstoredb.query;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassifiedAdQueryModel {

    private final UUID owner;
    private final UUID id;

    public ClassifiedAdQueryModel(UUID aggregateId, UUID ownerId) {
        this.id = aggregateId;
        this.owner = ownerId;
    }
}
