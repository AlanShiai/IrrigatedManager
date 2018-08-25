package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.ashi.irrigatedmanager.level2_6.ProjectInfo3;
import com.example.ashi.irrigatedmanager.level2_6.ProjectInfo3Adpter;

import java.util.ArrayList;
import java.util.List;

public class Level2_3_projectInfo3 extends AppCompatActivity {

    private List<ProjectInfo3> projectInfoList = new ArrayList<ProjectInfo3>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_level2_3_project_info3);

        ListView listView = (ListView) findViewById(R.id.project_info_list);
        initProjectInfoList();
        ProjectInfo3Adpter adapter = new ProjectInfo3Adpter(Level2_3_projectInfo3.this, R.layout.project_item_3, projectInfoList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Level2_3_projectInfo3.this, Level2_3_projectInfo4.class);
                startActivity(intent);
            }
        });

        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_3_projectInfo3.this, Level2_3_projectInfo.class);
                startActivity(intent);
                finish();
            }
        });

    }


    private void initProjectInfoList() {
        projectInfoList.add(new ProjectInfo3());
        projectInfoList.add(new ProjectInfo3());
        projectInfoList.add(new ProjectInfo3());
        projectInfoList.add(new ProjectInfo3());
        projectInfoList.add(new ProjectInfo3());
        projectInfoList.add(new ProjectInfo3());
        projectInfoList.add(new ProjectInfo3());
        projectInfoList.add(new ProjectInfo3());
        projectInfoList.add(new ProjectInfo3());
        projectInfoList.add(new ProjectInfo3());
        projectInfoList.add(new ProjectInfo3());
        projectInfoList.add(new ProjectInfo3());
    }
}
