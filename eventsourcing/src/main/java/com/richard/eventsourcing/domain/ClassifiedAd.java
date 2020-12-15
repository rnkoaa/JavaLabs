package com.richard.eventsourcing.domain;

import com.richard.eventsourcing.UserId;
import com.richard.eventsourcing.annotations.AggregateRoot;
import com.richard.eventsourcing.annotations.CommandHandler;
import com.richard.eventsourcing.annotations.EventSourcingHandler;
import com.richard.eventsourcing.command.ApproveClassifiedAd;
import com.richard.eventsourcing.command.CreateClassifiedAd;
import com.richard.eventsourcing.command.PublishClassifiedAd;
import com.richard.eventsourcing.command.UpdateClassifiedAdPrice;
import com.richard.eventsourcing.command.UpdateClassifiedAdText;
import com.richard.eventsourcing.command.UpdateClassifiedAdTitle;
import com.richard.eventsourcing.event.ClassifiedAdCreated;
import java.util.ArrayList;
import java.util.List;

@AggregateRoot
public class ClassifiedAd {

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

  @CommandHandler
  public ClassifiedAd(CreateClassifiedAd command) {
    System.out.println("creating classified ad");
  }

  @EventSourcingHandler
  public ClassifiedAd(ClassifiedAdCreated event) {

  }

  @CommandHandler
  public void handle(UpdateClassifiedAdTitle command) {
    System.out.println("updating classified ad title");
  }

  @CommandHandler
  public void handle(PublishClassifiedAd command) {
    System.out.println("publishing classified ad");
  }

  @CommandHandler
  public void handle(UpdateClassifiedAdText command) {
    System.out.println("updating classified ad text");
  }

  @CommandHandler
  public void handle(UpdateClassifiedAdPrice command) {
    System.out.println("updating classified ad price");
  }

  @CommandHandler
  public void handle(ApproveClassifiedAd command) {
    System.out.println("updating classified ad price");
  }


}
