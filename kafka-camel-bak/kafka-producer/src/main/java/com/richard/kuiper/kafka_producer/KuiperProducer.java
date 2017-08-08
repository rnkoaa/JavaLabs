package com.richard.kuiper.kafka_producer;

import java.util.Scanner;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * Hello world!
 */
public class KuiperProducer {
    private static Scanner in;

    public static class KafkaProducerRouteBuilder extends RouteBuilder {

        @Override
        public void configure() throws Exception {
            /*from("direct:kafkaRoute")
                    .to("kafka:{{kafka.host}}:{{kafka.port}}?" +
                            "topic=javaKafkaBasicTopicName&" +
                            "groupId=javaKafkaBasicGroupId&" +
                            "autoOffsetReset=earliest&" +
                            "consumersCount=1");*/

            /*from("direct:kafkaRoute")
                    .to("kafka:javaKafkaBasicTopicName");*/

            from("direct:kafkaRoute")
                    .to("kafka:{{producer.topic}}");
        }
    }

    public static class KafkaConsumerRouteBuilder extends RouteBuilder {

        @Override
        public void configure() throws Exception {
            PropertiesComponent pc = getContext().getComponent("properties", PropertiesComponent.class);
            pc.setLocation("classpath:application.properties");
            //from("kafka:localhost:9092?topic=test&groupId=testConsumerGroup&autoOffsetReset=earliest&consumersCount=1")
            from("kafka:{{consumer.topic}}?brokers={{kafka.host}}:{{kafka.port}}"
                    + "&maxPollRecords={{consumer.maxPollRecords}}"
                    + "&consumersCount={{consumer.consumersCount}}"
                    + "&seekTo={{consumer.seekTo}}"
                    + "&groupId={{consumer.group}}")
                    .routeId("consumerRoute")
                    .process(exchange -> {
                        String messageKey = "";
                        if (exchange.getIn() != null) {
                            Message message = exchange.getIn();
                            Integer partitionId = (Integer) message
                                    .getHeader(KafkaConstants.PARTITION);
                            String topicName = (String) message
                                    .getHeader(KafkaConstants.TOPIC);
                            if (message.getHeader(KafkaConstants.KEY) != null)
                                messageKey = (String) message
                                        .getHeader(KafkaConstants.KEY);
                            Object data = message.getBody();


                            System.out.println("topicName :: "
                                    + topicName + " partitionId :: "
                                    + partitionId + " messageKey :: "
                                    + messageKey + " message :: "
                                    + data + "\n");
                        }
                    }).to("stream:out");
            //.to("log:input");
        }

    }

    public static void main(String[] argv) throws Exception {
        CamelContext context = new DefaultCamelContext();
        PropertiesComponent pc = context.getComponent("properties", PropertiesComponent.class);
        pc.setLocation("classpath:application.properties");

        // setup kafka component with the brokers
        KafkaComponent kafka = new KafkaComponent();
        kafka.setBrokers("{{kafka.host}}:{{kafka.port}}");
        //context.addRoutes(new KafkaConsumerRouteBuilder());
        context.addComponent("kafka", kafka);

        context.addRoutes(new KafkaProducerRouteBuilder());
        ProducerTemplate producerTemplate = context.createProducerTemplate();
        context.start();

        producerTemplate.sendBody("direct:kafkaRoute", "This is a message from the /message route!");

    }

}
