package com.richard;

import com.google.gson.*;
import com.richard.model.User;

import java.lang.reflect.Type;

/**
 * Created by rnkoaa on 2/22/15.
 */
public class UserGsonConverter implements JsonSerializer<User>, JsonDeserializer<User> {
    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        return null;
    }

    @Override
    public JsonElement serialize(User src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject userJsonObject = new JsonObject();
        userJsonObject.addProperty("email", src.getPersonalEmail());
        userJsonObject.addProperty("firstName", src.getFirstName());
        userJsonObject.addProperty("lastName", src.getLastName());
        userJsonObject.addProperty("zip", src.getPersonalZip());
        userJsonObject.addProperty("password", src.getPassword());
        userJsonObject.addProperty("confirmPassword", src.getPassword());
        userJsonObject.addProperty("confirmPassword", src.getPassword());
        userJsonObject.addProperty("confirmPassword", src.getPassword());
        return userJsonObject;
    }
}
