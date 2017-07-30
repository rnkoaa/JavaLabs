package com.kuiper.kafka.consumer;

import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

public class KuiperConsumer {
    private static Scanner in;

	public static void main(String[] argv) throws InterruptedException {
		// TODO Auto-generated method stub
		if (argv.length != 2) {
            System.err.printf("Usage: %s <topicName> <groupId>\n",
            		KuiperConsumer.class.getSimpleName());
            System.exit(-1);
        }
        in = new Scanner(System.in);
        String topicName = argv[0];
        String groupId = argv[1];

        ConsumerThread consumerRunnable = new ConsumerThread(topicName,groupId);
        consumerRunnable.start();
        String line = "";
        while (!line.equals("exit")) {
            line = in.next();
        }
        consumerRunnable.getKafkaConsumer().wakeup();
        System.out.println("Stopping consumer .....");
        consumerRunnable.join();
	}
	
	 private static class ConsumerThread extends Thread{
	        private String topicName;
	        private String groupId;
	        private KafkaConsumer<String,String> kafkaConsumer;

	        public ConsumerThread(String topicName, String groupId){
	            this.topicName = topicName;
	            this.groupId = groupId;
	        }
	        public void run() {
	            Properties configProperties = new Properties();
	            configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.99.100:9092");
	            configProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArrayDeserializer");
	            configProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
	            configProperties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
	            configProperties.put(ConsumerConfig.CLIENT_ID_CONFIG, "simple");

	            //Figure out where to start processing messages from
	            kafkaConsumer = new KafkaConsumer<String, String>(configProperties);
	            kafkaConsumer.subscribe(Arrays.asList(topicName));
	            //Start processing messages
	            try {
	                while (true) {
	                    ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
	                    for (ConsumerRecord<String, String> record : records)
	                        System.out.println(record.value());
	                }
	            }catch(WakeupException ex){
	                System.out.println("Exception caught " + ex.getMessage());
	            }finally{
	                kafkaConsumer.close();
	                System.out.println("After closing KafkaConsumer");
	            }
	        }
	        public KafkaConsumer<String,String> getKafkaConsumer(){
	           return this.kafkaConsumer;
	        }
	    }

}
