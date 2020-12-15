package com.richard.eventsourcing;

public sealed abstract class OperationResult {

  public static final class Success extends OperationResult {

  }

  public static final class Failure extends OperationResult {

  }


}
