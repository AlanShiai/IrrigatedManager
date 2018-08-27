package com.example.ashi.irrigatedmanager.level2_2_3;

/**
 * Created by AShi on 7/20/2018.
 */

public class InspectDetailInfo {
    //{"total":5,"id":"33bd93b576b34efc8198a10f5f75b441","abnormalTotal":0,"total1":1,"name":"成安管理所","type":"user"},

    public int total;
    public String id;
    public int abnormalTotal;
    public String total1;
    public String name;
    public String type;

    public InspectDetailInfo(String name, String num) {
        this.name = name;
    }
}
