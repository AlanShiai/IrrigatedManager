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

import com.example.ashi.irrigatedmanager.level2_4.SluiceInfo;
import com.example.ashi.irrigatedmanager.level2_4.SluiceInfoAdapter;

import java.util.ArrayList;
import java.util.List;

public class Level2_4_1_sluice extends AppCompatActivity {

    private List<SluiceInfo> projectInfoList = new ArrayList<SluiceInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_level2_4_1_sluice);

        initProjectInfoList();

        ListView listView = (ListView) findViewById(R.id.level_2_4_1_sluice_list);
        initProjectInfoList();
        SluiceInfoAdapter adapter = new SluiceInfoAdapter(Level2_4_1_sluice.this, R.layout.sluice_item1, projectInfoList);
        listView.setAdapter(adapter);

        Button backButton = (Button) findViewById(R.id.leve1_2_4_1_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_4_1_sluice.this, Level2_4_realtimeMonitor.class);
                startActivity(intent);
            }
        });
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
    }
}
