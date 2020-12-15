package com.richard.eventsourcing;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class UserIdSerializer extends JsonSerializer<UserId> {
  @Override
  public void serialize(UserId userId, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    if (userId.value() == null) {
      gen.writeString("");
      return;
    }
    gen.writeString(userId.value().toString());
  }
}
