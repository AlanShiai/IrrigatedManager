package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ashi.irrigatedmanager.util.Const;

import java.util.ArrayList;
import java.util.List;

public class Level2_6_irrigationSchedule extends AppCompatActivity {

    private List<IrrigationScheduleInfo> projectInfoList = new ArrayList<IrrigationScheduleInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_level2_6_irrigation_schedule);

        TextView title = (TextView) findViewById(R.id.level_2_6_title);
        title.setText(Const.LEVEL_2_TITILE_6);

        ListView listView = (ListView) findViewById(R.id.level_2_6_irrigation_schedule);
        initProjectInfoList();
        IrrigationScheduleInfoAdpter adapter = new IrrigationScheduleInfoAdpter(Level2_6_irrigationSchedule.this, R.layout.irrigation_schedule, projectInfoList);
        listView.setAdapter(adapter);

        ImageView backButton = (ImageView) findViewById(R.id.leve1_2_6_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_6_irrigationSchedule.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initProjectInfoList() {
        projectInfoList.add(new IrrigationScheduleInfo("成安县", "13.75%"));
        projectInfoList.add(new IrrigationScheduleInfo("临漳县", "20.00%"));
        projectInfoList.add(new IrrigationScheduleInfo("魏县", "10.00%"));
        projectInfoList.add(new IrrigationScheduleInfo("磁县", "11.00%"));
    }
}
