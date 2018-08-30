package com.example.ashi.irrigatedmanager.level2_4;

import java.util.List;
import java.util.Map;

/**
 * Created by AShi on 7/22/2018.
 */

public class Rain {
    // {"id":"f84dbdcf634048169c2b1e8c06043269","time":"2018-08-26 16:48:41","project_name":"长孙无极",
    // "dataDay":[],"project_id":"xm98","dataMonth":null,"project_type":"xm09182981"}

    public String id;
    public String time;
    public String project_name;
    public List<RainData> dataDay; // day_rain
    public String project_id;
    public RainData dataMonth; // month_rain
    public String project_type;

    public Rain(String name) {
        this.project_name = name;
    }
}

class RainData {
    public float rainData;
    public String projectName;
}
