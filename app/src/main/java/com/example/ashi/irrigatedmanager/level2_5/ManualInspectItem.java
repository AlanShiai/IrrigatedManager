package com.example.ashi.irrigatedmanager.level2_5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ManualInspectItem {

    private String name;

    public ManualInspectItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
