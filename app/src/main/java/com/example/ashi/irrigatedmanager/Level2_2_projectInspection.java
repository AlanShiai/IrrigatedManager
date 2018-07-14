package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ashi.irrigatedmanager.util.Const;

public class Level2_2_projectInspection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_level2_2_project_inspection);

        TextView title = (TextView) findViewById(R.id.level_2_2_title);
        title.setText(Const.LEVEL_2_TITILE_2);

        LinearLayout ll_body = (LinearLayout) findViewById(R.id.draw_year_month_data);
        DrawYearMonthData view = new DrawYearMonthData(getApplicationContext());
        ll_body.addView(view);

        Button leve1_2_2_back = (Button) findViewById(R.id.leve1_2_2_back);
        leve1_2_2_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_projectInspection.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
