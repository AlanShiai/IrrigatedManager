package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.ashi.irrigatedmanager.level2_4.SluiceInfo;
import com.example.ashi.irrigatedmanager.level2_4.SluiceInfoAdapter;

import java.util.ArrayList;
import java.util.List;

public class Level2_4_realtimeMonitor2 extends AppCompatActivity {

    private List<SluiceInfo> projectInfoList = new ArrayList<SluiceInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_4_realtime_monitor2);

        initProjectInfoList();

        ListView listView = (ListView) findViewById(R.id.level_2_4_1_sluice_list);
        SluiceInfoAdapter adapter = new SluiceInfoAdapter(Level2_4_realtimeMonitor2.this, R.layout.sluice_item1, projectInfoList);
        listView.setAdapter(adapter);

        addListernerForBottomToolbar();
    }

    private void initProjectInfoList() {
        String[] strings = {
                "赵寨主干西闸位" ,
                "赵寨十九支东闸位" ,
                "王林主干闸位" ,
                "豆公主干闸位" ,
        };
        for (String name : strings) {
            projectInfoList.add(new SluiceInfo(name));
        }
        for (String name : strings) {
            projectInfoList.add(new SluiceInfo(name));
        }
        for (String name : strings) {
            projectInfoList.add(new SluiceInfo(name));
        }
    }

    private void addListernerForBottomToolbar() {
        findViewById(R.id.rain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_4_realtimeMonitor2.this, Level2_4_3_rain2.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.irrigation_schedule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_4_realtimeMonitor2.this, Level2_6_irrigationSchedule2.class);
                startActivity(intent);
            }
        });

    }

}
