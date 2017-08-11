package com.richard.agyei.kafka.producer.kafkaproducer;

import org.amoako.agyei.kafka.serializer.KeycloakRegistrationEventSerializer;
import org.amoako.agyei.keycloak.RegistrationEvents;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class KafkaProducerApplication {
    /*
     *    if (inputStream == null) {
     throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
     }*/

    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(KafkaProducerApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(CamelContext camelContext) {
        return args -> {
            ProducerTemplate producerTemplate = camelContext.createProducerTemplate();

            RegistrationEvents.KeycloakRegistrationEvent registrationEvent =
                    RegistrationEvents.KeycloakRegistrationEvent.newBuilder()
                            .setEventTime(System.currentTimeMillis())
                            .setUserId(UUID.randomUUID().toString())
                            .setEmail("richard.agyei@gmail.com")
                            .setUsername("rnkoaa")
                            .build();

            producerTemplate.sendBody("kafka:{{application.kafka.topic}}", registrationEvent);
        };
    }

    @Bean
    CamelContextConfiguration camelContextConfiguration() {
        return new CamelContextConfiguration() {
            @Override
            public void beforeApplicationStart(CamelContext camelContext) {
                KafkaComponent kafka = camelContext.getComponent("kafka", KafkaComponent.class);
                kafka.setBrokers("{{kafka.brokers.host}}:{{kafka.brokers.port}}");
                kafka.getConfiguration().setSerializerClass(KeycloakRegistrationEventSerializer.class.getName());
            }

            @Override
            public void afterApplicationStart(CamelContext camelContext) {

            }
        };
    }
}
