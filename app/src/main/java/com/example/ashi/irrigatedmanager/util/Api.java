package com.example.ashi.irrigatedmanager.util;

/**
 * Created by ashi on 8/26/2018.
 */

public interface Api {

    // "http://www.boze-tech.com/zfh_manager/a/app/login/loginCheck?loginName=admin&passwd=123";
    String API_01_loginCheck                 = "http://www.boze-tech.com/zfh_manager/a/app/login/loginCheck?";
    String API_02_getFriends                 = "http://www.boze-tech.com/zfh_manager/a/app/login/getFriends?userId=" + Global.userId;
    String API_03_getSluiceMonitorList     = "http://www.boze-tech.com/zfh_manager/a/app/login/getSluiceMonitorList?userId="  + Global.userId;
    String API_04_getWaterLevelMonitorList = "http://www.boze-tech.com/zfh_manager/a/app/login/getWaterLevelMonitorList?userId=" + Global.userId +"&name=张庄桥分洪闸上游水位";
    String API_05_getRainMonitorList        = "http://www.boze-tech.com/zfh_manager/a/app/login/getRainMonitorList?userId=" + Global.userId;
    String API_06_getRainList                = "http://www.boze-tech.com/zfh_manager/a/app/login/getRainList?userId=" + Global.userId ;
    String API_07_getVideoList               = "http://www.boze-tech.com/zfh_manager/a/app/login/getVideoList?userId="+ Global.userId+"&projectType=DS-2CD5A26EFWD-IZ&name=柳林管理所-闸室（枪）";
    String API_08_                             = "";
    String API_09_getLogList                 = "http://www.boze-tech.com/zfh_manager/a/app/login/getLogList?userId=" + Global.userId;
    String API_10_saveDeviceInfo            = "http://www.boze-tech.com/zfh_manager/a/app/login/saveDeviceInfo?userId="+ Global.userId +"&banbenhao=DS-2CD5A26EFWD-IZ&yuyan=zh" +
            "&xinghao=x100&changshang=长虹&baiduPushUserId=11&baiduPushAppId=22&baiduChannelId=22";
    String API_11_qrLoginCheck              = "http://www.boze-tech.com/zfh_manager/a/app/login/qrLoginCheck?userId="+ Global.userId +"&sid=1&pwd=2";
    String API_12_todoActList                = "http://www.boze-tech.com/zfh_manager/a/app/actTask/todoActList?userId=bbf042e126fc4201a761c2cb8da32475";
    String API_13_historyActList             = "http://www.boze-tech.com/zfh_manager/a/app/actTask/historyActList?userId=bbf042e126fc4201a761c2cb8da32475";
    String API_14_getMyProcess               = "http://www.boze-tech.com/zfh_manager/a/app/actTask/getMyProcess?userId=bbf042e126fc4201a761c2cb8da32475";
    String API_15_handleProcess              = "http://www.boze-tech.com/zfh_manager/a/app/actTask/handleProcess?taskName=异常处理&dealType=1&comment=很好" +
            "&taskId=8539953ad40a4e0ea8ad86f7f944d1a0&taskDefKey=audit2&procInsId=93acd047627d45edad2102a7cf00cc0e" +
            "&procDefId=patrol_result_deal_new:4:a1e5c2f5962a4db1820ca13aea26f57a&procDefKey=" +
            "&businessKey=pro_patrol_result_deal:98eaf7ee37354b48b875caf30bbad7a9&userId=xizha&flag=yes";
    String API_16_businessForm              = "http://www.boze-tech.com/zfh_manager/a/app/actTask/businessForm?businessKey=pro_patrol_result_deal:98eaf7ee37354b48b875caf30bbad7a9";
    String API_17_projectList               = "http://www.boze-tech.com/zfh_manager/a/app/project/projectList?userId="+ Global.userId +"&name=&office=";
    String API_18_projectDetail             = "http://www.boze-tech.com/zfh_manager/a/app/project/projectDetail?userId=" + Global.userId;
    String API_19_queryIrrigationSchedule = "http://www.boze-tech.com/zfh_manager/a/app/project/queryIrrigationSchedule?userId="+ Global.userId + "&year=2018";
    String API_20_patrolResult              = "http://www.boze-tech.com/zfh_manager/a/app/patrol/patrolResult?userId="+ Global.userId+"&day=2018-07-11&dayType=1&type=&resultType=&status=";
    String API_21_patrolItem                = "http://www.boze-tech.com/zfh_manager/a/app/patrol/patrolItem?";
    String API_22_patrolSave                = "http://www.boze-tech.com/zfh_manager/a/app/patrol/patrolSave?userId=" + Global.userId + "&images=&createBy=1";
    String API_23_basicInfo                 = "http://www.boze-tech.com/zfh_manager/a/app/patrol/basicInfo?userId=" + Global.userId;
    String API_24_patrolDetail              = "http://www.boze-tech.com/zfh_manager/a/app/patrol/patrolDetail?id=30136878e084460cad141af485addea2";
    String API_25_officeStatistic           = "http://www.boze-tech.com/zfh_manager/a/app/patrol/officeStatistic?userId=" + Global.userId + "&office=8eff2c16d5cf45fca84ac984190b0890";
    String API_26_patrolDesQuery            = "http://www.boze-tech.com/zfh_manager/a/app/patrol/patrolDesQuery?userId="+ Global.userId+"&type";
    String API_27_patrolQueryUser           = "http://www.boze-tech.com/zfh_manager/a/app/patrol/patrolQueryUser?userName=系统管理员";
    String API_28_officeUserStatistic       = "http://www.boze-tech.com/zfh_manager/a/app/patrol/officeUserStatistic?userId="+ Global.userId + "&office=06b21ce1eaec48e59e2a40025b0991ce";
    String API_29_queryTotalCount           = "http://www.boze-tech.com/zfh_manager/a/app/patrol/queryTotalCount?userId="+ Global.userId;
    String API_30_patrolInit                = "http://www.boze-tech.com/zfh_manager/a/app/patrol/patrolInit?type=";
    String API_31_queryYearCount            = "http://www.boze-tech.com//zfh_manager/a/app/patrol/queryYearCount?userId="+ Global.userId+"&month=05";
}
