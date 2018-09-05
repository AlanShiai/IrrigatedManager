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

import com.example.ashi.irrigatedmanager.gson.TaskFlow;
import com.example.ashi.irrigatedmanager.gson.TaskFlowAdapter;
import com.example.ashi.irrigatedmanager.level5.Appval;
import com.example.ashi.irrigatedmanager.level5.AppvalAdapter;
import com.example.ashi.irrigatedmanager.level5.AppvalDetails;
import com.example.ashi.irrigatedmanager.level5.AppvalDetailsAdapter;
import com.example.ashi.irrigatedmanager.level5.BusinessForm;
import com.example.ashi.irrigatedmanager.level5.MyProcess;
import com.example.ashi.irrigatedmanager.level5.ProcessAdapter;
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

public class Level2_5_1_appvalDetails extends AppCompatActivity {

    private List<TaskFlow> dataList = new ArrayList<TaskFlow>();

    TextView appval_details_title;

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
        setContentView(R.layout.activity_level2_5_1_appval_details);

        listView = (ListView) findViewById(R.id.level_2_5_1_appval_list);

        initData();
        TaskFlowAdapter adapter = new TaskFlowAdapter(
                Level2_5_1_appvalDetails.this, R.layout.appval_item2, dataList);
        listView.setAdapter(adapter);

        appval_details_title = (TextView) findViewById(R.id.appval_details_title);

        if (Global.lastPageIsTodo) {
            findViewById(R.id.next_step).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.next_step).setVisibility(View.INVISIBLE);
        }

        findViewById(R.id.next_step).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_5_1_appvalDetails.this, Level2_5_2_appvalProcess.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_5_1_appvalDetails.this, Level2_5_appvalProcess.class);
                startActivity(intent);
                finish();
            }
        });

        getDataFromServerAndUpdateListView();
        getDataFromServerAndUpdateListView2();
    }

    private void getDataFromServerAndUpdateListView() {
        // address + "/a/app/actTask/businessForm?businessKey=pro_patrol_result_deal:98eaf7ee37354b48b875caf30bbad7a9";
        String url = Api.API_16_businessForm + "businessKey=" + Global.businessKey;
        Log.d("aijun ", url);
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final BusinessForm businessForm = Utility.handleApi16businessFormResponse(responseText);
                Log.d("aijun, businessForm", responseText+"");
                Log.d("aijun, businessForm", businessForm+"");
                if ( null != businessForm) {
                    Log.d("aijun, businessForm", businessForm.name + "");
                    Log.d("aijun, businessForm", businessForm.workflow + "");
                }
                if ( null != businessForm ) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            appval_details_title.setText(businessForm.name.replace("@@", "      "));
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

    private void getDataFromServerAndUpdateListView2() {
        // address + "/a/app/actTask/taskFlow?procInsId=93acd047627d45edad2102a7cf00cc0e";
        String url = Api.API_33_taskFlow + "procInsId=" + Global.processInstanceId;
        Log.d("aijun API_33_taskFlow", url);
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
//                final BusinessForm businessForm = Utility.handleApi16businessFormResponse(responseText);
                Log.d("aijun, API_33_taskFlow", responseText+"");
                final List<TaskFlow> list = Utility.handleApi33taskFlowResponse(responseText);
                Log.d("aijun, API_33_taskFlow", list.size()+"");
                if ( null != list ) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dataList.clear();
                            dataList.addAll(list);
                            TaskFlowAdapter adapter = new TaskFlowAdapter(
                                    Level2_5_1_appvalDetails.this, R.layout.appval_item2, dataList);
                            listView.setAdapter(adapter);
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
        dataList.add(new TaskFlow("系统管理员"));
        dataList.add(new TaskFlow("大名管理所所长"));
    }
}
