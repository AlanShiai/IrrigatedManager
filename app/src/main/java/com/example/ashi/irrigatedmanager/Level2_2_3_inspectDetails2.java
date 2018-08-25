package com.example.ashi.irrigatedmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ashi.irrigatedmanager.level2_2_3.InspectDetailInfo;
import com.example.ashi.irrigatedmanager.level2_2_3.InspectDetailInfoAdpter;

import java.util.ArrayList;
import java.util.List;

public class Level2_2_3_inspectDetails2 extends AppCompatActivity {

    private List<InspectDetailInfo> dataList = new ArrayList<InspectDetailInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_level2_2_3_inspect_details2);

        initData();

        InspectDetailInfoAdpter adapter = new InspectDetailInfoAdpter(
                Level2_2_3_inspectDetails2.this, R.layout.inspect_details, dataList);
        ListView listView = (ListView) findViewById(R.id.level_2_2_3_inspect_details);
        listView.setAdapter(adapter);

        findViewById(R.id.leve1_2_1_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_3_inspectDetails2.this, Level2_2_projectInspection2.class);
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
    }

    private void showSingleChoiceDialog() {
        final String[] items = new String[]{"渠首", "闸门", "桥梁",  "渡槽", "涵洞",};
        AlertDialog.Builder builder = new AlertDialog.Builder(Level2_2_3_inspectDetails2.this);
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
        Toast.makeText(Level2_2_3_inspectDetails2.this, text, Toast.LENGTH_SHORT).show();
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
