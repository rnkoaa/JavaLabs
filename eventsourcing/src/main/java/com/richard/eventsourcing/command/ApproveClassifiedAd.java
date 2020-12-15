package com.richard.eventsourcing.command;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.UUID;
import org.immutables.value.Value;
import org.immutables.value.Value.Style;

@Value.Immutable
@JsonSerialize(as = ImmutableApproveClassifiedAd.class)
@JsonDeserialize(as = ImmutableApproveClassifiedAd.class)
@Style(passAnnotations = com.richard.eventsourcing.annotations.Command.class)
@com.richard.eventsourcing.annotations.Command
public interface ApproveClassifiedAd extends Command {
     UUID getClassifiedAdId();
     UUID getApproverId();
}
