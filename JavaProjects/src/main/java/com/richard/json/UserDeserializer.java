package com.richard.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.richard.Person;
import com.richard.User;

import java.io.IOException;

/**
 * Created by rnkoaa on 8/31/14.
 */
public class UserDeserializer extends JsonDeserializer<User> {
    @Override
    public User deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);
        String id = node.get("id").asText();
        String userName = node.get("username").asText();

        JsonNode personNode = node.get("person");
        String firstName = personNode.get("firstName").asText();
        String lastName = personNode.get("lastName").asText();
        int age = personNode.get("age").asInt();

        return new User(id, userName, null, new Person(firstName, lastName, age));
    }
}
