package com.example.ashi.irrigatedmanager.util;

/**
 * Created by ashi on 8/26/2018.
 */

public class Api {

    public static String product_address                    = "http://221.193.192.143:8091/zfh_manager";
    public static String test_address                       = "http://www.boze-tech.com/zfh_manager";
    public static String address                             = product_address;

    // "http://www.boze-tech.com/zfh_manager/a/app/login/loginCheck?loginName=admin&passwd=123";
    public static String API_01_loginCheck                 = address + "/a/app/login/loginCheck?";
    public static String API_02_getFriends                 = address + "/a/app/login/getFriends?userId=" + Global.user.id;
    public static String API_03_getSluiceMonitorList     = address + "/a/app/login/getSluiceMonitorList?";
    public static String API_04_getWaterLevelMonitorList = address + "/a/app/login/getWaterLevelMonitorList?";
    public static String API_05_getRainMonitorList        = address + "/a/app/login/getRainMonitorList?";
    public static String API_06_getRainList                = address + "/a/app/login/getRainList?";
    public static String API_07_getVideoList               = address + "/a/app/login/getVideoList?userId="+ Global.user.id +"&projectType=DS-2CD5A26EFWD-IZ&name=柳林管理所-闸室（枪）";
    public static String API_08_                             = "";
    public static String API_09_getLogList                 = address + "/a/app/login/getLogList?userId=" + Global.user.id;
    public static String API_10_saveDeviceInfo            = address + "/a/app/login/saveDeviceInfo?userId="+ Global.user.id +"&banbenhao=DS-2CD5A26EFWD-IZ&yuyan=zh" +
            "&xinghao=x100&changshang=长虹&baiduPushUserId=11&baiduPushAppId=22&baiduChannelId=22";
    public static String API_11_qrLoginCheck              = address + "/a/app/login/qrLoginCheck?userId="+ Global.user.id +"&sid=1&pwd=2";
    public static String API_12_todoActList                = address + "/a/app/actTask/todoActList?";
    public static String API_13_historyActList             = address + "/a/app/actTask/historyActList?";
    public static String API_14_getMyProcess               = address + "/a/app/actTask/getMyProcess?";
    public static String API_15_handleProcess              = address + "/a/app/actTask/handleProcess?";
    public static String API_16_businessForm              = address + "/a/app/actTask/businessForm?";
    public static String API_17_projectList               = address + "/a/app/project/projectList?name=&office=";
    public static String API_18_projectDetail             = address + "/a/app/project/projectDetail?";
    public static String API_19_queryIrrigationSchedule = address + "/a/app/project/queryIrrigationSchedule?";
    public static String API_20_patrolResult              = address + "/a/app/patrol/patrolResult?userId="+ Global.user.id + "&day=2018-07-11&dayType=1&type=&resultType=&status=";
    public static String API_21_patrolItem                = address + "/a/app/patrol/patrolItem?";
    public static String API_22_patrolSave                = address + "/a/app/patrol/patrolSave?";
    public static String API_23_basicInfo                 = address + "/a/app/patrol/basicInfo?";
    public static String API_24_patrolDetail              = address + "/a/app/patrol/patrolDetail?id=30136878e084460cad141af485addea2";
    public static String API_25_officeStatistic           = address + "/a/app/patrol/officeStatistic?";
    public static String API_26_patrolDesQuery            = address + "/a/app/patrol/patrolDesQuery?";
    public static String API_27_patrolQueryUser           = address + "/a/app/patrol/patrolQueryUser?userName=系统管理员";
    public static String API_28_officeUserStatistic      = address + "/a/app/patrol/officeUserStatistic?";
    public static String API_29_queryTotalCount           = address + "/a/app/patrol/queryTotalCount?";
    public static String API_30_patrolInit                = address + "/a/app/patrol/patrolInit?type=";
    public static String API_31_queryYearCount            = address + "/a/app/patrol/queryYearCount?";
    public static String API_32_getUserOfPatrol           = address + "/a/app/patrol/getUserOfPatrol?";
    public static String API_33_taskFlow                   = address + "/a/app/actTask/taskFlow?";

}
