package com.stopfan.sigmalight.core.utils;

import android.content.Context;
import android.preference.PreferenceManager;

import com.stopfan.sigmalight.core.models.User;

/**
 * Created by Denys on 11/11/2015.
 */
public class Data {

    public static void setAuthorized(Context context) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putBoolean(Constants.AUTHORIZED, true).commit();
    }

    public static void setNotAuthorized(Context context) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putBoolean(Constants.AUTHORIZED, false).commit();
    }

    public static boolean isAuthorized(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(Constants.AUTHORIZED, false);
    }

    public static void saveUserData(Context context, User user) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(Constants.UserData.USER_NAME, user.getName());
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(Constants.UserData.USER_SURNAME, user.getSurname());
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(Constants.UserData.USER_GENDER, user.getGender());
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(Constants.UserData.USER_PHONE, user.getPhone());
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(Constants.UserData.USER_EMAIL, user.getEmail());
    }

    public static User getUserFromPrefs(Context context) {
        User user = new User();

        user.setName(PreferenceManager.getDefaultSharedPreferences(context)
                .getString(Constants.UserData.USER_NAME, ""));
        user.setSurname(PreferenceManager.getDefaultSharedPreferences(context)
                .getString(Constants.UserData.USER_SURNAME, ""));
        user.setGender(PreferenceManager.getDefaultSharedPreferences(context)
                .getString(Constants.UserData.USER_GENDER, ""));
        user.setPhone(PreferenceManager.getDefaultSharedPreferences(context)
                .getString(Constants.UserData.USER_PHONE, ""));
        user.setEmail(PreferenceManager.getDefaultSharedPreferences(context)
                .getString(Constants.UserData.USER_EMAIL, ""));

        return user;
    }

    public interface Constants {
        String AUTHORIZED = "authorized";

        interface UserData {
            String USER_NAME = "user_name";
            String USER_SURNAME = "user_surname";
            String USER_GENDER = "user_gender";
            String USER_PHONE = "user_phone";
            String USER_EMAIL = "user_email";
        }

    }

}
