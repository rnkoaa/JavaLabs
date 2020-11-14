package com.richard.dagger;

import dagger.Component;

@Component(modules = {CommandModule.class, SystemOutModule.class})
public interface CommandRouterComponent {
  CommandRouter commandRouter();
}
