package com.richard.eventsourcing;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.richard.eventsourcing.command.CreateClassifiedAd;
import com.richard.eventsourcing.command.ImmutableCreateClassifiedAd;
import com.richard.eventsourcing.command.ImmutablePublishClassifiedAd;
import com.richard.eventsourcing.command.PublishClassifiedAd;
import io.vavr.control.Try;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings({"rawtypes", "unchecked"})
class CommandProcessorTest {

  CommandProcessor commandProcessor = new CommandProcessor();

  @Nested
  class TargetAggregateIdentifierTest {

    @Test
    void targetOptionalIdentifierRetrievedOnCommand() {
      UUID classifiedAdId = UUID.fromString("228eca6d-cc53-432b-af44-d7f6c1eb9a52");
      CreateClassifiedAd createClassifiedAd = ImmutableCreateClassifiedAd.builder()
          .classifiedAdId(classifiedAdId)
          .ownerId(UUID.randomUUID())
          .build();
      Try<Object> aggregateIdTry = commandProcessor.getAggregateId(createClassifiedAd);
      assertThat(aggregateIdTry.isSuccess()).isTrue();
      Object o = aggregateIdTry.get();
      assertThat(o).isInstanceOf(Optional.class);
      Optional optional = (Optional) o;
      assertThat(optional).isPresent();
      Object aggregateId = optional.get();
      assertThat(aggregateId).isInstanceOf(UUID.class)
          .isEqualTo(classifiedAdId);
    }

    @Test
    void targetIdentifierCanBeConvertedToOptional() {
      UUID classifiedAdId = UUID.fromString("228eca6d-cc53-432b-af44-d7f6c1eb9a52");
      CreateClassifiedAd createClassifiedAd = ImmutableCreateClassifiedAd.builder()
          .classifiedAdId(classifiedAdId)
          .ownerId(UUID.randomUUID())
          .build();
      Try<Object> aggregateIdTry = commandProcessor.getAggregateId(createClassifiedAd);
      assertThat(aggregateIdTry.isSuccess()).isTrue();
      Optional<Object> optionalObject = aggregateIdTry.toJavaOptional();
      assertThat(optionalObject).isPresent();
      Object o = optionalObject.get();
      assertThat(o).isInstanceOf(Optional.class);
      Optional optional = (Optional) o;
      assertThat(optional).isPresent();
      Object aggregateId = optional.get();
      assertThat(aggregateId).isInstanceOf(UUID.class)
          .isEqualTo(classifiedAdId);
    }

    @Test
    void missingIdentifierWillReturnNotFound() {
      CreateClassifiedAd createClassifiedAd = ImmutableCreateClassifiedAd.builder()
          .ownerId(UUID.randomUUID())
          .build();
      Try<Object> aggregateIdTry = commandProcessor.getAggregateId(createClassifiedAd);
      assertThat(aggregateIdTry.isSuccess()).isTrue();
      Object o = aggregateIdTry.get();
      assertThat(o).isInstanceOf(Optional.class);
      Optional optional = (Optional) o;
      assertThat(optional).isEmpty();
    }

    @Test
    void targetIdentifierRetrievedOnCommand() {
      UUID classifiedAdId = UUID.fromString("228eca6d-cc53-432b-af44-d7f6c1eb9a52");
      PublishClassifiedAd createClassifiedAd = ImmutablePublishClassifiedAd.builder()
          .classifiedAdId(classifiedAdId)
          .build();
      Try<Object> aggregateIdTry = commandProcessor.getAggregateId(createClassifiedAd);
      assertThat(aggregateIdTry.isSuccess()).isTrue();
      Object o = aggregateIdTry.get();
      assertThat(o).isInstanceOf(UUID.class);
      assertThat(o).isInstanceOf(UUID.class)
          .isEqualTo(classifiedAdId);
    }
  }

}