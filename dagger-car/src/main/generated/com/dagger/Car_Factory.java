package com.dagger;

import dagger.internal.Factory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class Car_Factory implements Factory<Car> {
  private final Provider<Wheel> wheelProvider;

  private final Provider<Engine> engineProvider;

  public Car_Factory(Provider<Wheel> wheelProvider, Provider<Engine> engineProvider) {
    this.wheelProvider = wheelProvider;
    this.engineProvider = engineProvider;
  }

  @Override
  public Car get() {
    return new Car(wheelProvider.get(), engineProvider.get());
  }

  public static Car_Factory create(Provider<Wheel> wheelProvider, Provider<Engine> engineProvider) {
    return new Car_Factory(wheelProvider, engineProvider);
  }

  public static Car newInstance(Wheel wheel, Engine engine) {
    return new Car(wheel, engine);
  }
}
