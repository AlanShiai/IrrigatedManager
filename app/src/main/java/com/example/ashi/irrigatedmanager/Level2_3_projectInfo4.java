package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.ashi.irrigatedmanager.level2_5.ManualInspectBasicInfo;
import com.example.ashi.irrigatedmanager.level2_5.ManualInspectBasicInfoAdapter;
import com.example.ashi.irrigatedmanager.level2_6.ProjectInfo3;
import com.example.ashi.irrigatedmanager.level2_6.ProjectInfo3Adpter;
import com.example.ashi.irrigatedmanager.util.Api;
import com.example.ashi.irrigatedmanager.util.HttpUtil;
import com.example.ashi.irrigatedmanager.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Level2_3_projectInfo4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_level2_3_project_info4);

        ManualInspectBasicInfoAdapter adapter = new ManualInspectBasicInfoAdapter(
                Level2_3_projectInfo4.this, R.layout.fragment_listview_item, new ArrayList<String>(ManualInspectBasicInfo.getInfo().keySet()));
        ListView listView = (ListView) findViewById(R.id.project_info_list);
        listView.setAdapter(adapter);

        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_3_projectInfo4.this, Level2_3_projectInfo3.class);
                startActivity(intent);
                finish();
            }
        });

        getDataFromServerAndUpdateListView();
    }

    private void getDataFromServerAndUpdateListView() {
        String url = Api.API_18_projectDetail;
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
//                final List<ProjectInfo3> list = Utility.handleApi17ProjectListResponse(responseText);
                Log.d("aijun, projectDetail;", responseText+"");
//                if ( ! list.isEmpty() ) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if ( null != listView ) {
//                                ProjectInfo3Adpter adapter = new ProjectInfo3Adpter(Level2_3_projectInfo3.this, R.layout.project_item_3, list);
//                                listView.setAdapter(adapter);
//                            }
//                        }
//                    });
//                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

}
