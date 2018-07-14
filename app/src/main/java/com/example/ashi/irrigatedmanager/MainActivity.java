package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ashi.irrigatedmanager.util.Const;

public class MainActivity extends AppCompatActivity {

    private TextView leve11Title;

    private ImageView level2_1_irrigatedOverview_image;
    private ImageView level2_2_projectInspection_image;
    private ImageView level2_3_projectInfo_image;
    private ImageView level2_4_realTimeMonitor_image;
    private ImageView level2_5_approvalProcess_image;
    private ImageView level2_6_irrigationSchedule_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_main);

        id2View();
        addViewListening();
    }

    private void addViewListening() {
        level2_1_irrigatedOverview_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Level2_1_irrigateOverview.class);
                startActivity(intent);
            }
        });
        level2_2_projectInspection_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Level2_2_projectInspection.class);
                startActivity(intent);
            }
        });
        level2_3_projectInfo_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Level2_3_projectInfo.class);
                startActivity(intent);
            }
        });
        level2_4_realTimeMonitor_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Level2_4_realtimeMonitor.class);
                startActivity(intent);
            }
        });
        level2_5_approvalProcess_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Level2_5_appvalProcess.class);
                startActivity(intent);
            }
        });
        level2_6_irrigationSchedule_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Level2_6_irrigationSchedule.class);
                startActivity(intent);
            }
        });

    }

    private void id2View() {
        level2_1_irrigatedOverview_image = (ImageView) findViewById(R.id.level2_1_irrigatedOverview_image);
        level2_2_projectInspection_image = (ImageView) findViewById(R.id.level2_2_projectInspection_image);
        level2_3_projectInfo_image = (ImageView) findViewById(R.id.level2_3_projectInfo_image);
        level2_4_realTimeMonitor_image = (ImageView) findViewById(R.id.level2_4_realTimeMonitor_image);
        level2_5_approvalProcess_image = (ImageView) findViewById(R.id.level2_5_approvalProcess_image);
        level2_6_irrigationSchedule_image = (ImageView) findViewById(R.id.level2_6_irrigationSchedule_image);
    }
}
