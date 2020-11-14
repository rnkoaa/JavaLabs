package com.richard.dagger;

import javax.inject.Inject;
import java.util.*;

public class CommandRouter {
  private final Map<String, Command> commands = new HashMap<>();

  @Inject
  CommandRouter(Command helloCommand) {
    commands.put(helloCommand.key(), helloCommand);
  }

  Command.Status route(String input) {
    List<String> splitInput = split(input);
    if (splitInput.isEmpty()) {
      return invalidCommand(input);
    }

    String commandKey = splitInput.get(0);
    Command command = commands.get(commandKey);
    if (command == null) {
      return invalidCommand(input);
    }

    //    Command.Status status = command.handleInput(splitInput.subList(1, splitInput.size()));
    //    if (status == Command.Status.INVALID) {
    //      System.out.println(commandKey + ": invalid arguments");
    //    }
    return Command.Status.INVALID;
  }

  private Command.Status invalidCommand(String input) {
    System.out.println(String.format("couldn't understand \"%s\". please try again.", input));
    return Command.Status.INVALID;
  }

  // Split on whitespace
  private static List<String> split(String string) {
    return Arrays.asList(string.split(" "));
  }
}
