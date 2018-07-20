package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.ashi.irrigatedmanager.level2_2_3.InspectDetailInfo;
import com.example.ashi.irrigatedmanager.level2_2_3.InspectDetailInfoAdpter;

import java.util.ArrayList;
import java.util.List;

public class Level2_2_3_inspectDetails extends AppCompatActivity {

    private List<InspectDetailInfo> dataList = new ArrayList<InspectDetailInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_2_3_inspect_details);

        initData();

        InspectDetailInfoAdpter adapter = new InspectDetailInfoAdpter(
                Level2_2_3_inspectDetails.this, R.layout.inspect_details, dataList);
        ListView listView = (ListView) findViewById(R.id.level_2_2_3_inspect_details);
        listView.setAdapter(adapter);



        findViewById(R.id.leve1_2_2_3_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_3_inspectDetails.this, Level2_2_projectInspection.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initData() {
        dataList.add(new InspectDetailInfo("西闸管理所", "0/10"));
        dataList.add(new InspectDetailInfo("曲周管理所", "0/0"));
        dataList.add(new InspectDetailInfo("肥乡管理所", "0/0"));
        dataList.add(new InspectDetailInfo("高级渠管理所", "0/0"));
        dataList.add(new InspectDetailInfo("张庄桥管理所", "0/0"));
        dataList.add(new InspectDetailInfo("广平管理所", "0/0"));
        dataList.add(new InspectDetailInfo("西八闸管理所", "0/0"));
        dataList.add(new InspectDetailInfo("成安管理所", "7/1"));
        dataList.add(new InspectDetailInfo("马头管理所", "0/0"));
    }
}
