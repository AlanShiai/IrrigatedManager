package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.ashi.irrigatedmanager.level2_4.Rain;
import com.example.ashi.irrigatedmanager.level2_4.RainAdapter;
import com.example.ashi.irrigatedmanager.level2_4.RainDetail;
import com.example.ashi.irrigatedmanager.level2_4.RainDetailAdapter;

import java.util.ArrayList;
import java.util.List;

public class Level2_4_3_rain3 extends AppCompatActivity {

    private List<RainDetail> dataList = new ArrayList<RainDetail>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_level2_4_3_rain3);

        initData();
        RainDetailAdapter adapter = new RainDetailAdapter(
                Level2_4_3_rain3.this, R.layout.rain_detail_item, dataList);
        ListView listView = (ListView) findViewById(R.id.level_2_4_3_rain_list);
        listView.setAdapter(adapter);

        addListernerForBackButton();
    }

    private void addListernerForBackButton() {
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_4_3_rain3.this, Level2_4_3_rain2.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initData() {
        dataList.add(new RainDetail("1", "0", "16", "0"));
        dataList.add(new RainDetail("2", "13.2", "17", "0"));
        dataList.add(new RainDetail("3", "0", "18", "0"));
        dataList.add(new RainDetail("4", "0", "19", "0"));
        dataList.add(new RainDetail("5", "0", "20", "0.6"));
        dataList.add(new RainDetail("6", "0", "21", "0"));
        dataList.add(new RainDetail("7", "0", "22", "0"));
        dataList.add(new RainDetail("8", "0", "23", "0"));
        dataList.add(new RainDetail("9", "0", "24", "0"));
        dataList.add(new RainDetail("10", "0", "25", "0"));
        dataList.add(new RainDetail("11", "0", "26", "0"));
        dataList.add(new RainDetail("12", "0", "27", "0"));
        dataList.add(new RainDetail("13", "0", "28", "0"));
        dataList.add(new RainDetail("14", "0", "29", "0"));
        dataList.add(new RainDetail("15", "0", "30", "0"));
    }
}
