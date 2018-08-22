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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashi.irrigatedmanager.level2_4.SluiceInfo;
import com.example.ashi.irrigatedmanager.level2_4.SluiceInfoAdapter;

import java.util.ArrayList;
import java.util.List;

public class Level2_4_realtimeMonitor2 extends AppCompatActivity {

    private List<SluiceInfo> projectInfoList = new ArrayList<SluiceInfo>();
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

        initProjectInfoList();
        for (int i = 0 ; i < projectInfoList.size(); i++) {
            enableList.add(false);
        }

        createAreaForSluiceListLayout();



        /*
        ListView listView = (ListView) findViewById(R.id.level_2_4_1_sluice_list);
        SluiceInfoAdapter adapter = new SluiceInfoAdapter(Level2_4_realtimeMonitor2.this, R.layout.sluice_item1, projectInfoList);
        listView.setAdapter(adapter);
        */

        addListernerForBottomToolbar();
        addListernerForBackButton();
        findViewById(R.id.select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingleChoiceDialog();
            }
        });
    }

    private void createAreaForSluiceListLayout() {
        boolean displayDetails = true;
        LinearLayout layout = (LinearLayout) findViewById(R.id.sluice_list_layout);
        layout.removeAllViews();
        int index = 0;
        for (SluiceInfo sluiceInfo: projectInfoList) {
            View view = LayoutInflater.from(Level2_4_realtimeMonitor2.this).inflate(R.layout.sluice_item1,
                    layout, false);
            TextView text = (TextView) view.findViewById(R.id.sluice_name);
            text.setText(sluiceInfo.getName());
            Button button = (Button) view.findViewById(R.id.show_details);
            view.findViewById(R.id.show_details).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout layout = (LinearLayout) v.getParent();
                    TextView text = (TextView) layout.findViewById(R.id.sluice_name);
                    for (int i = 0; i < projectInfoList.size(); i++ ) {
                        if (text.getText().equals(projectInfoList.get(i).getName())) {
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

            if (enableList.get(index)) {
                View view2 = LayoutInflater.from(Level2_4_realtimeMonitor2.this).inflate(R.layout.sluice_item2,
                        layout, false);
                layout.addView(view2);
            }
            index ++;
        }
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
            projectInfoList.add(new SluiceInfo(name));
        }
    }

    private void addListernerForBottomToolbar() {
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
