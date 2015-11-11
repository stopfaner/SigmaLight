package com.stopfan.sigmalight.core.net;

import com.stopfan.sigmalight.core.models.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Denys on 11/8/2015.
 */
public class RequestUser {

    private String action;

    private User user;

    public RequestUser(String action, User user) {
        this.action = action;
        this.user = user;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        JSONObject object = new JSONObject();
        try {
            object.put("action", action);
            JSONObject userObject = new JSONObject();
            userObject.put("email", user.getEmail());
            userObject.put("password", user.getPassword());
            object.put("data", userObject);

            return object.toString();
        } catch (JSONException ex) {
            return "";
        }
    }

}
