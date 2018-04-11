package com.richard.akka;

import java.util.List;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

@Component
@Scope("prototype")
public class PrinterActor extends AbstractLoggingActor {
	private static final Logger logger = LoggerFactory.getLogger(PrinterActor.class);
	private int sizeOfMessages;
	private int receivedMessageCount = 0;


	@Override
	public void preStart() throws Exception {
		logger.info("The Printer Actor has started. {}", self().path().name());
	}

	// @Autowired
	public PrinterActor() {
		
		receive(ReceiveBuilder.match(IndexMessage.class, indexMessage -> {
			logger.info("Received Indexable Object");

			List<String> messages = indexMessage.getItems();
			sizeOfMessages = messages.size();
			IntStream.range(0, sizeOfMessages)
			.forEach(idx -> {
				String message = messages.get(idx);
				ActorRef consolePrinterSupervisor = context()
						.actorOf(Props.create(ConsolePrinterActor.class),"consolePrinterActor-"+idx);
				consolePrinterSupervisor.tell(message, self());
			});

			sender().tell("done", self());

			// now kill yourself
		}).match(String.class, reply -> {
			if (reply.equals("consoleDone")) {
				receivedMessageCount++;
				if (receivedMessageCount == sizeOfMessages) {
					logger.info("received all messages, I'm dying...");

				}
			}
		}).matchAny(this::unhandled).build());
	}
}
