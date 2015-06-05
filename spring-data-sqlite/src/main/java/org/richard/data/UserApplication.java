package org.richard.data;

import org.richard.data.domain.Role;
import org.richard.data.domain.User;
import org.richard.data.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by rnkoaa on 6/4/15.
 */

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"org.richard.data"})
public class UserApplication {


    @Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.sqlite.JDBC");
        dataSourceBuilder.url("jdbc:sqlite:spring-data-sqlite.db");
        return dataSourceBuilder.build();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(UserApplication.class);

        UserService userService = context.getBean(UserService.class);

        User user = new User("Patricia", "Sarpong");
        user.setUsername("patty");

        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ROLE_ADMIN"));
        roles.add(new Role("ROLE_USER"));
        user.setRoles(roles);

        User saved = userService.save(user);
        System.out.println("Saved User Id: " + saved.getId());


        User richard = new User("Richard", "Agyei");
        richard.setUsername("rnkoaa");
        richard.setRoles(Collections.singleton(new Role("ROLE_ADMIN")));
        User savedRichard = userService.save(richard);
        System.out.println("Saved Another User: " + savedRichard.getId());

        /*Optional<Role> optionalRole = roleRepository.findRoleByName("ROLE_ADMIN");
        if (optionalRole.isPresent())
            System.out.println("Found Role: " + optionalRole.get());*/
        context.close();
    }
}
