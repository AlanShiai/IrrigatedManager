package com.example.ashi.irrigatedmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ashi.irrigatedmanager.gson.Abnormal;
import com.example.ashi.irrigatedmanager.level2_2_3.DrawColumn;
import com.example.ashi.irrigatedmanager.level2_2_3.DrawColumnAndPie;
import com.example.ashi.irrigatedmanager.level2_2_3.DrawPie;
import com.example.ashi.irrigatedmanager.level2_2_3.DrawYearMonthData;
import com.example.ashi.irrigatedmanager.util.Api;
import com.example.ashi.irrigatedmanager.util.Global;
import com.example.ashi.irrigatedmanager.util.HttpUtil;
import com.example.ashi.irrigatedmanager.util.Utility;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Level2_2_3_inspectDetailsPie extends AppCompatActivity {

    DrawColumn columnView;

    DrawPie pieView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_level2_2_3_inspect_details_pie);

//        initData();

//        LinearLayout ll_body = (LinearLayout) findViewById(R.id.inspect_detail_pie);
//        DrawColumnAndPie view = new DrawColumnAndPie(getApplicationContext());
//        ll_body.addView(view);

        LinearLayout column_body = (LinearLayout) findViewById(R.id.draw_column);
        columnView = new DrawColumn(getApplicationContext());
        column_body.addView(columnView);

        LinearLayout pie_body = (LinearLayout) findViewById(R.id.draw_pie);
        pieView = new DrawPie(getApplicationContext());
        pie_body.addView(pieView);

        findViewById(R.id.leve1_2_1_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_3_inspectDetailsPie.this, Level2_2_projectInspection2.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingleChoiceDialog();
            }
        });

        getDataFromServerAndUpdateListView();
        getDataFromServerAndUpdateListView2();
    }

    private void initData() {
        Global.abnormalList.clear();
//        Global.abnormalList.add(new Abnormal("渠首", "430", "20"));
//        Global.abnormalList.add(new Abnormal("闸门", "100", "19"));
//        Global.abnormalList.add(new Abnormal("桥梁", "300", "18"));
//        Global.abnormalList.add(new Abnormal("渡槽", "200", "17"));
//        Global.abnormalList.add(new Abnormal("涵洞", "100", "16"));

        // 8/28/2018 real data
        Global.abnormalList.add(new Abnormal("水闸", "1", "1"));
        Global.abnormalList.add(new Abnormal("渠道", "20", "4"));
        Global.abnormalList.add(new Abnormal("涵洞", "30", "0"));
        Global.abnormalList.add(new Abnormal("桥梁", "25", "0"));
        Global.abnormalList.add(new Abnormal("渡槽", "0", "0"));
        Global.abnormalList.add(new Abnormal("倒虹吸", "0", "0"));
    }

    private void getDataFromServerAndUpdateListView() {
        String url = Api.API_30_patrolInit;
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final List<Abnormal> list = Utility.handleApi30patrolInitResponse(responseText);
                Log.d("aijun patrolInit", responseText+"");
                if ( null != list ) {
                    Global.abnormalList.clear();
                    Global.abnormalList.addAll(list);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            columnView.invalidate();
                            pieView.invalidate();
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
        String url = Api.API_31_queryYearCount + "userId=" + Global.user.id + "&month=05";
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
//                final List<InspectNote> list = Utility.handleApi20patrolResultResponse(responseText);
                Log.d("aijun queryYearCount", responseText+"");
//                if ( ! list.isEmpty() ) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if ( null != listView ) {
//                                IrrigationScheduleInfoAdpter adapter = new IrrigationScheduleInfoAdpter(
//                                        Level2_6_irrigationSchedule2.this, R.layout.irrigation_schedule, list);
//                                listView.setAdapter(adapter);
//                            }
//                        }
//                    });
//                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void showSingleChoiceDialog() {
        final String[] items = new String[]{"一月", "二月", "三月", "四月","五月", "六月", "七月", "八月","九月", "十月", "十一月", "十二月",};
        AlertDialog.Builder builder = new AlertDialog.Builder(Level2_2_3_inspectDetailsPie.this);
        builder.setTitle("选择对话框");
        //千万不要加这句，不然列表显示不出来
//        builder.setMessage("这是一个简单的列表对话框");
//        builder.setIcon(R.mipmap.launcher);
        builder.setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showText(items[which]);
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showText("确定");
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showText("取消");
            }
        });

        builder.create().show();
    }

    private void showText(String text) {
        Toast.makeText(Level2_2_3_inspectDetailsPie.this, text, Toast.LENGTH_SHORT).show();
    }
}
