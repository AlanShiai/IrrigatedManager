package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.ashi.irrigatedmanager.level5.Appval;
import com.example.ashi.irrigatedmanager.level5.AppvalAdapter;
import com.example.ashi.irrigatedmanager.level5.AppvalDetails;
import com.example.ashi.irrigatedmanager.level5.AppvalDetailsAdapter;

import java.util.ArrayList;
import java.util.List;

public class Level2_5_1_appvalDetails extends AppCompatActivity {

    private List<AppvalDetails> dataList = new ArrayList<AppvalDetails>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_5_1_appval_details);

        initData();
        AppvalDetailsAdapter adapter = new AppvalDetailsAdapter(
                Level2_5_1_appvalDetails.this, R.layout.appval_item2, dataList);
        ListView listView = (ListView) findViewById(R.id.level_2_5_1_appval_list);
        listView.setAdapter(adapter);

        findViewById(R.id.leve1_2_5_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_5_1_appvalDetails.this, Level2_5_appvalProcess.class);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        dataList.add(new AppvalDetails("系统管理员"));
        dataList.add(new AppvalDetails("大名管理所所长"));
    }
}
