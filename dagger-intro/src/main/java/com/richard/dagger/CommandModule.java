package com.richard.dagger;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;

@Module
public abstract class CommandModule {

    @Binds
    @IntoMap
    @StringKey("login")
    abstract Command command(HelloCommand helloCommand);
}
