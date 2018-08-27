package com.example.ashi.irrigatedmanager.util;

/**
 * Created by ashi on 8/26/2018.
 */

public interface Api {

    // "http://www.boze-tech.com/zfh_manager/a/app/login/loginCheck?loginName=admin&passwd=123";
    String API_01_loginCheck                 = "http://www.boze-tech.com/zfh_manager/a/app/login/loginCheck?";
    String API_02_getFriends                 = "http://www.boze-tech.com/zfh_manager/a/app/login/getFriends?userId=1";
    String API_03_getSluiceMonitorList     = "http://www.boze-tech.com/zfh_manager/a/app/login/getSluiceMonitorList?userId=1&name=张庄桥分洪闸位";
    String API_04_getWaterLevelMonitorList = "http://www.boze-tech.com/zfh_manager/a/app/login/getWaterLevelMonitorList?userId=1&name=张庄桥分洪闸上游水位";
    String API_05_getRainMonitorList        = "http://www.boze-tech.com/zfh_manager/a/app/login/getRainMonitorList?userId=1";
    String API_06_getRainList                = "http://www.boze-tech.com/zfh_manager/a/app/login/getRainList?userId=1&name=柳林总雨量";
    String API_07_getVideoList               = "http://www.boze-tech.com/zfh_manager/a/app/login/getVideoList?userId=1&projectType=DS-2CD5A26EFWD-IZ&name=柳林管理所-闸室（枪）";
    String API_08_                             = "";
    String API_09_getLogList                 = "http://www.boze-tech.com/zfh_manager/a/app/login/getLogList?userId=1";
    String API_10_saveDeviceInfo            = "http://www.boze-tech.com/zfh_manager/a/app/login/saveDeviceInfo?userId=1&banbenhao=DS-2CD5A26EFWD-IZ&yuyan=zh" +
            "&xinghao=x100&changshang=长虹&baiduPushUserId=11&baiduPushAppId=22&baiduChannelId=22";
    String API_11_qrLoginCheck              = "http://www.boze-tech.com/zfh_manager/a/app/login/qrLoginCheck?userId=1&sid=1&pwd=2";
    String API_12_todoActList                = "http://www.boze-tech.com/zfh_manager/a/app/actTask/todoActList?userId=bbf042e126fc4201a761c2cb8da32475";
    String API_13_historyActList             = "http://www.boze-tech.com/zfh_manager/a/app/actTask/historyActList?userId=bbf042e126fc4201a761c2cb8da32475";
    String API_14_getMyProcess               = "http://www.boze-tech.com/zfh_manager/a/app/actTask/getMyProcess?userId=bbf042e126fc4201a761c2cb8da32475";
    String API_15_handleProcess              = "http://www.boze-tech.com/zfh_manager/a/app/actTask/handleProcess?taskName=异常处理&dealType=1&comment=很好" +
            "&taskId=8539953ad40a4e0ea8ad86f7f944d1a0&taskDefKey=audit2&procInsId=93acd047627d45edad2102a7cf00cc0e" +
            "&procDefId=patrol_result_deal_new:4:a1e5c2f5962a4db1820ca13aea26f57a&procDefKey=" +
            "&businessKey=pro_patrol_result_deal:98eaf7ee37354b48b875caf30bbad7a9&userId=xizha&flag=yes";
    String API_16_businessForm              = "http://www.boze-tech.com/zfh_manager/a/app/actTask/businessForm?businessKey=pro_patrol_result_deal:98eaf7ee37354b48b875caf30bbad7a9";
    String API_17_projectList               = "http://www.boze-tech.com/zfh_manager/a/app/project/projectList?userId=1&projectType=channel&name=&office=&subType=1";
    String API_18_projectDetail             = "http://www.boze-tech.com/zfh_manager/a/app/project/projectDetail?userId=1&projectType=channel&id=331d737641434a0bb476265b38d9db1c";
    String API_19_queryIrrigationSchedule = "http://www.boze-tech.com/zfh_manager/a/app/project/queryIrrigationSchedule?userId=1&year=2018&turn=1";
    String API_20_patrolResult              = "http://www.boze-tech.com/zfh_manager/a/app/patrol/patrolResult?userId=1&day=2018-07-11&dayType=1&type=&resultType=&status=";
    String API_21_patrolItem                = "http://www.boze-tech.com/zfh_manager/a/app/patrol/patrolItem?type=channel";
    String API_22_patrolSave                = "http://www.boze-tech.com/zfh_manager/a/app/patrol/patrolSave?type=channel&goalId=8502f69d32304ee6a9aacd99920fdcd7" +
            "&longitude=116.429489&latitude=39.87182&images=&itemResults=&createBy=1&contents=是是是&userId=1";
    String API_23_basicInfo                 = "http://www.boze-tech.com/zfh_manager/a/app/patrol/basicInfo?id=8502f69d32304ee6a9aacd99920fdcd7&type=channel&userId=1";
    String API_24_patrolDetail              = "http://www.boze-tech.com/zfh_manager/a/app/patrol/patrolDetail?id=30136878e084460cad141af485addea2";
    String API_25_officeStatistic           = "http://www.boze-tech.com/zfh_manager/a/app/patrol/officeStatistic?endDate=2018-07-12&startDate=2018-07-11&userId=1&dayType=&office=";
    String API_26_patrolDesQuery            = "http://www.boze-tech.com/zfh_manager/a/app/patrol/patrolDesQuery?userId=1&type";
    String API_27_patrolQueryUser           = "http://www.boze-tech.com/zfh_manager/a/app/patrol/patrolQueryUser?userName=系统管理员";
    String API_28_officeUserStatistic       = "http://www.boze-tech.com/zfh_manager/a/app/patrol/officeUserStatistic?userId=1&office=06b21ce1eaec48e59e2a40025b0991ce" +
            "&&projectType=channel&name=%E6%BB%8F%E9%98%B3%E6%B2%B3%E7%A3%81%E5%8E%BF%E6%AE%B5&startDate=2018-05-16&endDate=2018-05-19";
    String API_29_queryTotalCount           = "http://www.boze-tech.com/zfh_manager/a/app/patrol/queryTotalCount?userId=1";
    String API_30_patrolInit                = "http://www.boze-tech.com/zfh_manager/a/app/patrol/patrolInit?type=";
    String API_31_queryYearCount            = "http://www.boze-tech.com//zfh_manager/a/app/patrol/queryYearCount?userId=1&month=05";
}
