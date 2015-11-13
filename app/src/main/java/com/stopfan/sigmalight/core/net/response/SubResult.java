package com.stopfan.sigmalight.core.net.response;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;
import com.stopfan.sigmalight.core.models.SubCategory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denys on 11/11/2015.
 */
public class SubResult {

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String info;

    @SerializedName("data")
    public List<SubCategory> data;

    public SubResult(String status) {
        this.status = status;
    }

    public SubResult(String status, String info) {
        this.status = status;
        this.info = info;
    }

    public SubResult(String status, List<SubCategory> data) {
        this.status = status;
        this.data = data;
    }

    public static class ResultDeserializer implements JsonDeserializer<SubResult> {
        @Override
        public SubResult deserialize(JsonElement json, Type typeOfT,
                                         JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject thisObj = json.getAsJsonObject();
            JsonElement result = thisObj.get("status");

            if (result != null) {
                String strResult = result.getAsString();
                if ("error".equals(strResult)) {
                    JsonElement info = thisObj.get("message");
                    if (info.isJsonPrimitive())
                        return new SubResult(strResult, info.getAsString());
                } else {
                    JsonElement data = thisObj.get("data");
                    if (data.isJsonArray()) {
                        JsonArray array = data.getAsJsonArray();
                        ArrayList<SubCategory> subCategories = new ArrayList<>();
                        for (JsonElement object : array) {
                            JsonObject obj = object.getAsJsonObject();
                            SubCategory subCategory = new SubCategory();
                            subCategory.setId(obj.get("id").getAsLong());
                            subCategory.setTitle(obj.get("title").getAsString());
                            subCategory.setDescription(obj.get("description").getAsString());
                            subCategory.setAddress(obj.get("address").getAsString());
                            subCategory.setImage(obj.get("image").getAsString());
                            subCategories.add(subCategory);
                        }
                        return new SubResult(strResult, subCategories);
                    }
                }
            }

            return null;
        }
    }

}
