package com.dagger;

import dagger.BindsInstance;
import dagger.Component;

import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Component(modules = {CarModule.class, EngineModule.class})
public interface ApplicationContext {

    Car getCar();

    CarConfig getCarConfig();

    void inject(MainApplication mainApplication);

    @Component.Builder
    interface Builder {
        ApplicationContext build();

        @BindsInstance
        Builder horsePower(@Named("horsePower") int horsePower);

        @BindsInstance
        Builder engineCapacity(@Named("engineCapacity") int engineCapacity);

    }
}
