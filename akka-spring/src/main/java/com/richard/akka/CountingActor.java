package com.richard.akka;

import static akka.japi.pf.ReceiveBuilder.match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import akka.actor.AbstractLoggingActor;

@Component
@Scope("prototype")
public class CountingActor extends AbstractLoggingActor {

	private int count = 0;

	public static class Count {
	}

	public static class Get {
	}

	@Override
	public void preStart() throws Exception {
		log().info("The Counting Actor has started.");
		
	}

	// the service that will be automatically injected
	final CountingService countingService;

	@Autowired
	public CountingActor(CountingService countingService) {
		this.countingService = countingService;
		
		receive(match(CountingActor.Count.class, countObj -> {
			System.out.println("Received Message to Increment count: " + count);
			count = countingService.increment(count);
		}).match(Get.class, getObj -> {
			sender().tell(count, self());
		}).matchAny(this::unhandled).build());
	}

}
