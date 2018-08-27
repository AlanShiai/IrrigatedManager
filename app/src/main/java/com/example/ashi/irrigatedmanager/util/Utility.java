package com.example.ashi.irrigatedmanager.util;

import com.example.ashi.irrigatedmanager.IrrigationScheduleInfo;
import com.example.ashi.irrigatedmanager.gson.HttpResult;
import com.example.ashi.irrigatedmanager.gson.InspectNote;
import com.example.ashi.irrigatedmanager.gson.TotalCount;
import com.example.ashi.irrigatedmanager.gson.User;
import com.example.ashi.irrigatedmanager.level2_4.Rain;
import com.example.ashi.irrigatedmanager.level2_6.ProjectInfo3;
import com.example.ashi.irrigatedmanager.level5.Appval;
import com.example.ashi.irrigatedmanager.level5.AppvalHistory;
import com.example.ashi.irrigatedmanager.level5.BusinessForm;
import com.example.ashi.irrigatedmanager.level5.MyProcess;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ashi on 7/11/2018.
 */

public class Utility {

    public static HttpResult handleNormalFormResponse(String response) {
        try {
            return new Gson().fromJson(response, HttpResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static User handleApi01LoginResponse(String response) {
        try {
            return new Gson().fromJson(response, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Rain> handleApi05RainMonitorListResponse(String response) {
        try {
            return new Gson().fromJson(response, new TypeToken<List<Rain>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Appval> handleApi12TodoActListResponse(String response) {
        try {
            return new Gson().fromJson(response, new TypeToken<List<Appval>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static AppvalHistory handleApi13HisoryActListResponse(String response) {
        try {
            return new Gson().fromJson(response, AppvalHistory.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static MyProcess handleApi14getMyProcessResponse(String response) {
        try {
            return new Gson().fromJson(response, MyProcess.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BusinessForm handleApi16businessFormResponse(String response) {
        try {
            return new Gson().fromJson(response, BusinessForm.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<ProjectInfo3> handleApi17ProjectListResponse(String response) {
        try {
            return new Gson().fromJson(response, new TypeToken<List<ProjectInfo3>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<IrrigationScheduleInfo> handleApi19queryIrrigationScheduleResponse(String response) {
        try {
            return new Gson().fromJson(response, new TypeToken<List<IrrigationScheduleInfo>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<InspectNote> handleApi20patrolResultResponse(String response) {
        try {
            return new Gson().fromJson(response, new TypeToken<List<InspectNote>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static TotalCount handleApi29TotalCountResponse(String response) {
        try {
            return new Gson().fromJson(response, TotalCount.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
