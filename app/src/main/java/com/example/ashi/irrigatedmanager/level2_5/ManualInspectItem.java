package com.example.ashi.irrigatedmanager.level2_5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ManualInspectItem {
    // {"id":"197738a564ab4773a445c7179f65f0c9","goalId":"4b4e6c69342e4842ae00459e60b24171","type":"sluice","goalName":"北关闸"}

    public String id;
    public String goalId;
    public String type;
    public String goalName;

    public ManualInspectItem(String name) {
        this.goalName = name;
    }

}
