package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Level2_2_3_inspectDetailsPie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_2_3_inspect_details_pie);


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
