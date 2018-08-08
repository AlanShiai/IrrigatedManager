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
        addListernerForBackButton();
    }

    private void addListernerForBottomToolbar() {
        findViewById(R.id.start_inspect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_projectInspection2.this, Level2_2_5_1_manualInspect.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.inspect_note).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_projectInspection2.this, Level2_2_2_inspectNote.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_projectInspection2.this, Level2_2_3_inspectDetails2.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.details_pie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_projectInspection2.this, Level2_2_3_inspectDetailsPie.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addListernerForBackButton() {
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_projectInspection2.this, Level2_1_irrigateOverview.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
