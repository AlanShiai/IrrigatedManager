package com.example.ashi.irrigatedmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashi.irrigatedmanager.level2_4.SluiceInfo;
import com.example.ashi.irrigatedmanager.level2_4.SluiceInfoAdapter;
import com.example.ashi.irrigatedmanager.util.Api;
import com.example.ashi.irrigatedmanager.util.HttpUtil;
import com.example.ashi.irrigatedmanager.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Level2_4_realtimeMonitor2 extends AppCompatActivity {

    private List<SluiceInfo> sluiceInfoList = new ArrayList<SluiceInfo>();
    private List<Boolean> enableList = new ArrayList<Boolean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_level2_4_realtime_monitor2);

//        initProjectInfoList();
        createAreaForSluiceListLayout();

        /*
        ListView listView = (ListView) findViewById(R.id.level_2_4_1_sluice_list);
        SluiceInfoAdapter adapter = new SluiceInfoAdapter(Level2_4_realtimeMonitor2.this, R.layout.sluice_item1, projectInfoList);
        listView.setAdapter(adapter);
        */

        addListernerForTopToolbar();
        addListernerForBackButton();
        addListernerForBottomToolbar();

        findViewById(R.id.select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingleChoiceDialog();
            }
        });

        getSluiceDataFromServerAndUpdateListView1();
        getDataFromServerAndUpdateListView2();
        getDataFromServerAndUpdateListView3();
    }

    private void createAreaForSluiceListLayout() {
        enableList.clear();
        for (int i = 0 ; i < sluiceInfoList.size(); i++) {
            enableList.add(false);
        }

        boolean displayDetails = true;
        LinearLayout layout = (LinearLayout) findViewById(R.id.sluice_list_layout);
        layout.removeAllViews();
        int index = 0;
        for (SluiceInfo sluiceInfo: sluiceInfoList) {
            View view = LayoutInflater.from(Level2_4_realtimeMonitor2.this).inflate(R.layout.sluice_item1,
                    layout, false);
            TextView name = (TextView) view.findViewById(R.id.sluice_name);
            name.setText(sluiceInfo.getName());
            TextView time = (TextView) view.findViewById(R.id.time);
            time.setText(sluiceInfo.time);
            view.findViewById(R.id.show_details).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout layout = (LinearLayout) v.getParent();
                    TextView text = (TextView) layout.findViewById(R.id.sluice_name);
                    for (int i = 0; i < sluiceInfoList.size(); i++ ) {
                        if (text.getText().equals(sluiceInfoList.get(i).getName())) {
                            if (enableList.get(i) == true) {
                                enableList.set(i, false);
                            } else {
                                enableList.set(i, true);
                            }
                        }
                    }
                    createAreaForSluiceListLayout();
                }
            });
            layout.addView(view);

            ((ImageView) view.findViewById(R.id.show_details)).setImageResource(R.drawable.c6);
            if (enableList.get(index)) {
                ((ImageView) view.findViewById(R.id.show_details)).setImageResource(R.drawable.e8);
                View view2 = LayoutInflater.from(Level2_4_realtimeMonitor2.this).inflate(R.layout.sluice_item2,
                        layout, false);
                layout.addView(view2);
            }
            index ++;
        }
    }

    // [{"project_name":"张庄桥分洪闸位","level9":"0.00","level8":"0.00","level7":"0.00",
    // "project_type":"sluice","level6":"0.00","level10":"0.00","level5":"0.00","level4":"0.02","id":"ad407c9bd9634d0bac800651287a8c1f","level2":"0.01",
    // "level3":"0.00","time":"2018-08-27 17:24:51","level1":"0.01","project_id":null,"hole":"5"}]
    private void getSluiceDataFromServerAndUpdateListView1() {
        String url = Api.API_03_getSluiceMonitorList;
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
//                String hardcodeString = "[{\"project_name\":\"张庄桥分洪闸位\",\"level9\":\"0.00\",\"level8\":\"0.00\",\"level7\":\"0.00\","
//                        + "\"project_type\":\"sluice\",\"level6\":\"0.00\",\"level10\":\"0.00\",\"level5\":\"0.00\",\"level4\":\"0.02\",\"id\":\"ad407c9bd9634d0bac800651287a8c1f\",\"level2\":\"0.01\","
//                        + "\"level3\":\"0.00\",\"time\":\"2018-08-27 17:24:51\",\"level1\":\"0.01\",\"project_id\":null,\"hole\":\"5\"}]";
//                final List<SluiceInfo> list = Utility.handleApi03SluiceMonitorListResponse(hardcodeString);
                final List<SluiceInfo> list = Utility.handleApi03SluiceMonitorListResponse(responseText);
                Log.d("aijun SluiceMonit", list+"");
                if ( null != list ) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            sluiceInfoList.clear();
                            sluiceInfoList.addAll(list);
                            createAreaForSluiceListLayout();
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

    // [{"id":"a2231e58787845c1a11702d7968303f1","time":"2018-08-27 17:24:51","level":"4.39",
    // "project_name":"张庄桥分洪闸上游水位","project_id":null,"project_type":"content_gague"}]
    private void getDataFromServerAndUpdateListView2() {
        String url = Api.API_04_getWaterLevelMonitorList;
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
//                final List<InspectNote> list = Utility.handleApi20patrolResultResponse(responseText);
                Log.d("aijun WaterLevelMo", responseText+"");
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

    // [{"time":"25","rain_data":"3.5"},{"time":"25","rain_data":"0.5"}]
    private void getDataFromServerAndUpdateListView3() {
        String url = Api.API_06_getRainList;
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
//                final List<InspectNote> list = Utility.handleApi20patrolResultResponse(responseText);
                Log.d("aijun RainMonito", responseText+"");
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
        AlertDialog.Builder builder = new AlertDialog.Builder(Level2_4_realtimeMonitor2.this);
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
        Toast.makeText(Level2_4_realtimeMonitor2.this, text, Toast.LENGTH_SHORT).show();
    }

    private void initProjectInfoList() {
        String[] strings = {
                "赵寨主干西闸位" ,
                "赵寨十九支东闸位" ,
                "王林主干闸位" ,
                "豆公主干闸位" ,
        };
        for (String name : strings) {
            sluiceInfoList.add(new SluiceInfo(name));
        }
    }

    private void addListernerForTopToolbar() {
        findViewById(R.id.rain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_4_realtimeMonitor2.this, Level2_4_3_rain2.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.irrigation_schedule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_4_realtimeMonitor2.this, Level2_6_irrigationSchedule2.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addListernerForBottomToolbar() {
        findViewById(R.id.overview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_4_realtimeMonitor2.this, Level2_1_irrigateOverview.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.overview_inspect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_4_realtimeMonitor2.this, Level2_2_projectInspection2.class);
                startActivity(intent);
                finish();
            }
        });
//        findViewById(R.id.monitor).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Level2_1_irrigateOverview.this, Level2_4_realtimeMonitor2.class);
//                startActivity(intent);
//                finish();
//            }
//        });
        findViewById(R.id.overview_appval).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_4_realtimeMonitor2.this, Level2_5_appvalProcess.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.project_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_4_realtimeMonitor2.this, Level2_3_projectInfo.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addListernerForBackButton() {
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_4_realtimeMonitor2.this, Level2_1_irrigateOverview.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
