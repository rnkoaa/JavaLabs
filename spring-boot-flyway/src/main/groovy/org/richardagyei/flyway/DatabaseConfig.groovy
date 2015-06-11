package org.richardagyei.flyway

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.DisposableBean
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.orm.jpa.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement

import javax.sql.DataSource

/**
 * Created by rnkoaa on 6/10/15.
 *
 */
@Configuration
@EnableJpaRepositories(basePackages = ["org.richardagyei.flyway"])
@EntityScan(basePackages = ["org.richardagyei.flyway"])
@EnableAutoConfiguration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
class DatabaseConfig implements DisposableBean {
    private EmbeddedDatabase ed;
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        if (this.ed == null) {
            EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
            this.ed = builder.setType(EmbeddedDatabaseType.H2).build();
        }

        return this.ed;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("org.richardagyei.flyway");
        factory.setDataSource(dataSource());

        Properties ps = new Properties();
        ps.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        ps.put("hibernate.hbm2ddl.auto", "validate");
        ps.put("jadira.usertype.autoRegisterUserTypes", "true");

        factory.setJpaProperties(ps);
        factory.afterPropertiesSet();

        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(entityManagerFactory().getObject());
        return tm;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Override
    public void destroy() {

        if (this.ed != null) {
            this.ed.shutdown();
        }

    }
}
