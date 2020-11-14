package com.dagger;

import dagger.internal.Factory;
import javax.annotation.processing.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class Wheel_Factory implements Factory<Wheel> {
  private static final Wheel_Factory INSTANCE = new Wheel_Factory();

  @Override
  public Wheel get() {
    return new Wheel();
  }

  public static Wheel_Factory create() {
    return INSTANCE;
  }

  public static Wheel newInstance() {
    return new Wheel();
  }
}
