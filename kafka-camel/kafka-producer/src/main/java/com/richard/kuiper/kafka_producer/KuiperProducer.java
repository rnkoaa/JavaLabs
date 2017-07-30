package com.richard.kuiper.kafka_producer;

import java.util.Scanner;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * Hello world!
 */
public class KuiperProducer {
    private static Scanner in;

    public static class KafkaConsumerRouteBuilder extends RouteBuilder {

        @Override
        public void configure() throws Exception {
            from("kafka:192.168.99.100:9092?topic=test&groupId=kuiper-consumers&autoOffsetReset=earliest&consumersCount=1")
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
                    }).to("log:input");
        }
    }

    public static void main(String[] argv) throws Exception {
        in = new Scanner(System.in);
        System.out.println("Enter message(type exit to quit)");

        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new KafkaConsumerRouteBuilder());
        context.start();

        ProducerTemplate producerTemplate = context.createProducerTemplate();
        String line = in.nextLine();
        while (!line.equals("exit")) {
            producerTemplate.sendBody("kafka:192.168.99.100:9092?topic=test", line);
            line = in.nextLine();
        }
        in.close();


    }

}
