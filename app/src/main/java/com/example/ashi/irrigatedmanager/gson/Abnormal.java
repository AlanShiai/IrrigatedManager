package com.example.ashi.irrigatedmanager.gson;

/**
 * Created by ashi on 8/28/2018.
 */

public class Abnormal {

    // {"monthAbnormalNumber":0,"yearAbnormalNumber":0,"projectLabel":"倒虹吸","yearNumber":0,"monthNumber":0}

    public String monthAbnormalNumber = "0";
    public String yearAbnormalNumber = "0";
    public String projectLabel = "";
    public String yearNumber = "0";
    public String monthNumber = "0";

    public Abnormal (String name, String yearNumber, String yearAbnormalNumber) {
        this.projectLabel = name;
        this.yearNumber = yearNumber;
        this.yearAbnormalNumber = yearAbnormalNumber;
    }
}
