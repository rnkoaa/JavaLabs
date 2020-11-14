package com.richard.dagger;

import dagger.Module;
import dagger.Binds;

@Module
abstract class LoginCommandModule {
  @Binds
  abstract Command loginCommand(LoginCommand command);
}
