package com.marketplace.eventstoredb.classifiedad;

import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ClassifiedAd extends AggregateRoot {

    private final UUID id;
    private final UUID owner;

    public ClassifiedAd(UUID aggregateId, UUID ownerId) {
       this.id = aggregateId;
       this.owner = ownerId;
    }
}
