package com.example.ashi.irrigatedmanager.util;

import android.util.Log;

import com.example.ashi.irrigatedmanager.IrrigationScheduleInfo;
import com.example.ashi.irrigatedmanager.gson.Abnormal;
import com.example.ashi.irrigatedmanager.gson.HttpResult;
import com.example.ashi.irrigatedmanager.gson.InspectNote;
import com.example.ashi.irrigatedmanager.gson.PatrolManager;
import com.example.ashi.irrigatedmanager.gson.PatrolNote;
import com.example.ashi.irrigatedmanager.gson.ScanObject;
import com.example.ashi.irrigatedmanager.gson.TaskFlow;
import com.example.ashi.irrigatedmanager.gson.TotalCount;
import com.example.ashi.irrigatedmanager.gson.User;
import com.example.ashi.irrigatedmanager.level2_2_3.InspectDetailInfo;
import com.example.ashi.irrigatedmanager.level2_4.Rain;
import com.example.ashi.irrigatedmanager.level2_4.RainDetailData;
import com.example.ashi.irrigatedmanager.level2_4.SluiceInfo;
import com.example.ashi.irrigatedmanager.level2_5.ManualInspectItem;
import com.example.ashi.irrigatedmanager.level2_5.PatrolItem;
import com.example.ashi.irrigatedmanager.level2_6.ProjectInfo3;
import com.example.ashi.irrigatedmanager.level2_6.ProjectInfo4;
import com.example.ashi.irrigatedmanager.level5.Appval;
import com.example.ashi.irrigatedmanager.level5.AppvalHistory;
import com.example.ashi.irrigatedmanager.level5.BusinessForm;
import com.example.ashi.irrigatedmanager.level5.MyProcess;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ashi on 7/11/2018.
 */

public class Utility {

    public static String toDayString(int year, int month, int day) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(year);
        stringBuilder.append("-");
        if ( month < 10 ) {
            stringBuilder.append("0");
        }
        stringBuilder.append(month);
        stringBuilder.append("-");
        if ( day < 10 ) {
            stringBuilder.append("0");
        }
        stringBuilder.append(day);
        return stringBuilder.toString();
    }

    public static int getThisYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getThisMonth() {
        return Calendar.getInstance().get(Calendar.MONTH)+1;
    }

    /**
     * 取得当月天数
     * */
    public static int getCurrentMonthLastDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 得到指定月的天数
     * */
    public static int getMonthLastDay(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    public static String toURLEncoded(String paramString) {
        if (paramString == null || paramString.equals("")) {
            Log.d("toURLEncoded error:", paramString);
            return "";
        }

        try {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLEncoder.encode(str, "UTF-8");
            return str;
        } catch (Exception localException) {
            Log.e("toURLEncoded error:" + paramString, localException.getMessage());
        }

        return "";
    }

    public static ScanObject handleScanResponse(String response) {
        try {
            return new Gson().fromJson(response, ScanObject.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

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

    public static List<SluiceInfo> handleApi03SluiceMonitorListResponse(String response) {
        try {
            return new Gson().fromJson(response, new TypeToken<List<SluiceInfo>>(){}.getType());
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

    public static List<RainDetailData> handleApi06getRainListResponse(String response) {
        try {
            return new Gson().fromJson(response, new TypeToken<List<RainDetailData>>(){}.getType());
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

    public static ProjectInfo4 handleApi18projectDetailResponse(String response) {
        try {
            return new Gson().fromJson(response, ProjectInfo4.class);
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

    public static List<PatrolItem> handleApi21patrolItemResponse(String response) {
        try {
            return new Gson().fromJson(response, new TypeToken<List<PatrolItem>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<InspectDetailInfo> handleApi25officeStatisticResponse(String response) {
        try {
            return new Gson().fromJson(response, new TypeToken<List<InspectDetailInfo>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<ManualInspectItem> handleApi26patrolDesQueryResponse(String response) {
        try {
            return new Gson().fromJson(response, new TypeToken<List<ManualInspectItem>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<PatrolNote> handleApi28officeUserStatisticResponse(String response) {
        try {
            return new Gson().fromJson(response, new TypeToken<List<PatrolNote>>(){}.getType());
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

    public static List<Abnormal> handleApi30patrolInitResponse(String response) {
        try {
            return new Gson().fromJson(response, new TypeToken<List<Abnormal>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<PatrolManager> handleApi32getUserOfPatrolResponse(String response) {
        try {
            return new Gson().fromJson(response, new TypeToken<List<PatrolManager>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<TaskFlow> handleApi33taskFlowResponse(String response) {
        try {
            return new Gson().fromJson(response, new TypeToken<List<TaskFlow>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
