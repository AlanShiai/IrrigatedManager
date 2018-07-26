package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Level2_6_irrigationSchedule2 extends AppCompatActivity {

    private List<IrrigationScheduleInfo> projectInfoList = new ArrayList<IrrigationScheduleInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_6_irrigation_schedule2);

        ListView listView = (ListView) findViewById(R.id.level_2_6_irrigation_schedule);
        initProjectInfoList();
        IrrigationScheduleInfoAdpter adapter = new IrrigationScheduleInfoAdpter(
                Level2_6_irrigationSchedule2.this, R.layout.irrigation_schedule, projectInfoList);
        listView.setAdapter(adapter);

        addListernerForBackButton();
    }

    private void initProjectInfoList() {
        projectInfoList.add(new IrrigationScheduleInfo("成安县", "13.75%"));
        projectInfoList.add(new IrrigationScheduleInfo("临漳县", "20.00%"));
        projectInfoList.add(new IrrigationScheduleInfo("魏县", "10.00%"));
        projectInfoList.add(new IrrigationScheduleInfo("磁县", "11.00%"));
    }

    private void addListernerForBackButton() {
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_6_irrigationSchedule2.this, Level2_4_realtimeMonitor2.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
