package com.richard.boot.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Created by U0165547 on 4/29/2015.
 */
@Configuration
public class DatabaseConfig {


    /**
     * <bean id="liquibase" class="liquibase.integration.spring.SpringLiquibase">
     * <property name="dataSource" ref="myDataSource" />
     * <property name="changeLog" value="classpath:db-changelog.xml" />
     * <p/>
     * <!--
     * contexts specifies the runtime contexts to use.
     * -->
     * <property name="contexts" value="test, production" />
     * </bean>
     */

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:db/changelog/db.changelog-master.xml");
        return liquibase;
    }
}
