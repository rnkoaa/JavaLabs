package com.richard.eventsourcing;

import com.richard.eventsourcing.annotations.Command;
import com.richard.eventsourcing.command.ImmutableApproveClassifiedAd;
import java.util.UUID;

public class MainApplication {

  public static void main(String[] args) {
    MainApplication application = new MainApplication();
    CommandProcessor commandProcessor = new CommandProcessor();
    commandProcessor.handle(ImmutableApproveClassifiedAd.builder()
        .approverId(UUID.randomUUID())
        .classifiedAdId(UUID.randomUUID())
        .build());
//    application.handleCommand(ImmutableCreateClassifiedAd.builder()
//        .ownerId(UUID.randomUUID())
//        .build());

//    Optional<ConstructorInvoker> constructorForCommand = application.findConstructorForCommand(aggregateRoots, CreateClassifiedAd.class);
//    System.out.println(constructorForCommand.isPresent());
//    System.out.println(constructorForCommand.isEmpty());
//    ConstructorInvoker constructorInvoker = constructorForCommand.get();
//    System.out.println(constructorInvoker.aggregateRoot);
//    System.out.println(constructorInvoker.constructor);
//
//    System.out.println("--------------");
//    Optional<ConstructorInvoker> constructorForEvent = application.findConstructorForEvent(aggregateRoots, ClassifiedAdCreated.class);
//    System.out.println(constructorForEvent.isPresent());
//    System.out.println(constructorForEvent.isEmpty());
//    ConstructorInvoker eventConstructorInvoker = constructorForEvent.get();
//    System.out.println(eventConstructorInvoker.aggregateRoot);
//    System.out.println(eventConstructorInvoker.constructor);

    // invoke constructor
/*

    Set<Class<?>> events = EventSourcingHandlerProcessor.getEvents();
    Set<Class<?>> commands = EventSourcingHandlerProcessor.getCommands();

    for (Class<?> aggregateRoot : aggregateRoots) {
      System.out.println(aggregateRoot.getCanonicalName());
      Method[] declaredMethods = aggregateRoot.getDeclaredMethods();
      Set<Method> eventSourcingHandlerMethods = Arrays.stream(declaredMethods)
          .filter(method -> method.isAnnotationPresent(EventSourcingHandler.class))
          .collect(Collectors.toSet());

      Constructor<?>[] declaredConstructors = aggregateRoot.getDeclaredConstructors();
      Set<Constructor<?>> eventSourcingHandlerConstructors = Arrays.stream(declaredConstructors)
          .filter(method -> method.isAnnotationPresent(EventSourcingHandler.class))
          .collect(Collectors.toSet());
    }
*/

  }


  public void handleCommand(Object object) {
    if (object.getClass().isAnnotationPresent(Command.class)) {
      System.out.println("Will process Command");
    } else {
      System.out.println("Will not process command because command does not have annotation");
    }
  }
}

//class Scratch {
//
//  public static void main(String[] args) {
//    import com.marketplace.domain.classifiedad.command.CreateClassifiedAd;
//import com.marketplace.domain.classifiedad.command.UpdateClassifiedAd;
//import com.marketplace.domain.classifiedad.command.UpdateClassifiedAdText;
//import com.marketplace.domain.classifiedad.command.UpdateClassifiedAdTitle;
//import com.marketplace.eventstore.annotations.EventSourcingHandler;
//
//    public class EventSourceAggregate {
//
//
//      @EventSourcingHandler
//      public EventSourceAggregate(CreateClassifiedAd createClassifiedAd) {
//
//      }
//
//      @EventSourcingHandler
//      public void on(UpdateClassifiedAdText updateClassifiedAdText) {
//
//      }
//
//      @EventSourcingHandler
//      public void on(UpdateClassifiedAdTitle updateClassifiedAdText) {
//
//      }
//
//      @EventSourcingHandler
//      public void on(UpdateClassifiedAd updateClassifiedAd) {
//
//      }
//
//
//    }

/*
compile time, find list of classes annotated with AggregateRoot

// find all constructors and methods annotated with EventSourcingHandler,
// Create caching for use during runtime.

// find all constructors and methods annotated with CommandHandler,
// Create caching for use during runtime.
 */
/*
//    List<Class<?>>
    Map<Class<?>, Object> invokes = new HashMap<>();
    Constructor<?>[] constructors = EventSourceAggregate.class.getDeclaredConstructors();
    for (Constructor<?> constructor : constructors) {
      if (constructor.getParameterCount() == 0) {
        invokes.put(EventSourceAggregate.class, constructor);
        System.out.println("Found default constructor");
      } else {
        System.out.println("No default constructor found");
      }
    }

    if (!invokes.containsKey(CreateClassifiedAd.class)) {
      for (Constructor<?> constructor : constructors) {
        if (constructor.getParameterCount() == 1) {
          Class<?> parameterTypes = constructor.getParameterTypes()[0];
          if (parameterTypes.isAssignableFrom(CreateClassifiedAd.class)) {
            invokes.put(CreateClassifiedAd.class, constructor);
          }
        }
      }
    }

    Method[] declaredMethods = EventSourceAggregate.class.getDeclaredMethods();
    for (Method declaredMethod : declaredMethods) {
      Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
      if (parameterTypes.length == 1) {
        Class<?> parameterType = parameterTypes[0];
        if (parameterType.isAssignableFrom(CreateClassifiedAd.class)) {
          System.out.println(declaredMethod.getName());
          invokes.put(CreateClassifiedAd.class, declaredMethod);
        }else if(parameterType.isAssignableFrom(UpdateClassifiedAdTitle.class)){
          invokes.put(UpdateClassifiedAdTitle.class, declaredMethod);
        }
      }
    }

    System.out.println(invokes);
    invokes.forEach((key, value) -> {
      System.out.println(value.getClass());
      // java.lang.reflect.Constructor
      //get constructor that takes a String as argument
//    Constructor constructor = MyObject.class.getConstructor(String.class);
//
//    MyObject myObject = (MyObject)
//        constructor.newInstance("constructor-arg1");

      // class java.lang.reflect.Method
      //  Operations operationsInstance = new Operations();
      //    Double result
      //      = (Double) sumInstanceMethod.invoke(operationsInstance, 1, 3);
    });




//    Constructor<?>[] declaredConstructors = ClassifiedAd.class.getDeclaredConstructors();
//    for (Constructor<?> declaredConstructor : declaredConstructors) {
//      for (Class<?> parameterType : declaredConstructor.getParameterTypes()) {
////        parameterType
//        if (parameterType.isAssignableFrom(List.class)) {
//
//
////          Class<?> persistentClass = (Class<?>)
////              ((ParameterizedType)parameterType.getGenericSuperclass())
////                  .getActualTypeArguments()[0];
////          System.out.println(persistentClass);
//
////         genericInterface.getTypeName()
////          parameterType
////          ParameterizedType parameterizedType = (ParameterizedType) parameterType;
//        }
//      }
//    }
 */
//
//  }
//}
