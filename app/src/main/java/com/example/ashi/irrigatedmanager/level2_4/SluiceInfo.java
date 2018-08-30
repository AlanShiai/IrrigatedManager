package com.example.ashi.irrigatedmanager.level2_4;

/**
 * Created by ashi on 7/13/2018.
 */

public class SluiceInfo {
    // [{"project_name":"张庄桥分洪闸位","level9":"0.00","level8":"0.00","level7":"0.00",
    // "project_type":"sluice","level6":"0.00","level10":"0.00","level5":"0.00","level4":"0.02","id":"ad407c9bd9634d0bac800651287a8c1f","level2":"0.01",
    // "level3":"0.00","time":"2018-08-27 17:24:51","level1":"0.01","project_id":null,"hole":"5"}]

    // update for waterData
    // [{"project_name":"张庄桥分洪闸位","level9":"0.00","level8":"0.00","level7":"0.00","project_type":"sluice",
    // "level6":"0.00","level10":"0.00","level5":"0.00","level4":"0.02","id":"ad407c9bd9634d0bac800651287a8c1f",
    // "level2":"0.01","level3":"0.00","time":"2018-08-30 15:38:51","level1":"0.01","project_id":null,
    // "waterData":"闸前水位:4.39毫米 闸后水位:0.16毫米 ","hole":"5"}]


    public String project_name;
    public String level9 = "";
    public String level8 = "";
    public String level7 = "";
    public String project_type;
    public String level6 = "";
    public String level10 = "";
    public String level5 = "";
    public String level4 = "";
    public String id;
    public String level2 = "";
    public String level3 = "";
    public String time = "2018-06-11 10:44:46 ";
    public String level1 = "";
    public String project_id;
    public String waterData;
    public String hole;

    public SluiceInfo(String name) { this.project_name = name; }

    public void setName(String name) {
        this.project_name = name;
    }

    public String getName() {
        return project_name;
    }
}
