/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.breman;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.UUID;

public class App {

    public static void main(String[] args) throws JsonProcessingException {
        var objectMapper = new ObjectMapper().findAndRegisterModules();
        SimpleModule module = new SimpleModule();
        module.addSerializer(UserId.class, new UserIdSerializer());
        module.addDeserializer(UserId.class, new UserIdDeserializer());
        objectMapper.registerModule(module);

        var person = new Person(new UserId(UUID.randomUUID()), "Richard");
        String personString = objectMapper.writeValueAsString(person);
        System.out.println(personString);

        var o = objectMapper.readValue(personString, Person.class);
        System.out.println(o);


    }
}
