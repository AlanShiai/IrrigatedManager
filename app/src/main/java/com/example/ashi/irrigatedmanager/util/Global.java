package com.example.ashi.irrigatedmanager.util;

import com.example.ashi.irrigatedmanager.gson.Abnormal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ashi on 8/27/2018.
 */

public class Global {

    public static boolean isExceptionFound = false;

    public static String projectInfoType = "channelHead";
    public static String projectId = "331d737641434a0bb476265b38d9db1c";
    public static String projectInfoSubtype = "1";
    public static HashMap<String,String> projectDetails = new HashMap<>();

    public static List<Abnormal> abnormalList = new ArrayList<>();
}
