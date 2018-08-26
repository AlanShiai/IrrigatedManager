package com.example.ashi.irrigatedmanager;

/**
 * Created by ashi on 7/9/2018.
 */

public class IrrigationScheduleInfo {
    public String id;
    public String turn;
    public String areaName;
    public String totalArea;
    public String year;
    public String irrigationArea;

    public IrrigationScheduleInfo(String name) {
        this.areaName = name;
    }

}
