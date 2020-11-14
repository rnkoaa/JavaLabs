package com.richard.dagger;

import javax.inject.Inject;
import java.util.List;

public class LoginCommand extends SingleArgCommand{
  private final Outputter outputter;

  @Inject
  public LoginCommand(Outputter outputter) {
    this.outputter = outputter;
  }

  @Override
  public String key() {
    return "login";
  }

  @Override
  public Result handleArg(String username) {
    outputter.output(username + " is logged in.");
//    return Status.HANDLED;
    return Result.handled();
  }


}
