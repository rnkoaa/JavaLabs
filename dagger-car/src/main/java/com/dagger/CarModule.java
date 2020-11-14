package com.dagger;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public abstract class CarModule {

    @Provides
    @Singleton
    static CarConfig carConfig() {
        return new CarConfig(2000);
    }
}
