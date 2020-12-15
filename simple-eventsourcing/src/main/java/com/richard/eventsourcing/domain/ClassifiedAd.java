package com.richard.eventsourcing.domain;

import com.richard.eventsourcing.IdGenerator;
import com.richard.eventsourcing.IdGeneratorImpl;
import com.richard.eventsourcing.UserId;
import com.richard.eventsourcing.annotations.CommandHandler;
import com.richard.eventsourcing.annotations.EventSourcingHandler;
import com.richard.eventsourcing.command.ApproveClassifiedAd;
import com.richard.eventsourcing.command.CreateClassifiedAd;
import com.richard.eventsourcing.command.PublishClassifiedAd;
import com.richard.eventsourcing.command.UpdateClassifiedAdPrice;
import com.richard.eventsourcing.command.UpdateClassifiedAdText;
import com.richard.eventsourcing.command.UpdateClassifiedAdTitle;
import com.richard.eventsourcing.event.ClassifiedAdCreated;
import com.richard.eventsourcing.event.ClassifiedAdPictureResized;
import com.richard.eventsourcing.event.ClassifiedAdPriceUpdated;
import com.richard.eventsourcing.event.ClassifiedAdSentForReview;
import com.richard.eventsourcing.event.ClassifiedAdTextUpdated;
import com.richard.eventsourcing.event.ClassifiedAdTitleChanged;
import com.richard.eventsourcing.event.ClassifiedApproved;
import com.richard.eventsourcing.event.ImmutableClassifiedAdCreated;
import com.richard.eventsourcing.event.ImmutableClassifiedAdPriceUpdated;
import com.richard.eventsourcing.event.ImmutableClassifiedAdSentForReview;
import com.richard.eventsourcing.event.ImmutableClassifiedAdTextUpdated;
import com.richard.eventsourcing.event.ImmutableClassifiedAdTitleChanged;
import com.richard.eventsourcing.event.ImmutableClassifiedApproved;
import com.richard.eventsourcing.event.ImmutablePictureAddedToAClassifiedAd;
import com.richard.eventsourcing.event.PictureAddedToAClassifiedAd;
import com.richard.eventsourcing.event.VersionedEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClassifiedAd extends com.richard.eventsourcing.domain.AggregateRoot {

  private final IdGenerator idGenerator = new IdGeneratorImpl();
  private static final String AGGREGATE_NAME = ClassifiedAd.class.getSimpleName();
  private ClassifiedAdId id;
  private final List<Picture> pictures = new ArrayList<>();
  private UserId ownerId;
  private ClassifiedAdTitle title;
  private ClassifiedAdText text;
  private Price price;
  private UserId approvedBy;
  private ClassifiedAdState state;

  public ClassifiedAd() {
  }

  @Override
  public String toString() {
    return "ClassifiedAd{" +
        "id=" + id +
        ", ownerId=" + ownerId +
        ", title=" + title +
        ", text=" + text +
        ", price=" + price +
        ", approvedBy=" + approvedBy +
        ", state=" + state +
        '}';
  }

  @Override
  public void ensureValidState(VersionedEvent event) {
    var valid = id != null && ownerId != null;

    boolean foundBadPictures = /*pictures.size() == 0 ||*/ pictures.stream().anyMatch(p -> !p.hasCorrectSize());

    valid = valid && switch (this.state) {
      case PENDING_REVIEW -> title != null && text != null
          && price != null
          && price.money() != null
          && price.money().amount().doubleValue() > 0
          && !foundBadPictures;
      case INACTIVE, MARKED_AS_SOLD -> !foundBadPictures;
      case ACTIVE, APPROVED -> title != null
          && text != null
          && price != null
          && price.money() != null
          && price.money().amount().doubleValue() > 0
          && approvedBy != null
          && !foundBadPictures;
    };
    if (!valid) {
      throw new InvalidStateException("post checks failed in state while processing event " + event.getClass().getSimpleName());
    }
  }

  @Override
  public void when(VersionedEvent event) {
    if (event instanceof ClassifiedAdCreated e) {
      on(e);
    } else if (event instanceof ClassifiedAdTextUpdated e) {
      on(e);
    } else if (event instanceof ClassifiedAdPriceUpdated e) {
      on(e);
    } else if (event instanceof ClassifiedAdTitleChanged e) {
      on(e);
    } else if (event instanceof ClassifiedAdSentForReview e) {
      on(e);
    } else if (event instanceof ClassifiedApproved e) {
      on(e);
    } else if (event instanceof PictureAddedToAClassifiedAd e) {
      on(e);
    } else if (event instanceof ClassifiedAdPictureResized e) {
      on(e);
    }
  }

  @Override
  public UUID getAggregateId() {
    return id.value();
  }

  @EventSourcingHandler
  public void on(ClassifiedAdTitleChanged event) {
    this.title = new ClassifiedAdTitle(event.getTitle());
  }

  @EventSourcingHandler
  public void on(ClassifiedAdPictureResized event) {
    var optionalPicture = findPicture(new PictureId(event.getPictureId()));
    optionalPicture.ifPresent(picture -> applyToEntity(picture, event));
  }

  @EventSourcingHandler
  public void on(ClassifiedAdSentForReview event) {
    this.state = ClassifiedAdState.PENDING_REVIEW;
  }

  @EventSourcingHandler
  public void on(ClassifiedAdTextUpdated event) {
    this.text = ClassifiedAdText.fromString(event.getText());
  }

  @EventSourcingHandler
  public void on(ClassifiedApproved event) {
    this.state = ClassifiedAdState.APPROVED;
    this.approvedBy = UserId.from(event.getUserId());
  }

  @EventSourcingHandler
  public void on(ClassifiedAdPriceUpdated event) {
    this.price = new Price(new Money(event.getPrice(), event.getCurrency()));
  }

  @EventSourcingHandler
  public void on(PictureAddedToAClassifiedAd event) {
    var picture = new Picture(this);
    applyToEntity(picture, event);
    this.pictures.add(picture);
  }

  @EventSourcingHandler
  public void on(ClassifiedAdCreated event) {
    this.id = ClassifiedAdId.from(event.getAggregateId());
    this.ownerId = new UserId(event.getOwnerId());
    this.state = ClassifiedAdState.INACTIVE;
  }

  public PictureId addPicture(String uri, PictureSize size, int order) {
    int newPictureOrder = order > 0 ? order : pictures.size() <= 0 ? 0 : pictures.size() + 1;
    var pictureId = idGenerator.newUUID();
    apply(ImmutablePictureAddedToAClassifiedAd.builder()
        .id(idGenerator.newUUID())
        .aggregateName(AGGREGATE_NAME)
        .aggregateId(id.value())
        .pictureId(pictureId)
        .url(uri)
        .height(size.height())
        .width(size.width())
        .order(newPictureOrder)
        .classifiedAdId(id.value())
        .build());
    return new PictureId(pictureId);
  }

  public PictureId addPicture(PictureId picId, String uri, PictureSize size, int order) {
    int newPictureOrder = order > 0 ? order : pictures.size() <= 0 ? 0 : pictures.size() + 1;
    var pictureId = (picId != null) ? picId : PictureId.newPictureId();

    apply(ImmutablePictureAddedToAClassifiedAd.builder()
        .id(idGenerator.newUUID())
        .aggregateName(AGGREGATE_NAME)
        .aggregateId(id.value())
        .pictureId(pictureId.value())
        .url(uri)
        .height(size.height())
        .width(size.width())
        .order(newPictureOrder)
        .classifiedAdId(id.value())
        .build());
    return pictureId;
  }

  public Picture createPicture(PictureSize pictureSize, String uri, int order) {
    var pictureItem = new Picture(this);
    var pictureId = addPicture(uri, pictureSize, order);
    Optional<Picture> picture = findPicture(pictureId);
    return picture.orElse(pictureItem);
  }

  public Picture createPicture(PictureId pictureId, PictureSize pictureSize, String uri, int order) {
    var pictureItem = new Picture(this);
    var result = addPicture(pictureId, uri, pictureSize, order);
    Optional<Picture> picture = findPicture(result);
    return picture.orElse(pictureItem);
  }

  public PictureId resizePicture(PictureId pictureId, PictureSize newSize) {
    var picture = findPicture(pictureId);
    var p = picture
        .orElseThrow(() -> new IllegalArgumentException("cannot resize a picture that I don't have"));
    p.resize(newSize);
    return pictureId;
  }

  private Optional<Picture> findPicture(PictureId pictureId) {
    return this.pictures.stream()
        .filter(p -> p.getId().equals(pictureId))
        .findFirst();
  }

  private Optional<Picture> first() {
    return this.pictures.stream().min(Comparator.comparingInt(Picture::getOrder));
  }

  @CommandHandler
  public ClassifiedAd(CreateClassifiedAd command) {
    System.out.println("creating classified ad");
    Optional<UUID> maybeClassifiedAdId = command.getClassifiedAdId();
    UUID classifiedAdId = maybeClassifiedAdId.orElse(UUID.randomUUID());

    apply(ImmutableClassifiedAdCreated.builder()
        .id(classifiedAdId)
        .aggregateId(classifiedAdId)
        .ownerId(command.getOwnerId())
        .build());
  }

  @CommandHandler
  public void handleCommand(UpdateClassifiedAdTitle command) {
    System.out.println("updating classified ad title");
    apply(ImmutableClassifiedAdTitleChanged.builder()
        .id(idGenerator.newUUID())
        .aggregateId(command.getClassifiedAdId())
        .aggregateName(AGGREGATE_NAME)
        .title(command.getTitle())
        .build());
  }

  @CommandHandler
  public void handleCommand(PublishClassifiedAd command) {
    apply(ImmutableClassifiedAdSentForReview.builder()
        .id(idGenerator.newUUID())
        .aggregateId(command.getClassifiedAdId())
        .aggregateName(AGGREGATE_NAME)
        .build());
  }

  @CommandHandler
  public void handleCommand(UpdateClassifiedAdText command) {
    apply(ImmutableClassifiedAdTextUpdated.builder()
        .id(idGenerator.newUUID())
        .aggregateId(command.getClassifiedAdId())
        .aggregateName(AGGREGATE_NAME)
        .text(command.getText())
        .build());
  }

  @CommandHandler
  public void handleCommand(UpdateClassifiedAdPrice command) {
    System.out.println("updating classified ad price");
    apply(ImmutableClassifiedAdPriceUpdated.builder()
        .id(idGenerator.newUUID())
        .aggregateId(command.getClassifiedAdId())
        .aggregateName(AGGREGATE_NAME)
        .price(command.getAmount())
        .currency(command.getCurrency())
        .build());
  }

  @CommandHandler
  public void handleCommand(ApproveClassifiedAd command) {
    apply(ImmutableClassifiedApproved.builder()
        .id(idGenerator.newUUID())
        .aggregateName(AGGREGATE_NAME)
        .aggregateId(command.getClassifiedAdId())
        .userId(command.getApproverId())
        .build());
  }


}
