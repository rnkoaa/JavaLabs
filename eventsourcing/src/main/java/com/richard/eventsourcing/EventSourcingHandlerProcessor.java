// https://www.baeldung.com/reflections-library

package com.richard.eventsourcing;

import com.richard.eventsourcing.annotations.Aggregate;
import com.richard.eventsourcing.annotations.AggregateRoot;
import com.richard.eventsourcing.annotations.Command;
import com.richard.eventsourcing.annotations.CommandHandler;
import com.richard.eventsourcing.annotations.Event;
import com.richard.eventsourcing.annotations.EventSourcingHandler;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.Executors;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

public class EventSourcingHandlerProcessor {

  static final Reflections reflections = new Reflections(new ConfigurationBuilder()
      .setUrls(ClasspathHelper.forPackage("com.richard.eventsourcing"))
      .setScanners(new FieldAnnotationsScanner(),
          new MethodAnnotationsScanner(),
          new TypeAnnotationsScanner(), new SubTypesScanner())
      .setExecutorService(Executors.newFixedThreadPool(4)));

  public static Set<Class<?>> getProcessingAggregates() {
    Set<Class<?>> aggregateRoots = getAggregateRoots();
    Set<Class<?>> aggregates = getAggregates();
    aggregates.addAll(aggregateRoots);
    return aggregates;
  }

  public static Set<Class<?>> getAggregateRoots() {
    return reflections.getTypesAnnotatedWith(AggregateRoot.class);
  }

  public static Set<Class<?>> getAggregates() {
    return reflections.getTypesAnnotatedWith(Aggregate.class);
  }

  public static Set<Constructor> getCommandHandlerConstructors() {
    return reflections.getConstructorsAnnotatedWith(CommandHandler.class);
  }

  public static Set<Method> getCommandHandlerMethods() {
    return reflections.getMethodsAnnotatedWith(CommandHandler.class);
  }

  public static Set<Constructor> getEventSourcingConstructors() {
    return reflections.getConstructorsAnnotatedWith(EventSourcingHandler.class);
  }

  public static Set<Class<?>> getEvents() {
    return reflections.getTypesAnnotatedWith(Event.class);
  }

  public static Set<Class<?>> getCommands() {
    return reflections.getTypesAnnotatedWith(Command.class);
  }

  public Set<Method> getEventSourcingHandlers() {
    return reflections.getMethodsAnnotatedWith(EventSourcingHandler.class);
  }
}
