package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Level2_4_realtimeMonitor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_4_realtime_monitor);


        TextView title = (TextView) findViewById(R.id.level_2_4_title);
        title.setText(Const.LEVEL_2_TITILE_4);

        ImageView backButton = (ImageView) findViewById(R.id.leve1_2_4_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_4_realtimeMonitor.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
