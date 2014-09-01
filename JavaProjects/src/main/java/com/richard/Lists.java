package com.richard;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.richard.json.PersonDeserializer;
import com.richard.json.PersonSerializer;
import com.richard.json.UserDeserializer;
import com.richard.json.UserSerializer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rnkoaa on 8/31/14.
 */
public class Lists {

    public static void main(String[] args) throws IOException {
        List<String> strings = Arrays.asList("Richard", "Agyei", "Amoako");

        /*strings.stream().forEach(System.out::println);
        strings.stream().map(String::toUpperCase)
                .forEach(System.out::println);*/

        //System.out.println(strings.stream().filter(string -> string.length() <= 6).count());

        List<Person> persons = Arrays.asList(
                new Person("kwame", "od", 31),
                new Person("Kwame", "ag", 25),
                new Person("Ama", "se", 28),
                new Person("Abena", "he", 33),
                new Person("Afia", "he", 14)
        );

        //persons.stream().filter(person -> person.getAge() < 30).forEach(System.out::println);

        //print first name of all persons that are below 30
        /*persons.stream().filter(person -> person.getAge() < 30)
                .forEach(person -> System.out.println(person.getFirstName()));*/

        //display all who's firstname is less than 4 chars
       /* persons.stream().filter(person -> person.getAge() <= 30)
                .collect(Collectors.toList()).forEach(System.out::println);*/

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Person.class, new PersonSerializer());
        module.addSerializer(User.class, new UserSerializer());
        module.addDeserializer(Person.class, new PersonDeserializer());
        module.addDeserializer(User.class, new UserDeserializer());
        mapper.registerModule(module);

        //System.out.println(mapper.writeValueAsString());
        Person richard = new Person("Richard", "Agyei", 31);
        String jsonRichard = mapper.writeValueAsString(richard);
        //System.out.println(jsonRichard);

        Person objRichard = mapper.readValue(jsonRichard, Person.class);
        //System.out.println(objRichard);
        //User user = new User("id-1", "rnkoaa", "nana0323", richard);
        //System.out.println(mapper.writeValueAsString(user));

        String s = mapper.writeValueAsString(persons);

        List<Person> listPersons = mapper.readValue(s,  new TypeReference<List<Person>>() {});
        System.out.println("Displaying data");
        listPersons.stream().forEach(System.out::println);
        //System.out.println(s);

        System.out.println("---------------------------------");
        User user = new User("id-1", "rnkoaa", "nana0323", richard);
        String userJson = mapper.writeValueAsString(user);
        System.out.println(userJson);

        User jsonUser = mapper.readValue(userJson, User.class);
        System.out.println(jsonUser);
    }
}
