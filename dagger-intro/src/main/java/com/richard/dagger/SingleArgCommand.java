package com.richard.dagger;

import java.util.List;

/** Abstract command that accepts a single argument. */
abstract class SingleArgCommand implements Command {

  @Override
  public final Result handleInput(List<String> input) {
    return input.size() == 1 ? handleArg(input.get(0)) : Result.invalid();
  }

  /** Handles the single argument to the command. */
  protected abstract Result handleArg(String arg);
}
