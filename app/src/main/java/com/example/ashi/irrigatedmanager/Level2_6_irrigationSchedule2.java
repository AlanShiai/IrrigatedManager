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
        projectInfoList.add(new IrrigationScheduleInfo("井陉县", "15.75%"));
        projectInfoList.add(new IrrigationScheduleInfo("正定县", "75.00%"));
        projectInfoList.add(new IrrigationScheduleInfo("成磁县", "98.00%"));
        projectInfoList.add(new IrrigationScheduleInfo("行唐县", "40.00%"));
        projectInfoList.add(new IrrigationScheduleInfo("灵寿县", "15.75%"));
        projectInfoList.add(new IrrigationScheduleInfo("深泽县", "40.00%"));
        projectInfoList.add(new IrrigationScheduleInfo("赞皇县", "98.00%"));
        projectInfoList.add(new IrrigationScheduleInfo("无极县", "40.00%"));
        projectInfoList.add(new IrrigationScheduleInfo("平山县", "15.75%"));
        projectInfoList.add(new IrrigationScheduleInfo("元氏县", "40.00%"));
        projectInfoList.add(new IrrigationScheduleInfo("磁县", "98.00%"));
        projectInfoList.add(new IrrigationScheduleInfo("漳县", "40.00%"));
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
