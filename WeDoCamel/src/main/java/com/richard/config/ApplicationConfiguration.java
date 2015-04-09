package com.richard.config;

import com.richard.service.UserService;
import com.richard.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by rnkoaa on 3/25/15.
 */
@Configuration
@PropertySource({"classpath:application.properties"})
@ComponentScan(basePackages = {"com.richard"})
public class ApplicationConfiguration {

    @Bean(name = "activemqUrl")
    public String activemqUrl() {
        System.out.println("Configuring => " + "hello");
        return "hello";
    }

    @Bean
    public UserService userService(){
        return new UserServiceImpl();
    }
}
