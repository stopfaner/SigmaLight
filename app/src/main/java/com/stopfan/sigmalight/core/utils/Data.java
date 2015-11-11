package com.stopfan.sigmalight.core.utils;

import android.content.Context;
import android.preference.PreferenceManager;

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

    public interface Constants {
        String AUTHORIZED = "authorized";


    }

}
