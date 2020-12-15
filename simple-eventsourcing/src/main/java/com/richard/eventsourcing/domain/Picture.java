package com.richard.eventsourcing.domain;


import com.richard.eventsourcing.Entity;
import com.richard.eventsourcing.IdGenerator;
import com.richard.eventsourcing.IdGeneratorImpl;
import com.richard.eventsourcing.event.ClassifiedAdPictureResized;
import com.richard.eventsourcing.event.ImmutableClassifiedAdPictureResized;
import com.richard.eventsourcing.event.PictureAddedToAClassifiedAd;
import com.richard.eventsourcing.event.VersionedEvent;

public class Picture extends Entity<VersionedEvent> {

  private final IdGenerator idGenerator = new IdGeneratorImpl();
  private PictureId id;
  private ClassifiedAdId parentId;
  private PictureSize size;
  private String uri;
  private int order;

  public PictureId getId() {
    return id;
  }

  public PictureSize getSize() {
    return size;
  }

  public String getUri() {
    return uri;
  }

  public int getOrder() {
    return order;
  }

  public ClassifiedAdId getParentId() {
    return parentId;
  }

  public Picture(EventApplier eventApplier) {
    super(eventApplier);
  }

  @Override
  public void when(VersionedEvent event) {
    if (event instanceof PictureAddedToAClassifiedAd e) {
      this.id = new PictureId(e.getPictureId());
      this.parentId = new ClassifiedAdId(e.getClassifiedAdId());
      this.size = new PictureSize(e.getWidth(), e.getHeight());
      this.uri = e.getUrl();
      this.order = e.getOrder();
    } else if (event instanceof ClassifiedAdPictureResized e) {
      this.size = new PictureSize(e.getWidth(), e.getHeight());
    }
  }

  public void resize(PictureSize newSize) {
    apply(ImmutableClassifiedAdPictureResized.builder()
        .id(idGenerator.newUUID())
        .aggregateId(this.parentId.value())
        .classifiedAdId(this.parentId.value())
        .pictureId(this.id.value())
        .height(newSize.height())
        .width(newSize.width())
        .build());
  }

  public boolean hasCorrectSize() {
    return PictureRules.hasCorrectSize(this);
  }
}