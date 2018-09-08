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

    private List<ProjectInfo> projectTypeList = new ArrayList<ProjectInfo>();

    private List<ProjectInfo> projectSubTypeList = new ArrayList<ProjectInfo>();

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
        initProjectTypeList();
        initProjectInfoList2();
        ProjectInfoAdpter adapter = new ProjectInfoAdpter(Level2_3_projectInfo.this, R.layout.project_item1, projectTypeList);
        listView.setAdapter(adapter);

        ListView listView2 = (ListView) findViewById(R.id.level2_menu_list);
        ProjectInfoAdpter adapter2 = new ProjectInfoAdpter(Level2_3_projectInfo.this, R.layout.project_item2, projectSubTypeList);
        listView2.setAdapter(adapter2);
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Global.projectInfoType = "channel";
                Global.projectInfoSubtype = projectSubTypeList.get(position).getType();
                Global.projectInfoName = projectSubTypeList.get(position).getName() + "列表";
                Intent intent = new Intent(Level2_3_projectInfo.this, Level2_3_projectInfo3.class);
                startActivity(intent);
                finish();
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
                    Global.projectInfoType = projectTypeList.get(position).getType();
                    Global.projectInfoSubtype = "1";
                    Global.projectInfoName = projectTypeList.get(position).getName() + "列表";
                    listView.setVisibility(View.GONE);
                    Intent intent = new Intent(Level2_3_projectInfo.this, Level2_3_projectInfo3.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_3_projectInfo.this, Logout.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initProjectTypeList() {
        projectTypeList.add(new ProjectInfo("渠道", "channel"));
        projectTypeList.add(new ProjectInfo("渠首", "channelHead"));
        projectTypeList.add(new ProjectInfo("水闸", "sluice"));
        projectTypeList.add(new ProjectInfo("涵洞", "culvert"));
        projectTypeList.add(new ProjectInfo("渡槽", "aqueduct"));
        projectTypeList.add(new ProjectInfo("桥梁", "bridge"));
        projectTypeList.add(new ProjectInfo("水库", "reservoir"));
        projectTypeList.add(new ProjectInfo("泵站", "pump"));
        projectTypeList.add(new ProjectInfo("水电站", "waterPower"));
        projectTypeList.add(new ProjectInfo("枢纽", "hinge"));
        projectTypeList.add(new ProjectInfo("县界", "boundaries"));
        projectTypeList.add(new ProjectInfo("倒虹吸", "inverted"));
        projectTypeList.add(new ProjectInfo("跌水", "waterFall"));
    }

    private void initProjectInfoList2() {
        projectSubTypeList.add(new ProjectInfo("总干渠", "1"));
        projectSubTypeList.add(new ProjectInfo("干渠", "2"));
        projectSubTypeList.add(new ProjectInfo("干渠段", "3"));
        projectSubTypeList.add(new ProjectInfo("支渠", "4"));
        projectSubTypeList.add(new ProjectInfo("排水干渠", "5"));
        projectSubTypeList.add(new ProjectInfo("排水支渠", "6"));
    }
}
