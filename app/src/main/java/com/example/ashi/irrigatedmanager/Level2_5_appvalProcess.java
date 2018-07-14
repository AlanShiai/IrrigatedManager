package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashi.irrigatedmanager.gson.User;
import com.example.ashi.irrigatedmanager.util.Const;
import com.example.ashi.irrigatedmanager.util.HttpUtil;
import com.example.ashi.irrigatedmanager.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Level2_5_appvalProcess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_level2_5_appval_process);


        TextView title = (TextView) findViewById(R.id.level_2_5_title);
        title.setText(Const.LEVEL_2_TITILE_5);

        Button button = (Button) findViewById(R.id.should_do_task_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.boze-tech.com/zfh_manager/a/app/login/loginCheck?loginName=admin&passwd=123";
                HttpUtil.sendOkHttpRequest(url, new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String responseText = response.body().string();
                        Log.d("aijun login", responseText);
                        final User user = Utility.handleLoginResponse(responseText);
                        Log.d("aijun login", Boolean.toString(user.isLoginSuccess()));
//                        final Weather weather = Utility.handleWeatherResponse(responseText);
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Level2_5_appvalProcess.this, "登录失败", Toast.LENGTH_SHORT).show();
//                                swipeRefresh.setRefreshing(false);
                            }
                        });
                    }
                });

            }
        });

        ImageView backButton = (ImageView) findViewById(R.id.leve1_2_5_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_5_appvalProcess.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
