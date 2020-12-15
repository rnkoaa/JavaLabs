package com.richard.eventsourcing;

import com.richard.eventsourcing.annotations.CommandHandler;
import com.richard.eventsourcing.annotations.EventSourcingHandler;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

public class TypeProcessor {

  public static final Set<Class<?>> aggregateRoots = EventSourcingHandlerProcessor.getProcessingAggregates();

  public static record ConstructorInvoker(Class<?> aggregateRoot, Constructor<?> constructor) {

  }

  public static record MethodInvoker(Class<?> aggregateRoot, Method method) {

  }

  Optional<ConstructorInvoker> findConstructorForCommand(Set<Class<?>> aggregateRoots, Class<?> command) {
    return findConstructorWithAnnotation(aggregateRoots, command, CommandHandler.class);
  }

  Optional<MethodInvoker> findMethodForCommand(Set<Class<?>> aggregateRoots, Class<?> command) {
    return findMethodWithAnnotation(aggregateRoots, command, CommandHandler.class);
  }

  Optional<ConstructorInvoker> findConstructorForEvent(Set<Class<?>> aggregateRoots, Class<?> command) {
    return findConstructorWithAnnotation(aggregateRoots, command, EventSourcingHandler.class);
  }


  Optional<MethodInvoker> findMethodForEvent(Set<Class<?>> aggregateRoots, Class<?> command) {
    return findMethodWithAnnotation(aggregateRoots, command, EventSourcingHandler.class);
  }

  Optional<ConstructorInvoker> findConstructorWithAnnotation(Set<Class<?>> aggregateRoots, Class<?> command,
      Class<? extends Annotation> annotationClass) {
    return aggregateRoots.stream()
        .flatMap(aggregateRoot -> {
          Constructor<?>[] declaredConstructors = aggregateRoot.getDeclaredConstructors();
          return Arrays.stream(declaredConstructors)
              .filter(method -> method.isAnnotationPresent(annotationClass))
              .filter(constructor -> constructor.getParameterCount() == 1)
              .filter(constructor -> {
                Class<?> parameterType = constructor.getParameterTypes()[0];
                return parameterType.isAssignableFrom(command);
              })
              .map(it -> new ConstructorInvoker(aggregateRoot, it));

        })
        .findFirst();
  }

  Optional<MethodInvoker> findMethodWithAnnotation(Set<Class<?>> aggregateRoots, Class<?> command,
      Class<? extends Annotation> annotationClass) {
    return aggregateRoots.stream()
        .flatMap(aggregateRoot -> {
          Method[] declaredMethods = aggregateRoot.getDeclaredMethods();
          return Arrays.stream(declaredMethods)
              .filter(method -> method.isAnnotationPresent(annotationClass))
              .filter(constructor -> constructor.getParameterCount() == 1)
              .filter(constructor -> {
                Class<?> parameterType = constructor.getParameterTypes()[0];
                return parameterType.isAssignableFrom(command);
              })
              .map(it -> new MethodInvoker(aggregateRoot, it));

        })
        .findFirst();
  }

}
