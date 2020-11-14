package com.dagger;

import dagger.internal.Factory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DieselEngine_Factory implements Factory<DieselEngine> {
  private final Provider<Integer> horsePowerProvider;

  private final Provider<Integer> engineCapacityProvider;

  public DieselEngine_Factory(
      Provider<Integer> horsePowerProvider, Provider<Integer> engineCapacityProvider) {
    this.horsePowerProvider = horsePowerProvider;
    this.engineCapacityProvider = engineCapacityProvider;
  }

  @Override
  public DieselEngine get() {
    return new DieselEngine(horsePowerProvider.get(), engineCapacityProvider.get());
  }

  public static DieselEngine_Factory create(
      Provider<Integer> horsePowerProvider, Provider<Integer> engineCapacityProvider) {
    return new DieselEngine_Factory(horsePowerProvider, engineCapacityProvider);
  }

  public static DieselEngine newInstance(int horsePower, int engineCapacity) {
    return new DieselEngine(horsePower, engineCapacity);
  }
}
