package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.ashi.irrigatedmanager.level2_2_3.InspectDetailInfo;
import com.example.ashi.irrigatedmanager.level2_2_3.InspectDetailInfoAdpter;
import com.example.ashi.irrigatedmanager.level2_4.Rain;
import com.example.ashi.irrigatedmanager.level2_4.RainAdapter;

import java.util.ArrayList;
import java.util.List;

public class Level2_4_3_rain extends AppCompatActivity {

    private List<Rain> dataList = new ArrayList<Rain>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_4_3_rain);

        initData();
        RainAdapter adapter = new RainAdapter(
                Level2_4_3_rain.this, R.layout.rain_item, dataList);
        ListView listView = (ListView) findViewById(R.id.level_2_4_3_rain_list);
        listView.setAdapter(adapter);

        findViewById(R.id.leve1_2_4_3_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_4_3_rain.this, Level2_4_realtimeMonitor.class);
                startActivity(intent);
            }
        });

    }

    private void initData() {
        dataList.add(new Rain("健康女士"));
        dataList.add(new Rain("减肥呢不考虑了"));
        dataList.add(new Rain("真的吗完美不"));
        dataList.add(new Rain("下雨了吗"));
        dataList.add(new Rain("柳林总雨量"));
        dataList.add(new Rain("张庄桥总雨量"));
        dataList.add(new Rain("忠楼寺总雨量"));
    }
}
