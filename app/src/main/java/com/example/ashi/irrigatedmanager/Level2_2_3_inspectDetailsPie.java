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

import com.example.ashi.irrigatedmanager.level2_2_3.DrawColumn;
import com.example.ashi.irrigatedmanager.level2_2_3.DrawColumnAndPie;
import com.example.ashi.irrigatedmanager.level2_2_3.DrawPie;
import com.example.ashi.irrigatedmanager.level2_2_3.DrawYearMonthData;
import com.example.ashi.irrigatedmanager.util.Api;
import com.example.ashi.irrigatedmanager.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Level2_2_3_inspectDetailsPie extends AppCompatActivity {

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

//        LinearLayout ll_body = (LinearLayout) findViewById(R.id.inspect_detail_pie);
//        DrawColumnAndPie view = new DrawColumnAndPie(getApplicationContext());
//        ll_body.addView(view);

        LinearLayout column_body = (LinearLayout) findViewById(R.id.draw_column);
        DrawColumn columnView = new DrawColumn(getApplicationContext());
        column_body.addView(columnView);

        LinearLayout pie_body = (LinearLayout) findViewById(R.id.draw_pie);
        DrawPie pieView = new DrawPie(getApplicationContext());
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

    private void getDataFromServerAndUpdateListView() {
        String url = Api.API_30_patrolInit;
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
//                final List<InspectNote> list = Utility.handleApi20patrolResultResponse(responseText);
                Log.d("aijun patrolInit", responseText+"");
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
    private void getDataFromServerAndUpdateListView2() {
        String url = Api.API_31_queryYearCount;
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
        builder.setIcon(R.mipmap.launcher);
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
