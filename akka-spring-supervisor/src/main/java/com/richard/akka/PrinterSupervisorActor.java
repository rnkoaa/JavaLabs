package com.richard.akka;

import java.util.Arrays;
import java.util.List;

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
public class PrinterSupervisorActor extends AbstractLoggingActor {
	private static final Logger logger = LoggerFactory.getLogger(PrinterSupervisorActor.class);
	@Override
	public void preStart() throws Exception {
		logger.info("The Printer Supervisor Actor has started. {}", self().path().name());
	}
	
	//@Autowired
	public PrinterSupervisorActor() {
		ActorRef printer = context().actorOf(Props.create(PrinterActor.class), "PrinterActor"); //(PrinterActor.class, "printerActor");
		
		receive(ReceiveBuilder.match(String.class, cmd -> {
			if(cmd.equalsIgnoreCase("start")){
				List<String> items = Arrays.asList("Richard",
						"Amoako", "Started", "Something", "great", "is", "Agyei");
				IndexMessage indexMessage = new IndexMessage(items);
			
				printer.tell(indexMessage, self());
			}else if(cmd.equalsIgnoreCase("done")){
				logger.info("Received a done Message from My done, exiting...");
				//context().stop(printer);
				context().stop(self());
				//System.exit(0);
				
			}
		}).matchAny(this::unhandled).build());
	}
}
