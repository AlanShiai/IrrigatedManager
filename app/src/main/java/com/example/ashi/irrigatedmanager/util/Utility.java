package com.example.ashi.irrigatedmanager.util;

import com.example.ashi.irrigatedmanager.gson.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ashi on 7/11/2018.
 */

public class Utility {

    public static User handleLoginResponse(String response) {
        try {
            return new Gson().fromJson(response, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
