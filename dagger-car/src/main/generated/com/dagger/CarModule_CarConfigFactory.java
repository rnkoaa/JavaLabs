package com.dagger;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;

@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class CarModule_CarConfigFactory implements Factory<CarConfig> {
  @Override
  public CarConfig get() {
    return carConfig();
  }

  public static CarModule_CarConfigFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CarConfig carConfig() {
    return Preconditions.checkNotNull(CarModule.carConfig(), "Cannot return null from a non-@Nullable @Provides method");
  }

  private static final class InstanceHolder {
    private static final CarModule_CarConfigFactory INSTANCE = new CarModule_CarConfigFactory();
  }
}
