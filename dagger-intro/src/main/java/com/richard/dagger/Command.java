package com.richard.dagger;

import java.util.List;
import java.util.Optional;

public interface Command {
  String key();

  /**
   * Processes and optionally acts upon the given {@code input}.
   *
   * @return a {@link Result} indicating how the input was handled
   */
  Result handleInput(List<String> input);

  /**
   * A command result, which has a {@link Status} and optionally a new {@link CommandRouter} that
   * will handle subsequent commands.
   */
  final class Result {
    private final Status status;
    private final Optional<CommandRouter> nestedCommandRouter;

    private Result(Status status, Optional<CommandRouter> nestedCommandRouter) {
      this.status = status;
      this.nestedCommandRouter = nestedCommandRouter;
    }

    static Result invalid() {
      return new Result(Status.INVALID, Optional.empty());
    }

    static Result handled() {
      return new Result(Status.HANDLED, Optional.empty());
    }

    static Result inputCompleted() {
      return new Result(Status.INPUT_COMPLETED, Optional.empty());
    }

    static Result enterNestedCommandSet(CommandRouter nestedCommandRouter) {
      return new Result(Status.HANDLED, Optional.of(nestedCommandRouter));
    }

    Status status() {
      return status;
    }

    Optional<CommandRouter> nestedCommandRouter() {
      return nestedCommandRouter;
    }
  }

  enum Status {
    INVALID,
    HANDLED,
    /** The command handled the input and no further inputs should be submitted. */
    INPUT_COMPLETED,
  }
}
