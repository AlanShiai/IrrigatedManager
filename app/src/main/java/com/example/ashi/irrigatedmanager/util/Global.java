package com.example.ashi.irrigatedmanager.util;

import com.example.ashi.irrigatedmanager.gson.Abnormal;
import com.example.ashi.irrigatedmanager.gson.User;
import com.example.ashi.irrigatedmanager.level5.Appval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ashi on 8/27/2018.
 */

public class Global {

    public static List<Integer> colors = Arrays.asList(0xFFFE618E, 0xFF9ADC78, 0xFF94AEE7, 0xFF77DAF7, 0xFFFE998F);

    public static User user = new User("1");

    public static boolean isExceptionFound = false;
    public static String exceptionMsg = "";

    public static String projectInfoType = "channelHead";
    public static String projectId = "331d737641434a0bb476265b38d9db1c";
    public static String projectInfoSubtype = "1";
    public static HashMap<String,String> projectDetails = new HashMap<>();

    // patrol
    public static String patrolType = "channel";
    public static String patrolId = "62c59e22820b41e094fa17788df11b66";
    public static HashMap<String,String> patrolDetails = new HashMap<>();

    public static List<Abnormal> abnormalList = new ArrayList<>();

    public static String rain_name = "柳林总雨量";

    public static int irrigation_turn = 1;

    // appval
    public static boolean lastPageIsTodo = false;
    public static String businessKey = "pro_patrol_result_deal:98eaf7ee37354b48b875caf30bbad7a9";
    public static String processInstanceId = "93acd047627d45edad2102a7cf00cc0e";

    // handleProcess
    public static Appval appval;
    public static int appvalSelector = 0;

    // inspectDetails
    public static String inspectDetails_projectType = "";
    public static int inspectDetails_month = 1;
    public static String inspectDetails_officeId = "8eff2c16d5cf45fca84ac984190b0890";

    // patrol_note
    public static int start_year = Utility.getThisYear(), start_month = Utility.getThisMonth(), start_day = 1;
    public static int end_year = Utility.getThisYear(), end_month = Utility.getThisMonth(), end_day = Utility.getCurrentMonthLastDay();;
    public static int typeSelector = 0;
    public static String search = "";
}
