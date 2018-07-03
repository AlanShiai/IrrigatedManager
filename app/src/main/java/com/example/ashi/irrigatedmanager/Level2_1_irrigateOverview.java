package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Level2_1_irrigateOverview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_1_irrigate_overview);

        TextView title = (TextView) findViewById(R.id.level_2_1_title);
        title.setText(Const.LEVEL_2_TITILE_1);

        ImageView leve1_2_1_back = (ImageView) findViewById(R.id.leve1_2_1_back);
        leve1_2_1_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_1_irrigateOverview.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
