package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ashi.irrigatedmanager.level2_4.Rain;
import com.example.ashi.irrigatedmanager.level2_4.RainAdapter;
import com.example.ashi.irrigatedmanager.level2_4.RainDetail;
import com.example.ashi.irrigatedmanager.level2_4.RainDetailAdapter;
import com.example.ashi.irrigatedmanager.level2_4.RainDetailData;
import com.example.ashi.irrigatedmanager.util.Api;
import com.example.ashi.irrigatedmanager.util.Global;
import com.example.ashi.irrigatedmanager.util.HttpUtil;
import com.example.ashi.irrigatedmanager.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Level2_4_3_rain3 extends AppCompatActivity {

    private List<RainDetail> dataList = new ArrayList<RainDetail>();

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_4_3_rain3);

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(Global.rain_name);

//        initData();
        listView = (ListView) findViewById(R.id.level_2_4_3_rain_list);

        TextView rain_date = (TextView) findViewById(R.id.rain_date);
        rain_date.setText("  " + Utility.getThisYear() + "年" + Utility.getThisMonth() + "月  ");

//        RainDetailAdapter adapter = new RainDetailAdapter(
//                Level2_4_3_rain3.this, R.layout.rain_detail_item, dataList);
//        listView.setAdapter(adapter);

        addListernerForBackButton();

        getDataFromServerAndUpdateView();
    }

    private void getDataFromServerAndUpdateView() {
        // "http://www.boze-tech.com/zfh_manager/a/app/login/getRainList?userId=1&name=柳林总雨量";
        String url = Api.API_06_getRainList + "userId=" + Global.user.id + "&projectId=" + Global.rain_name;
//        url = "http://221.193.192.143:8099/zfh_manager/a/app/login/getRainList?userId=8bc3a54c71dd4c648e3ce28612d0ba11&projectId=02310303";
        Log.d("aijun url", url+"");
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final List<RainDetailData> list = Utility.handleApi06getRainListResponse(responseText);
                Log.d("aijun getRainList", responseText+"");
                Log.d("aijun getRainList", list+"");
                if ( null != list ) {
                    updateData(list);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ( null != listView ) {
                                RainDetailAdapter adapter = new RainDetailAdapter(
                                        Level2_4_3_rain3.this, R.layout.rain_detail_item, dataList);
                                listView.setAdapter(adapter);
                            }
                        }
                    });
                }
            }

            public void updateData(List<RainDetailData> list) {
                dataList.clear();
                HashMap<String, String> map = new HashMap<String, String>();
                for (RainDetailData rainDetailData : list) {
                    map.put(rainDetailData.time, rainDetailData.rain_data);
                }

                int lastDay = Utility.getCurrentMonthLastDay();
                int halfDays = lastDay/2;
                if (lastDay % 2 != 0) {
                    halfDays = lastDay/2 + 1;
                } else {
                    halfDays = lastDay/2;
                }
                String day1, rain1, day2, rain2;
                for (int i = 1; i <= lastDay/2 ; i++ ) {
                    day1=i+""; rain1="0"; day2=halfDays+i+""; rain2="0";
                    if(map.containsKey(day1)) {
                        rain1 = map.get(day1);
                    }
                    if(map.containsKey("0" + day1)) {
                        rain1 = map.get("0" + day1);
                    }
                    if(map.containsKey(day2)) {
                        rain2 = map.get(day2);
                    }
                    if(map.containsKey("0" + day2)) {
                        rain2 = map.get("0" + day2);
                    }
                    dataList.add(new RainDetail(day1, rain1, day2, rain2));
                }
                if (lastDay % 2 != 0) {
                    day1=lastDay/2+1+""; rain1="0";
                    if(map.containsKey(day1)) {
                        rain1 = map.get(day1);
                    }
                    dataList.add(new RainDetail(day1, rain1, "-", "-"));
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void addListernerForBackButton() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_4_3_rain3.this, Level2_4_3_rain2.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initData() {
        int lastDay = Utility.getCurrentMonthLastDay();
        int halfDays = lastDay/2;
        if (lastDay % 2 != 0) {
            halfDays = lastDay/2 + 1;
        } else {
            halfDays = lastDay/2;
        }
        for (int i = 1; i <= lastDay/2 ; i++ ) {
            dataList.add(new RainDetail(i+"", "0", halfDays+i+"", "0"));
        }
        if (lastDay % 2 != 0) {
            dataList.add(new RainDetail(lastDay/2+1+"", "0", "-", "-"));
        }
    }

}
