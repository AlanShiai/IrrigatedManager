package com.example.ashi.irrigatedmanager.gson;

/**
 * Created by ashi on 9/3/2018.
 */

public class TaskFlow {
    // {"startTime":"2018-07-05 10:23:42","entTime":"2018-07-05 10:23:42","assigneeName":"西闸管理所所长",
    // "comment":"","activityName":"巡检异常申请","durationTime":""}

    public TaskFlow(String name) {
        this.assigneeName = name;
    }

    public String startTime = "";
    public String entTime = "";
    public String assigneeName ="";
    public String comment = "";
    public String activityName = "";
}
