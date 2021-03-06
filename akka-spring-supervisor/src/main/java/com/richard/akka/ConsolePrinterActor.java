package com.richard.akka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;

@Component
@Scope("prototype")
public class ConsolePrinterActor extends AbstractLoggingActor  {
	private static final Logger logger = LoggerFactory.getLogger(ConsolePrinterActor.class);
	
	//@Autowired
	public ConsolePrinterActor() {
		receive(ReceiveBuilder.match(String.class, indexMessage -> {
			logger.info("Received String Message. {}", indexMessage);
			
			//tell your supervisor that you are done.
			sender().tell("consoleDone", ActorRef.noSender());
			
			//now kill yourself
			context().stop(self());
		}).matchAny(this::unhandled).build());
	}
}
