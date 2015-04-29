package com.richard.boot.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by U0165547 on 4/29/2015.
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories
@ComponentScan(basePackages = {
        "com.richard.boot.config",
        "com.richard.boot.controller",
        "com.richard.boot.repository",
        "com.richard.boot.domain"
})
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);


      /*  System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }*/
    }
}
