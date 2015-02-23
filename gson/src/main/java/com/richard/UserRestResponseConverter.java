package com.richard;

import com.google.gson.*;
import com.google.gson.annotations.JsonAdapter;
import com.richard.model.User;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rnkoaa on 2/22/15.
 */
public class UserRestResponseConverter implements JsonDeserializer<RestResponse<User>> {

    @Override
    public RestResponse<User> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        RestResponse<User> restResponse = new RestResponse<>();

        JsonObject respObj = json.getAsJsonObject().get("resp").getAsJsonObject();
        boolean status = respObj.get("s").getAsBoolean();

        int errorCode = 0;
        JsonElement errorElem = respObj.get("c");
        //for some reason, the error code is a json object if it does not exist.
        if (!errorElem.isJsonNull())
            errorCode = errorElem.getAsInt();
        String message = respObj.get("m").getAsString();
        String errorData = respObj.get("d").getAsString();
        RestResponse.CTMResponse ctmResponse = new RestResponse.CTMResponse(status, message, errorCode, errorData);

        restResponse.setResponse(ctmResponse);
        //response failed, so get all the error codes.
        if (!ctmResponse.isSuccessful()) {
            Map<Integer, String> errorMap = new HashMap<>();
            JsonArray errorsArray = json.getAsJsonObject().get("e").getAsJsonArray();
            errorsArray.forEach(error -> {
                int errorC = error.getAsJsonObject().get("c").getAsInt();
                String errorM = error.getAsJsonObject().get("m").getAsString();
                errorMap.put(errorC, errorM);
            });
            restResponse.setErrors(errorMap);

        }
        return restResponse;
    }
}
