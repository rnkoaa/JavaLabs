package com.richard.akka;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

@SpringBootApplication
public class AkkaSpringApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(AkkaSpringApplication.class);

	@Autowired
	ActorSystem actorSystem;

	public static void main(String[] args) {
		SpringApplication.run(AkkaSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("Hello, We have started a new Spring Project");
		logger.info("Joining thread, you can press Ctrl+C to shutdown application");
		// Thread.currentThread().join();
		assert (actorSystem != null);
/*
		List<IndexedItem> items = Arrays.asList(new IndexedItem("Richard"),
				new IndexedItem("Agyei"),
				new IndexedItem("Jacqueline"),
				new IndexedItem("Odame"),
				new IndexedItem("Amoako"));*/
		
		List<String[]> arrays = Arrays.asList(new String[]{"Hello", "World"},
				new String[]{"Richard", "Odame"},
				new String[]{"Raj", "Ramadoss"},
				new String[]{"Peter", "James", "Leyden"});
		//Indexable indexable = new Indexable(items);
		ActorRef printer = actorSystem.actorOf(SpringExtension.SpringExtProvider.get(actorSystem).props("printerActor"),
				"printer");

		printer.tell(arrays, null);
		/*
		 * ActorRef counter =
		 * actorSystem.actorOf(SpringExtension.SpringExtProvider.get(actorSystem
		 * ).props("countingActor"), "counter"); // tell it to count three times
		 * counter.tell(new Count(), null); counter.tell(new Count(), null);
		 * counter.tell(new Count(), null);
		 * 
		 * // print the result FiniteDuration duration =
		 * FiniteDuration.create(3, TimeUnit.SECONDS); Future<Object> result =
		 * ask(counter, new Get(), Timeout.durationToTimeout(duration)); try {
		 * System.out.println("Got back " + Await.result(result, duration)); }
		 * catch (Exception e) { System.err.println("Failed getting result: " +
		 * e.getMessage()); throw e; } finally { actorSystem.terminate(); }
		 */
	}

	/**
	 * Actor system singleton for this application. the application context is
	 * needed to initialize the Akka Spring Extension
	 */
	@Bean
	public ActorSystem actorSystem(ApplicationContext applicationContext) {
		ActorSystem system = ActorSystem.create("AkkaJavaSpring");
		// initialize the application context in the Akka Spring Extension
		SpringExtension.SpringExtProvider.get(system).initialize(applicationContext);
		return system;
	}
}
