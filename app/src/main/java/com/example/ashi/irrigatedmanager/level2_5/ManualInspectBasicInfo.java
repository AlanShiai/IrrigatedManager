package com.example.ashi.irrigatedmanager.level2_5;

import java.util.HashMap;

/**
 * Created by AShi on 7/23/2018.
 */

public class ManualInspectBasicInfo {

    HashMap<String,String> basicInfo = new HashMap<String, String>();

    public static HashMap<String,String> getInfo() {
        ManualInspectBasicInfo info = new ManualInspectBasicInfo();
        info.basicInfo.put("名称","民有一干渠成安段");
        info.basicInfo.put("桩号","27+662-39+320");
        info.basicInfo.put("建设年代","1967-04-01 00:00:00");
        info.basicInfo.put("所属渠道","民有一干渠");
        info.basicInfo.put("管理单位","成安管理所");
        info.basicInfo.put("收益单位","成安县");
        info.basicInfo.put("设计流量","50.00");
        info.basicInfo.put("量水类型","流速仪器水");
        info.basicInfo.put("切面形状","梯形");
        info.basicInfo.put("衬砌形式","预制混凝土板");
        info.basicInfo.put("渠底宽度","11-9");
        info.basicInfo.put("渠顶宽度","11.1");
        info.basicInfo.put("高度","4");
        info.basicInfo.put("尺寸","11.658");
        info.basicInfo.put("泵站补水","");
        info.basicInfo.put("起点经度","114.679510");
        info.basicInfo.put("起点伟度","36.362112");
        info.basicInfo.put("终点经度","124.679510");
        info.basicInfo.put("终点伟度","38.362112");
        return info.basicInfo;
    }
}
