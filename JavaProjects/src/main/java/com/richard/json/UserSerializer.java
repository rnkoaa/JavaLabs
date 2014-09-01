package com.richard.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.richard.User;

import java.io.IOException;

/**
 * Created by rnkoaa on 8/31/14.
 */
public class UserSerializer extends JsonSerializer<User> {
    @Override
    public void serialize(User user, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeStringField("id", user.getId());
        jgen.writeStringField("username", user.getUserName());
        jgen.writeObjectField("person", user.getPerson());
        //jgen.writeNumberField("age", person.getAge());
        jgen.writeEndObject();
    }
}
