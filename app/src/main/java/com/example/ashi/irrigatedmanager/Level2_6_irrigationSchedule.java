package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Level2_6_irrigationSchedule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_6_irrigation_schedule);


        TextView title = (TextView) findViewById(R.id.level_2_6_title);
        title.setText(Const.LEVEL_2_TITILE_6);

        ImageView backButton = (ImageView) findViewById(R.id.leve1_2_6_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_6_irrigationSchedule.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
