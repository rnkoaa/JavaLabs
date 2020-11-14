package com.richard.dagger;

import javax.inject.Inject;
import java.util.List;

public class HelloCommand implements Command {
  private final Outputter outputter;

  @Inject
  HelloCommand(Outputter outputter) {
    this.outputter = outputter;
  }

  @Override
  public Status handleInput(List<String> input) {
    outputter.output("world!");
    return Status.HANDLED;
  }

  @Override
  public String key() {
    return "hello";
  }
}
