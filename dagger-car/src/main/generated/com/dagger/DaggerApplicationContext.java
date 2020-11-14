package com.dagger;

import dagger.internal.DoubleCheck;
import dagger.internal.InstanceFactory;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerApplicationContext implements ApplicationContext {
  private Provider<Integer> horsePowerProvider;

  private Provider<Integer> engineCapacityProvider;

  private Provider<DieselEngine> dieselEngineProvider;

  private Provider<Engine> bindEngineProvider;

  private Provider<Car> carProvider;

  private Provider<CarConfig> carConfigProvider;

  private DaggerApplicationContext(Integer horsePowerParam, Integer engineCapacityParam) {

    initialize(horsePowerParam, engineCapacityParam);
  }

  public static ApplicationContext.Builder builder() {
    return new Builder();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Integer horsePowerParam, final Integer engineCapacityParam) {
    this.horsePowerProvider = InstanceFactory.create(horsePowerParam);
    this.engineCapacityProvider = InstanceFactory.create(engineCapacityParam);
    this.dieselEngineProvider =
        DieselEngine_Factory.create(horsePowerProvider, engineCapacityProvider);
    this.bindEngineProvider = DoubleCheck.provider((Provider) dieselEngineProvider);
    this.carProvider =
        DoubleCheck.provider(Car_Factory.create(Wheel_Factory.create(), bindEngineProvider));
    this.carConfigProvider = DoubleCheck.provider(CarModule_CarConfigFactory.create());
  }

  @Override
  public Car getCar() {
    return carProvider.get();
  }

  @Override
  public CarConfig getCarConfig() {
    return carConfigProvider.get();
  }

  @Override
  public void inject(MainApplication mainApplication) {}

  private static final class Builder implements ApplicationContext.Builder {
    private Integer horsePower;

    private Integer engineCapacity;

    @Override
    public Builder horsePower(int horsePower) {
      this.horsePower = Preconditions.checkNotNull(horsePower);
      return this;
    }

    @Override
    public Builder engineCapacity(int engineCapacity) {
      this.engineCapacity = Preconditions.checkNotNull(engineCapacity);
      return this;
    }

    @Override
    public ApplicationContext build() {
      Preconditions.checkBuilderRequirement(horsePower, Integer.class);
      Preconditions.checkBuilderRequirement(engineCapacity, Integer.class);
      return new DaggerApplicationContext(horsePower, engineCapacity);
    }
  }
}
