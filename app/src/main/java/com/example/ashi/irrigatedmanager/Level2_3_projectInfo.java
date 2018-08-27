package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ashi.irrigatedmanager.util.Const;
import com.example.ashi.irrigatedmanager.util.Global;

import java.util.ArrayList;
import java.util.List;

public class Level2_3_projectInfo extends AppCompatActivity {

    private List<ProjectInfo> projectInfoList = new ArrayList<ProjectInfo>();

    private List<ProjectInfo> projectInfoList2 = new ArrayList<ProjectInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_level2_3_project_info);

        ListView listView = (ListView) findViewById(R.id.level_2_3_projectInfo);
        initProjectInfoList();
        initProjectInfoList2();
        ProjectInfoAdpter adapter = new ProjectInfoAdpter(Level2_3_projectInfo.this, R.layout.project_item1, projectInfoList);
        listView.setAdapter(adapter);

        ListView listView2 = (ListView) findViewById(R.id.level2_menu_list);
        ProjectInfoAdpter adapter2 = new ProjectInfoAdpter(Level2_3_projectInfo.this, R.layout.project_item2, projectInfoList2);
        listView2.setAdapter(adapter2);
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Global.projectInfoType = "channel";
                Global.projectInfoSubtype = projectInfoList2.get(position).getType();
                Intent intent = new Intent(Level2_3_projectInfo.this, Level2_3_projectInfo3.class);
                startActivity(intent);
            }
        });
        listView2.setVisibility(View.GONE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setBackgroundColor(0x515151);
                Log.d("aijun", position + "");
                ListView listView = (ListView) findViewById(R.id.level2_menu_list);
                if ( 0 == position) {
                    listView.setVisibility(View.VISIBLE);
                } else {
                    Global.projectInfoType = projectInfoList.get(position).getType();
                    Global.projectInfoSubtype = "1";
                    listView.setVisibility(View.GONE);
                    Intent intent = new Intent(Level2_3_projectInfo.this, Level2_3_projectInfo3.class);
                    startActivity(intent);
                }
            }
        });

        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_3_projectInfo.this, Level2_1_irrigateOverview.class);
                startActivity(intent);
                finish();
            }
        });
        addListernerForBottomToolbar();
    }

    private void addListernerForBottomToolbar() {
        findViewById(R.id.overview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_3_projectInfo.this, Level2_1_irrigateOverview.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.overview_inspect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_3_projectInfo.this, Level2_2_projectInspection2.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.monitor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_3_projectInfo.this, Level2_4_realtimeMonitor2.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.overview_appval).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_3_projectInfo.this, Level2_5_appvalProcess.class);
                startActivity(intent);
                finish();
            }
        });
//        findViewById(R.id.project_info).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Level2_5_appvalProcess.this, Level2_3_projectInfo.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }

    private void initProjectInfoList() {
        projectInfoList.add(new ProjectInfo("渠道", "channel"));
        projectInfoList.add(new ProjectInfo("渠首", "channelHead"));
        projectInfoList.add(new ProjectInfo("水闸", "sluice"));
        projectInfoList.add(new ProjectInfo("涵洞", "culvert"));
        projectInfoList.add(new ProjectInfo("渡槽", "aqueduct"));
        projectInfoList.add(new ProjectInfo("桥梁", "bridge"));
        projectInfoList.add(new ProjectInfo("水库", "reservoir"));
        projectInfoList.add(new ProjectInfo("泵站", "pump"));
        projectInfoList.add(new ProjectInfo("水电站", "waterPower"));
        projectInfoList.add(new ProjectInfo("枢纽", "boundaries"));
        projectInfoList.add(new ProjectInfo("县界", "boundaries"));
        projectInfoList.add(new ProjectInfo("倒虹吸", "inverted"));
        projectInfoList.add(new ProjectInfo("跌水", "waterFall"));
    }

    private void initProjectInfoList2() {
        projectInfoList2.add(new ProjectInfo("总干渠", "1"));
        projectInfoList2.add(new ProjectInfo("干渠", "2"));
        projectInfoList2.add(new ProjectInfo("干渠段", "3"));
        projectInfoList2.add(new ProjectInfo("支渠", "4"));
        projectInfoList2.add(new ProjectInfo("排水干渠", "5"));
        projectInfoList2.add(new ProjectInfo("排水支渠", "6"));
    }
}
