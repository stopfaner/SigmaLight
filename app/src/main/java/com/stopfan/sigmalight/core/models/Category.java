package com.stopfan.sigmalight.core.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Denys on 11/11/2015.
 */
public class Category {

    @SerializedName("id")
    public long id;

    @SerializedName("category")
    public String name;

    @SerializedName("parent_id")
    public long parentId;

    public Category() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
}
