package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashi.irrigatedmanager.gson.User;
import com.example.ashi.irrigatedmanager.level2_2_3.InspectDetailInfo;
import com.example.ashi.irrigatedmanager.level2_2_3.InspectDetailInfoAdpter;
import com.example.ashi.irrigatedmanager.level5.Appval;
import com.example.ashi.irrigatedmanager.level5.AppvalAdapter;
import com.example.ashi.irrigatedmanager.util.Const;
import com.example.ashi.irrigatedmanager.util.HttpUtil;
import com.example.ashi.irrigatedmanager.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Level2_5_appvalProcess extends AppCompatActivity {

    private List<Appval> dataList = new ArrayList<Appval>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_5_appval_process);

        Button button = (Button) findViewById(R.id.should_do_task_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.boze-tech.com/zfh_manager/a/app/login/loginCheck?loginName=admin&passwd=123";
                HttpUtil.sendOkHttpRequest(url, new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String responseText = response.body().string();
                        Log.d("aijun login", responseText);
                        final User user = Utility.handleLoginResponse(responseText);
                        Log.d("aijun login", Boolean.toString(user.isLoginSuccess()));
//                        final Weather weather = Utility.handleWeatherResponse(responseText);
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Level2_5_appvalProcess.this, "登录失败", Toast.LENGTH_SHORT).show();
//                                swipeRefresh.setRefreshing(false);
                            }
                        });
                    }
                });
            }
        });

        initData();
        AppvalAdapter adapter = new AppvalAdapter(
                Level2_5_appvalProcess.this, R.layout.appval_item, dataList);
        ListView listView = (ListView) findViewById(R.id.level_2_5_appval_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Level2_5_appvalProcess.this, Level2_5_1_appvalDetails.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.leve1_2_5_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_5_appvalProcess.this, Level2_1_irrigateOverview.class);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        dataList.add(new Appval("呵呵干渠"));
        dataList.add(new Appval("八支渠节制闸"));
        dataList.add(new Appval("巡查异常处理"));
        dataList.add(new Appval("巡查异常处理"));
        dataList.add(new Appval("巡查异常处理"));
        dataList.add(new Appval("巡查异常处理"));
        dataList.add(new Appval("巡查异常处理"));
        dataList.add(new Appval("巡查异常处理"));
        dataList.add(new Appval("巡查异常处理"));
    }
}
