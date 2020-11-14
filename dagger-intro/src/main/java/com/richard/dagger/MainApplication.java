package com.richard.dagger;

import java.util.Scanner;

public class MainApplication {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    //    CommandRouter commandRouter = new CommandRouter();
    //      CommandRouter commandRouter = DaggerCom
    CommandRouterComponent commandRouterComponent = DaggerCommandRouterComponent.create();
    CommandRouter commandRouter = commandRouterComponent.commandRouter();

    while (scanner.hasNextLine()) {
      commandRouter.route(scanner.nextLine());
    }
  }
}
