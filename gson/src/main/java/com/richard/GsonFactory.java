package com.richard;

/**
 * Created by rnkoaa on 2/22/15.
 */

import com.google.gson.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.lang.reflect.Type;

/**
 * User: rnkoaa
 * Created: 2/19/15 10:05 PM
 */
public class GsonFactory {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZZ");

    private GsonFactory() {
    }

    public static Gson create() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeDeserializer());
        gsonBuilder.registerTypeAdapter(RestResponse.class, new UserRestResponseConverter());
        //gsonBuilder.registerTypeAdapter(User.class, new UserGsonConverter());
        return gsonBuilder.create();
    }

    public static class DateTimeDeserializer implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {
        // No need for an InstanceCreator since DateTime provides a no-args constructor
        @Override
        public JsonElement serialize(DateTime src, Type srcType, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }

        @Override
        public DateTime deserialize(JsonElement json, Type type, JsonDeserializationContext context)
                throws JsonParseException {
            return new DateTime(json.getAsString());
        }
    }
}