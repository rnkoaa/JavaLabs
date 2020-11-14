package com.dagger;

import dagger.internal.Factory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class PetrolEngine_Factory implements Factory<PetrolEngine> {
  private final Provider<Integer> horsePowerProvider;

  public PetrolEngine_Factory(Provider<Integer> horsePowerProvider) {
    this.horsePowerProvider = horsePowerProvider;
  }

  @Override
  public PetrolEngine get() {
    return new PetrolEngine(horsePowerProvider.get());
  }

  public static PetrolEngine_Factory create(Provider<Integer> horsePowerProvider) {
    return new PetrolEngine_Factory(horsePowerProvider);
  }

  public static PetrolEngine newInstance(int horsePower) {
    return new PetrolEngine(horsePower);
  }
}
