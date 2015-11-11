package com.stopfan.sigmalight.core.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Denys on 11/8/2015.
 */
public class SubCategory {

    @SerializedName("id")
    public Long id;

    @SerializedName("sub_id")
    public Long subId;

    @SerializedName("parent_id")
    public Long parentId;

    @SerializedName("image")
    public String image;

    @SerializedName("description")
    public String description;

    @SerializedName("title")
    public String title;

    @SerializedName("address")
    public String address;

    public SubCategory() {

    }

    public SubCategory(Long subId) {
        this.subId = subId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
