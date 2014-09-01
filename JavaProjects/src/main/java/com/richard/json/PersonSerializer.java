package com.richard.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.richard.Person;

import java.io.IOException;

/**
 * Created by rnkoaa on 8/31/14.
 */
public class PersonSerializer extends JsonSerializer<Person> {
    @Override
    public void serialize(Person person, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeStringField("firstName", person.getFirstName());
        jgen.writeStringField("lastName", person.getLastName());
        jgen.writeNumberField("age", person.getAge());
        jgen.writeEndObject();
    }
}
