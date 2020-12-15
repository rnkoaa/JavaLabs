package com.richard.eventsourcing;

import com.richard.eventsourcing.annotations.Command;
import com.richard.eventsourcing.annotations.TargetAggregateIdentifier;
import io.vavr.control.Try;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

public class CommandProcessor extends TypeProcessor {

  public void handle(Object command) {
    if (!command.getClass().isAnnotationPresent(Command.class)) {
      throw new IllegalArgumentException("Command Must have `Command` annotation for it to be processed");
    }

    // can either be uuid or optional<uuid>
    Optional<Object> tryAggregateId = getAggregateId(command)
        .toJavaOptional();

    Optional<ConstructorInvoker> constructorForCommand = findConstructorForCommand(aggregateRoots, command.getClass());
    constructorForCommand.ifPresentOrElse(constructorInvoker -> {
      // compute constructor on aggregateRoot
      System.out.println("Handling Command using Constructor");

      // load aggregate from aggregateStore, if not found, create using constructor or default constructor
    }, () -> {
      Optional<MethodInvoker> methodForCommand = findMethodForCommand(aggregateRoots, command.getClass());
      MethodInvoker methodInvoker = methodForCommand
          .orElseThrow(() -> new IllegalArgumentException("Unable to process command, could not find method or constructor to "
              + "process command"));

      System.out.println("Handling Command using method");
      // process method on aggregate root
    });
  }

  static record AggregateMetadata(Object id, Class<?> clazz) {

  }

  Try<Object> getAggregateId(Object command) {
    Field[] declaredFields = command.getClass().getDeclaredFields();
    Optional<Field> first = Arrays.stream(declaredFields)
        .filter(declaredField -> {
          declaredField.setAccessible(true);
          return declaredField.isAnnotationPresent(TargetAggregateIdentifier.class);
        })
        .findFirst();

    if (first.isPresent()) {
      Field field = first.get();
      return Try.of(() -> field.get(command));
    } else {
      Optional<Method> identifierMethod = Arrays.stream(command.getClass().getDeclaredMethods())
          .filter(declaredMethod -> {
            declaredMethod.setAccessible(true);
            return declaredMethod.isAnnotationPresent(TargetAggregateIdentifier.class);
          })
          .findFirst();
      if (identifierMethod.isPresent()) {
        Method method = identifierMethod.get();
        return Try.of(() -> method.invoke(command));
      }
    }
    return Try.failure(new AggregateIdentifierNotFoundException());
  }

}
