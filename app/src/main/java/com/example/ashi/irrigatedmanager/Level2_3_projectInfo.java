package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ashi.irrigatedmanager.util.Const;

import java.util.ArrayList;
import java.util.List;

public class Level2_3_projectInfo extends AppCompatActivity {

    private List<ProjectInfo> projectInfoList = new ArrayList<ProjectInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_3_project_info);

        ListView listView = (ListView) findViewById(R.id.level_2_3_projectInfo);
        initProjectInfoList();
        ProjectInfoAdpter adapter = new ProjectInfoAdpter(Level2_3_projectInfo.this, R.layout.project_item1, projectInfoList);
        listView.setAdapter(adapter);

        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_3_projectInfo.this, Level2_2_projectInspection2.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initProjectInfoList() {
        String[] strings = {
                "渠道" ,
                "渠首" ,
                "桥梁" ,
                "水闸" ,
                "枢纽" ,
                "县界" ,
                "跌水" ,
                "倒虹吸" ,
                "渡槽" ,
                "涵洞" ,
                "泵站" ,
                "水电站" ,
                "水库"};
        for (String name : strings) {
            projectInfoList.add(new ProjectInfo(name));
        }
    }
}
