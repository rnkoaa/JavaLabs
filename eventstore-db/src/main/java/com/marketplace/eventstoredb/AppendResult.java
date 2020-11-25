package com.marketplace.eventstoredb;

import lombok.AllArgsConstructor;
import lombok.Getter;

public sealed class AppendResult {

  public static final class Success extends AppendResult {

  }


  @AllArgsConstructor
  @Getter
  public static final class Failure extends AppendResult {
    private final String message;
  }
}
