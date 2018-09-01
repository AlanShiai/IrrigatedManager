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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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

public class Level2_6_irrigationSchedule2 extends AppCompatActivity {

    private List<IrrigationScheduleInfo> projectInfoList = new ArrayList<IrrigationScheduleInfo>();

    ListView listView;

    Button selectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_level2_6_irrigation_schedule2);

        listView = (ListView) findViewById(R.id.level_2_6_irrigation_schedule);
        initProjectInfoList();
//        IrrigationScheduleInfoAdpter adapter = new IrrigationScheduleInfoAdpter(
//                Level2_6_irrigationSchedule2.this, R.layout.irrigation_schedule, projectInfoList);
//        listView.setAdapter(adapter);

        addListernerForBackButton();
        selectButton = (Button) findViewById(R.id.select);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingleChoiceDialog();
            }
        });

        getDataFromServerAndUpdateListView();
    }

    private void getDataFromServerAndUpdateListView() {
        // "http://www.boze-tech.com/zfh_manager/a/app/project/queryIrrigationSchedule?userId="+ Global.userId + "&year=2018&turn=1";
        String url = Api.API_19_queryIrrigationSchedule + "&turn=" + Global.irrigation_turn;
        Log.d("aijun, irrigation", url);
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final List<IrrigationScheduleInfo> list = Utility.handleApi19queryIrrigationScheduleResponse(responseText);
                Log.d("aijun IrrigationSched", list+"");
                if ( null != list ) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ( null != listView ) {
                                IrrigationScheduleInfoAdpter adapter = new IrrigationScheduleInfoAdpter(
                                        Level2_6_irrigationSchedule2.this, R.layout.irrigation_schedule, list);
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

    int newIndex;
    int selectIndex;
    private void showSingleChoiceDialog() {
        selectIndex = Global.irrigation_turn - 1;
        newIndex = Global.irrigation_turn - 1;

        final String[] items = new String[]{"第一轮", "第二轮", "第三轮", "第四轮", "第五轮", "第六轮", "第七轮", "第八轮", "第九轮", "第十轮",};
        AlertDialog.Builder builder = new AlertDialog.Builder(Level2_6_irrigationSchedule2.this);
        builder.setTitle("选择对话框");
        //千万不要加这句，不然列表显示不出来
//        builder.setMessage("这是一个简单的列表对话框");
//        builder.setIcon(R.mipmap.launcher);
        builder.setSingleChoiceItems(items, selectIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newIndex = which;
                showText(items[which]);
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(Global.irrigation_turn != newIndex + 1) {
                    selectButton.setText(items[newIndex]);
                    Global.irrigation_turn = newIndex + 1;
                    getDataFromServerAndUpdateListView();
                }
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
        Toast.makeText(Level2_6_irrigationSchedule2.this, text, Toast.LENGTH_SHORT).show();
    }

    private void initProjectInfoList() {
        projectInfoList.add(new IrrigationScheduleInfo("井陉县"));
        projectInfoList.add(new IrrigationScheduleInfo("正定县"));
        projectInfoList.add(new IrrigationScheduleInfo("成磁县"));
        projectInfoList.add(new IrrigationScheduleInfo("行唐县"));
        projectInfoList.add(new IrrigationScheduleInfo("灵寿县"));
        projectInfoList.add(new IrrigationScheduleInfo("深泽县"));
    }

    private void addListernerForBackButton() {
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_6_irrigationSchedule2.this, Level2_4_realtimeMonitor2.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
