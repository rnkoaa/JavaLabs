package com.richard.akka;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import akka.actor.AbstractLoggingActor;
import akka.japi.pf.ReceiveBuilder;

@Component
@Scope("prototype")
public class PrinterActor extends AbstractLoggingActor {
	@Override
	public void preStart() throws Exception {
		log().info("The Printer Actor has started.");
	}
	
	//@Autowired
	@SuppressWarnings("unchecked")
	public PrinterActor() {
		log().info("Constructing Printing Actor");
		/*receive(ReceiveBuilder.match(Indexable.class, indexable -> {
			log().info("Received Indexable Object");
			if(indexable != null){
				List<String> items = indexable.getItems();
				items.forEach(item -> {
					log().info("Printing Serialized Item: " + item);
				});
			}else{
				log().info("indexable object is null");
			}
		}).matchAny(this::unhandled).build());*/
		
		/*receive(ReceiveBuilder.match(List.class, items -> {
			log().info("Received Indexable Object");
			if(items != null && items.size() > 0){
				items.forEach(item -> {
					IndexedItem indexedItem = (IndexedItem)item;
					log().info("Printing Serialized Item: " + indexedItem.getName());
				});
			}
		}).matchAny(this::unhandled).build());*/
		
		receive(ReceiveBuilder.match(List.class, items -> {
			log().info("Received Indexable Object");
			if(items != null && items.size() > 0){
				items.forEach(item -> {
					String[] indexedItem = (String[])item;
					log().info("Printing Serialized Item: " + indexedItem.length);
					Stream<String> stream = Stream.of(indexedItem);
					stream.forEach(streamItem -> {
						System.out.println("Item: -> " + streamItem);
					});
				});
			}
		}).matchAny(this::unhandled).build());
	}
}
