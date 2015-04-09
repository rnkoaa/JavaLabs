package com.richard;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.richard.json.PersonDeserializer;
import com.richard.json.PersonSerializer;
import com.richard.json.UserDeserializer;
import com.richard.json.UserSerializer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created on 9/1/14.
 * This is a simple demonstration of the power of jackson json object mapper.
 */
public class JacksonTests {
    private static ObjectMapper objectMapper;
    private Person personOne;
    private User user;
    private List<Person> persons;

    @BeforeClass
    public static void doStaging() {
        objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Person.class, new PersonSerializer());
        module.addSerializer(User.class, new UserSerializer());
        module.addDeserializer(Person.class, new PersonDeserializer());
        module.addDeserializer(User.class, new UserDeserializer());
        objectMapper.registerModule(module);
    }

    @Before
    public void setup() {
        personOne = new Person("Jackson", "Monroe", 31);
        persons = Arrays.asList(
                new Person("John", "Kennedy", 31),
                new Person("Jason", "Siegel", 25),
                new Person("Ike", "Eisenhower", 28),
                new Person("Dwight", "hoover", 33),
                new Person("Ronald", "he", 14)
        );
        user = new User("id-1", "ctdg", "aaddcc", personOne);
    }

    @Test
    public void testSingleSerializationAndDeserialization() throws IOException {
        String singlePersonString = objectMapper.writeValueAsString(personOne);
        assertThat(singlePersonString).isNotNull()
                .isEqualTo("{\"firstName\":\"Jackson\",\"lastName\":\"Monroe\",\"age\":31}");

        Person objPerson = objectMapper.readValue(singlePersonString, Person.class);
        assertThat(objPerson)
                .isNotNull()
                .isEqualTo(personOne);
    }


    @Test
    public void testEmbeddedObjects() throws IOException {
        String userJson = objectMapper.writeValueAsString(user);
        System.out.println(userJson);
        assertThat(userJson)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo("{\"id\":\"id-1\",\"username\":\"ctdg\",\"person\":" +
                        "{\"firstName\":\"Jackson\",\"lastName\":\"Monroe\",\"age\":31}}");

        User userFromJson = objectMapper.readValue(userJson, User.class);
        assertThat(user)
                .isNotNull()
                .isEqualTo(userFromJson);
    }

    @Test
    public void testCollectionSerializationDeserializations() throws IOException {
        String personArrayJson = objectMapper.writeValueAsString(persons);
        System.out.println(personArrayJson);
        assertThat(personArrayJson)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo("[{\"firstName\":\"John\",\"lastName\":\"Kennedy\",\"age\":31}," +
                        "{\"firstName\":\"Jason\",\"lastName\":\"Siegel\",\"age\":25}," +
                        "{\"firstName\":\"Ike\",\"lastName\":\"Eisenhower\",\"age\":28}," +
                        "{\"firstName\":\"Dwight\",\"lastName\":\"hoover\",\"age\":33}," +
                        "{\"firstName\":\"Ronald\",\"lastName\":\"he\",\"age\":14}]");

        List<Person> listPersons = objectMapper.readValue(personArrayJson, new TypeReference<List<Person>>() {
        });
        assertThat(listPersons).isNotNull()
                .isNotEmpty()
                .hasSize(persons.size());
    }
}
