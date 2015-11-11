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




}
