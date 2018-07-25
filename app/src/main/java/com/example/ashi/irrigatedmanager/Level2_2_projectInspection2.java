package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Level2_2_projectInspection2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_2_project_inspection2);

        LinearLayout ll_body = (LinearLayout) findViewById(R.id.draw_year_month_data);
        DrawYearMonthData view = new DrawYearMonthData(getApplicationContext());
        ll_body.addView(view);

        addListernerForBottomToolbar();
    }

    private void addListernerForBottomToolbar() {
        findViewById(R.id.details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_projectInspection2.this, Level2_2_3_inspectDetails2.class);
                startActivity(intent);
            }
        });
    }
}
