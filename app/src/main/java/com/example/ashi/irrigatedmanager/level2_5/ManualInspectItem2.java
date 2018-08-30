package com.example.ashi.irrigatedmanager.level2_5;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AShi on 7/23/2018.
 */

public class ManualInspectItem2 {

    public String name;

    private List<String> items = new ArrayList<>();

    public ManualInspectItem2(String name) {
        this.name = name;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
