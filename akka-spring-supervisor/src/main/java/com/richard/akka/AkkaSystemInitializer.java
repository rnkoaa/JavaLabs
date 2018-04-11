package com.richard.akka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import akka.actor.ActorSystem;

@Configuration
public class AkkaSystemInitializer implements InitializingBean, FactoryBean<ActorSystem>, DisposableBean {
	private final Logger logger = LoggerFactory.getLogger(AkkaSystemInitializer.class);
	
	/**
	 * Actor system singleton for this application. the application context is
	 * needed to initialize the Akka Spring Extension
	 */
	ActorSystem actorSystem;
	
	private final ApplicationContext applicationContext;

	public AkkaSystemInitializer(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public void destroy() throws Exception {
		logger.info("Killing the Akka Actor System");
		actorSystem.terminate();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		actorSystem = ActorSystem.create("AkkaJavaSpring");
		// initialize the application context in the Akka Spring Extension
		SpringExtension.SpringExtProvider.get(actorSystem).initialize(applicationContext);
	}

	@Override
	public ActorSystem getObject() throws Exception {
		return actorSystem;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public Class<?> getObjectType() {
		return ActorSystem.class;
	}

}
