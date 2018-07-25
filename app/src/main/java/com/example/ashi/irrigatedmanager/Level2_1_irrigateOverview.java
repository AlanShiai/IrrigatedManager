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

public class Level2_1_irrigateOverview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
        setContentView(R.layout.activity_level2_1_irrigate_overview);

        TextView title = (TextView) findViewById(R.id.level_2_1_title);
        title.setText(Const.LEVEL_2_TITILE_1);

        TextView content = (TextView) findViewById(R.id.level_2_1_content);
        content.setText(Const.LEVEL_2_CONTENT);

        Button leve1_2_1_back = (Button) findViewById(R.id.leve1_2_1_back);
        leve1_2_1_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_1_irrigateOverview.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
