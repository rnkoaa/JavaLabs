package com.breman;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class UserIdSerializer extends JsonSerializer<UserId> {
    public UserIdSerializer() {
    }

    @Override
    public void serialize(UserId value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value.value() == null) {
            gen.writeString("");
            return;
        }
        gen.writeString(value.value().toString());

    }
}
