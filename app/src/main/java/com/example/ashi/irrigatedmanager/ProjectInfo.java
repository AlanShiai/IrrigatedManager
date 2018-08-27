package com.example.ashi.irrigatedmanager;

/**
 * Created by ashi on 7/9/2018.
 */

public class ProjectInfo {

    private String name;
    private String type;

    public ProjectInfo(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }
}
