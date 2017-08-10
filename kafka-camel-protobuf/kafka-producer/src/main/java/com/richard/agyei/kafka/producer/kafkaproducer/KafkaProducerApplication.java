package com.richard.agyei.kafka.producer.kafkaproducer;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
            //Endpoint endpoint = camelContext.getEndpoint("kafka:{{application.kafka.topic}}");
            // producerTemplate.set
            // producerTemplate.setDefaultEndpoint(endpoint);
            for (int i = 0; i < 25; i++) {
                System.out.println("Sending Message Index: " + i);
                producerTemplate.sendBody("kafka:{{application.kafka.topic}}", "This is a message from the message route! Index. " + i);
            }
        };
    }

    @Bean
    CamelContextConfiguration camelContextConfiguration() {
        return new CamelContextConfiguration() {
            @Override
            public void beforeApplicationStart(CamelContext camelContext) {
                // setup kafka component with the brokers
                KafkaComponent kafka = new KafkaComponent();
                kafka.setBrokers("{{kafka.brokers.host}}:{{kafka.brokers.port}}");
                camelContext.addComponent("kafka", kafka);

            }

            @Override
            public void afterApplicationStart(CamelContext camelContext) {

            }
        };
    }
}
