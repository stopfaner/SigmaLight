package com.stopfan.sigmalight.core.models;

import com.google.gson.annotations.SerializedName;

/**
 * Class represents model of User
 * @author Denys Lytvinyuk
 * Made with love
 */
public class User {

    @SerializedName("id")
    public long id;

    @SerializedName("name")
    public String name;

    @SerializedName("surname")
    public String surname;

    @SerializedName("gender")
    public String gender;

    @SerializedName("phone")
    public String phone;

    @SerializedName("email")
    public String email;

    @SerializedName("photo")
    public String photo;

    @SerializedName("password")
    public String password;

    public static User createLoginInstance(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return user;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
