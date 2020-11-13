package com.breman;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.UUID;

public class UserIdDeserializer extends JsonDeserializer<UserId> {
    @Override
    public UserId deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        String value = node.asText("");

        return new UserId(UUID.fromString(value));

//        return OrderKey.get( node.asText() );
    }
}
