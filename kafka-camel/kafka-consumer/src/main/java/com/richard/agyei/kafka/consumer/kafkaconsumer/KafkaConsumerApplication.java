package com.richard.agyei.kafka.consumer.kafkaconsumer;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class KafkaConsumerApplication {

    @Bean
    CamelContextConfiguration camelContextConfiguration() {
        return new CamelContextConfiguration() {
            @Override
            public void beforeApplicationStart(CamelContext camelContext) {
                // setup kafka component with the brokers
                KafkaComponent kafka = new KafkaComponent();
                kafka.setBrokers("{{kafka.brokers.host}}:{{kafka.brokers.port}}");
                kafka.getConfiguration().setAutoOffsetReset("earliest");
                kafka.getConfiguration().setGroupId("test-group-1");
                kafka.getConfiguration().setConsumersCount(1);
                camelContext.addComponent("kafka", kafka);
            }

            @Override
            public void afterApplicationStart(CamelContext camelContext) {

            }
        };
    }


    public static void main(String[] args) {
        SpringApplication.run(KafkaConsumerApplication.class, args);
    }
}

@Component
class ConsumerRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("kafka:{{application.kafka.topic}}")
                .log("${body}");
                //.to("stream:out");
    }
};