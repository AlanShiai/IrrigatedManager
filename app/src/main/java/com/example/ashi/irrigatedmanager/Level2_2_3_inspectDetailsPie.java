package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.ashi.irrigatedmanager.level2_2_3.DrawColumnAndPie;
import com.example.ashi.irrigatedmanager.level2_2_3.DrawYearMonthData;

public class Level2_2_3_inspectDetailsPie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_2_3_inspect_details_pie);

        LinearLayout ll_body = (LinearLayout) findViewById(R.id.inspect_detail_pie);
        DrawColumnAndPie view = new DrawColumnAndPie(getApplicationContext());
        ll_body.addView(view);

        findViewById(R.id.leve1_2_1_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_3_inspectDetailsPie.this, Level2_2_projectInspection2.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
