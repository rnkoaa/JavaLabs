package com.dagger;

public class MainApplication {

    private final ApplicationContext applicationContext;

    public MainApplication(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = DaggerApplicationContext.builder()
                .horsePower(120)
                .engineCapacity(140)
                .build();
////                .engineModule(new EngineModule(200))
//                .build();
        MainApplication application = new MainApplication(applicationContext);
        application.run();
    }

    public void run() {

        applicationContext.inject(this);
        CarConfig carConfig = applicationContext.getCarConfig();
        System.out.println(carConfig);
        Car car = applicationContext.getCar();
        car.drive();

        Car car2 = applicationContext.getCar();
        car2.drive();
    }
}
