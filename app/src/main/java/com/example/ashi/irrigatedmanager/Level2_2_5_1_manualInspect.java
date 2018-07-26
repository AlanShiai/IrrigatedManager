package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ashi.irrigatedmanager.level2_2_3.InspectDetailInfo;
import com.example.ashi.irrigatedmanager.level2_2_3.InspectDetailInfoAdpter;
import com.example.ashi.irrigatedmanager.level2_5.ManualInspectItem;
import com.example.ashi.irrigatedmanager.level2_5.ManualInspectItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class Level2_2_5_1_manualInspect extends AppCompatActivity {

    private List<ManualInspectItem> dataList = new ArrayList<ManualInspectItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_2_5_1_manual_inspect);

        initData();

        ManualInspectItemAdapter adapter = new ManualInspectItemAdapter(
                Level2_2_5_1_manualInspect.this, R.layout.manual_inspect, dataList);
        ListView listView = (ListView) findViewById(R.id.level_2_2_5_1_inspect_details);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Level2_2_5_1_manualInspect.this, Level2_2_5_2_manualInspect.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.leve1_2_2_5_1_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_5_1_manualInspect.this, Level2_2_projectInspection2.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initData() {
        dataList.add(new ManualInspectItem("滏阳河张庄桥"));
        dataList.add(new ManualInspectItem("呵呵干渠"));
        dataList.add(new ManualInspectItem("滏阳河张庄桥"));
    }
}
