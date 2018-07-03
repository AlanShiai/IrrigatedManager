package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Level2_3_projectInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_3_project_info);


        TextView title = (TextView) findViewById(R.id.level_2_3_title);
        title.setText(Const.LEVEL_2_TITILE_3);

        ImageView backButton = (ImageView) findViewById(R.id.leve1_2_3_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_3_projectInfo.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
