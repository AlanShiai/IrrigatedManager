package com.example.ashi.irrigatedmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

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
        findViewById(R.id.select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingleChoiceDialog();
            }
        });
    }

    private void showSingleChoiceDialog() {
        final String[] items = new String[]{"第一轮", "第二轮", "第三轮", "第四轮", "第五轮", "第六轮", "第七轮", "第八轮", "第九轮", "第十轮",};
        AlertDialog.Builder builder = new AlertDialog.Builder(Level2_6_irrigationSchedule2.this);
        builder.setTitle("选择对话框");
        //千万不要加这句，不然列表显示不出来
//        builder.setMessage("这是一个简单的列表对话框");
        builder.setIcon(R.mipmap.launcher);
        builder.setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showText(items[which]);
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showText("确定");
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showText("取消");
            }
        });

        builder.create().show();
    }

    private void showText(String text) {
        Toast.makeText(Level2_6_irrigationSchedule2.this, text, Toast.LENGTH_SHORT).show();
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
