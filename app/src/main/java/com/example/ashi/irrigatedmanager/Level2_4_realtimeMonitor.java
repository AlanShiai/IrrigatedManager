package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ashi.irrigatedmanager.util.Const;

public class Level2_4_realtimeMonitor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_level2_4_realtime_monitor);

        TextView title = (TextView) findViewById(R.id.level_2_4_title);
        title.setText(Const.LEVEL_2_TITILE_4);

        ImageView sluice = (ImageView) findViewById(R.id.level2_4_1_sluice);
        sluice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_4_realtimeMonitor.this, Level2_4_1_sluice.class);
                startActivity(intent);
            }
        });

        Button backButton = (Button) findViewById(R.id.leve1_2_4_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_4_realtimeMonitor.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
