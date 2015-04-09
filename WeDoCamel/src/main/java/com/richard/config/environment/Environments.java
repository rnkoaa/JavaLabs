package com.richard.config.environment;

/**
 * Created by rnkoaa on 3/25/15.
 */
public class Environments {

    /*@Configuration
    @Profile("local")
    @PropertySource({"classpath:environment/local-application.properties"})
    public static class LocalConfig{
        @Bean
        public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
            *//*PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
            propertySourcesPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(Boolean.TRUE);
            propertySourcesPlaceholderConfigurer.setLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:META-INF/props/default*//**//*.properties"));
            return propertySourcesPlaceholderConfigurer;*//*
            return new PropertySourcesPlaceholderConfigurer();
        }
    }

    @Configuration
    @Profile("prod")
    @PropertySource({"classpath:environment/prod-application.properties"})
    public static class ProdConfig{

        @Bean
        public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
            return new PropertySourcesPlaceholderConfigurer();
        }

    }*/
}
