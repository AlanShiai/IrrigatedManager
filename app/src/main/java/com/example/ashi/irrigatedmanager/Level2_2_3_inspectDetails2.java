package com.example.ashi.irrigatedmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.ashi.irrigatedmanager.level2_2_3.InspectDetailInfo;
import com.example.ashi.irrigatedmanager.level2_2_3.InspectDetailInfoAdpter;

import java.util.ArrayList;
import java.util.List;

public class Level2_2_3_inspectDetails2 extends AppCompatActivity {

    private List<InspectDetailInfo> dataList = new ArrayList<InspectDetailInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_2_3_inspect_details2);

        initData();

        InspectDetailInfoAdpter adapter = new InspectDetailInfoAdpter(
                Level2_2_3_inspectDetails2.this, R.layout.inspect_details, dataList);
        ListView listView = (ListView) findViewById(R.id.level_2_2_3_inspect_details);
        listView.setAdapter(adapter);

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