package com.example.ashi.irrigatedmanager;

/**
 * Created by ashi on 7/9/2018.
 */

public class IrrigationScheduleInfo {
    private String name;
    private String schedule;

    public IrrigationScheduleInfo(String name, String schedule) {
        this.name = name;
        this.schedule = schedule;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getName() {
        return name;
    }

    public String getSchedule() {
        return schedule;
    }
}
