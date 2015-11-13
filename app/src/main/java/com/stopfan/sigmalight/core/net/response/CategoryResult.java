package com.stopfan.sigmalight.core.net.response;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;
import com.stopfan.sigmalight.core.models.Category;
import com.stopfan.sigmalight.core.models.SubCategory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by denys on 12.11.15.
 */
public class CategoryResult {

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String info;

    @SerializedName("data")
    public List<Category> data;

    public CategoryResult(String status) {
        this.status = status;
    }

    public CategoryResult(String status, String info) {
        this.status = status;
        this.info = info;
    }

    public CategoryResult(String status, List<Category> data) {
        this.status = status;
        this.data = data;
    }

    public static class ResultDeserializer implements JsonDeserializer<CategoryResult> {
        @Override
        public CategoryResult deserialize(JsonElement json, Type typeOfT,
                                     JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject thisObj = json.getAsJsonObject();
            JsonElement result = thisObj.get("status");

            if (result != null) {
                String strResult = result.getAsString();
                if ("error".equals(strResult)) {
                    JsonElement info = thisObj.get("message");
                    if (info.isJsonPrimitive())
                        return new CategoryResult(strResult, info.getAsString());
                } else {
                    JsonElement data = thisObj.get("data");
                    if (data.isJsonArray()) {
                        JsonArray array = data.getAsJsonArray();
                        ArrayList<Category> categories = new ArrayList<>();
                        JsonElement element = array.get(1);
                        if (element.isJsonArray()) {
                            JsonArray element1 = element.getAsJsonArray();
                            for (JsonElement el : element1) {
                                JsonObject object = el.getAsJsonObject();
                                Category category = new Category();
                                category.setId(object.get("id").getAsLong());
                                category.setName(object.get("category").getAsString());
                                category.setParentId(object.get("parent_id").getAsLong());
                                categories.add(category);
                            }
                            return new CategoryResult(strResult, categories);
                        }
                    }
                }
            }

            return null;
        }
    }
}
