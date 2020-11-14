package com.dagger;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

//@Module
//public class EngineModule {
//
//    @Provides
//    @Singleton
//    Engine provideEngine(DieselEngine engine) {
//        return engine;
//    }
//}

//@Module
//public class EngineModule {
//    int horsePower;
//
//    public EngineModule(int horsePower) {
//        this.horsePower = horsePower;
//    }
//
//    @Provides
//    int provideHorsePower(){
//        return horsePower;
//    }
//
//    @Provides
//    @Singleton
//    Engine provideEngine(DieselEngine engine) {
////        return new DieselEngine(horsePower);
//        return engine;
//    }
//}


@Module
public abstract class EngineModule {

    @Binds
    @Singleton
    abstract Engine bindEngine(DieselEngine engine);
}
