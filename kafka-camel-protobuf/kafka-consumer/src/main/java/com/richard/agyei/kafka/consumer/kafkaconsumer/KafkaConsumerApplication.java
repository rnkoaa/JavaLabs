package com.richard.agyei.kafka.consumer.kafkaconsumer;

import org.amoako.agyei.kafka.serializer.KeycloakRegistrationEventDeserializer;
import org.amoako.agyei.keycloak.RegistrationEvents;
import org.amoako.agyei.keycloak.RegistrationEvents.KeycloakRegistrationEvent;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.model.dataformat.ProtobufDataFormat;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class KafkaConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaConsumerApplication.class, args);
    }
}

@Component
class ConsumerRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        KafkaComponent kafka = getContext().getComponent("kafka", KafkaComponent.class);
        kafka.setBrokers("{{kafka.brokers.host}}:{{kafka.brokers.port}}");
        kafka.getConfiguration().setGroupId("in-memory-2");
        kafka.getConfiguration().setAutoOffsetReset("earliest");
        kafka.getConfiguration().setConsumersCount(1);
        kafka.getConfiguration().setValueDeserializer(KeycloakRegistrationEventDeserializer.class.getName());

        from("kafka:{{application.kafka.topic}}")
                .log("${body}")
                .to("stream:out");
    }
};