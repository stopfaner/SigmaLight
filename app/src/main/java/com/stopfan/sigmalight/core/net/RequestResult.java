package com.stopfan.sigmalight.core.net;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;
import com.stopfan.sigmalight.core.models.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Denys on 11/8/2015.
 */
public class RequestResult {

    @SerializedName("status")
    public String status;

    @SerializedName("data")
    public String info;

    @SerializedName("data")
    public User data;

    public RequestResult(String status) {
        this.status = status;
    }

    public RequestResult(String status, String info) {
        this.status = status;
        this.info = info;
    }

    public RequestResult(String status, User data) {
        this.status = status;
        this.data = data;
    }


    public static class ResultDeserializer implements JsonDeserializer<RequestResult> {
        @Override
        public RequestResult deserialize(JsonElement json, Type typeOfT,
                                         JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject thisObj = json.getAsJsonObject();
            JsonElement result = thisObj.get("status");

            if (result != null) {
                String strResult = result.getAsString();
                if ("error".equals(strResult)) {
                    JsonElement info = thisObj.get("message");
                    if (info.isJsonPrimitive())
                        return new RequestResult(strResult, info.getAsString());

                } else if ("success".equals(strResult)) {
                    JsonElement data = thisObj.get("data");
                    if (data.isJsonObject()) {
                        JsonObject object = data.getAsJsonObject();
                        RequestResult requestResult;
                        if (object.has("gender")) {
                            final User user = new User();
                            user.setId(object.get("id").getAsLong());
                            user.setName(object.get("name").getAsString());
                            user.setSurname(object.get("surname").getAsString());
                            user.setGender(object.get("gender").getAsString());
                            user.setPhone(object.get("phone").getAsString());
                            user.setEmail(object.get("email").getAsString());
                            user.setPhoto(object.get("photo").getAsString());

                            requestResult = new RequestResult(strResult, user);
                            return requestResult;
                        }

                    }
                }


            }

            return null;
        }
    }

}
