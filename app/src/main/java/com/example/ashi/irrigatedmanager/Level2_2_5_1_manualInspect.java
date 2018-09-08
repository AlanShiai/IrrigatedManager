package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ashi.irrigatedmanager.level2_2_3.InspectDetailInfo;
import com.example.ashi.irrigatedmanager.level2_2_3.InspectDetailInfoAdpter;
import com.example.ashi.irrigatedmanager.level2_5.ManualInspectItem;
import com.example.ashi.irrigatedmanager.level2_5.ManualInspectItemAdapter;
import com.example.ashi.irrigatedmanager.util.Api;
import com.example.ashi.irrigatedmanager.util.Global;
import com.example.ashi.irrigatedmanager.util.HttpUtil;
import com.example.ashi.irrigatedmanager.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Level2_2_5_1_manualInspect extends AppCompatActivity {

    private List<ManualInspectItem> dataList = new ArrayList<ManualInspectItem>();

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_level2_2_5_1_manual_inspect);

        initData();

        listView = (ListView) findViewById(R.id.level_2_2_5_1_inspect_details);
//        ManualInspectItemAdapter adapter = new ManualInspectItemAdapter(
//                Level2_2_5_1_manualInspect.this, R.layout.manual_inspect, dataList);
//        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Global.patrolId = dataList.get(position).goalId;
                Global.patrolType = dataList.get(position).type;
                Intent intent = new Intent(Level2_2_5_1_manualInspect.this, Level2_2_5_2_manualInspect.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_5_1_manualInspect.this, Level2_2_projectInspection2.class);
                startActivity(intent);
                finish();
            }
        });

        getDataFromServerAndUpdateListView();
    }

    private void getDataFromServerAndUpdateListView() {
        String url = Api.API_26_patrolDesQuery + "userId=" + Global.user.id  + "&type=";
        Log.d("aijun patrolDesQuery", url);
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                Log.d("aijun patrolDesQuery", responseText);
                final List<ManualInspectItem> list = Utility.handleApi26patrolDesQueryResponse(responseText);
                Log.d("aijun patrolDesQuery", list.size()+"");
                if ( ! list.isEmpty() ) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ( null != listView ) {
                                dataList.clear();
                                for (ManualInspectItem manualInspectItem : list) {
                                    if (null != manualInspectItem.goalName) {
                                        dataList.add(manualInspectItem);
                                    }
                                }
                                ManualInspectItemAdapter adapter = new ManualInspectItemAdapter(
                                        Level2_2_5_1_manualInspect.this, R.layout.manual_inspect, dataList);
                                listView.setAdapter(adapter);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void initData() {
        dataList.add(new ManualInspectItem("滏阳河张庄桥"));
        dataList.add(new ManualInspectItem("钟楼市二干渠节制闸"));
        dataList.add(new ManualInspectItem("滏阳二干渠"));
        dataList.add(new ManualInspectItem("滏阳河张庄桥"));
        dataList.add(new ManualInspectItem("钟楼市二干渠节制闸"));
        dataList.add(new ManualInspectItem("滏阳二干渠"));
    }
}
