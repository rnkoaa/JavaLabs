package com.richard.akka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
		logger.info("Actor System Started");
		ActorRef printerSupervisor = actorSystem
				.actorOf(SpringExtension.SpringExtProvider.get(actorSystem).props("printerSupervisorActor"),"printer");

		printerSupervisor.tell("start", ActorRef.noSender());
	}
}
