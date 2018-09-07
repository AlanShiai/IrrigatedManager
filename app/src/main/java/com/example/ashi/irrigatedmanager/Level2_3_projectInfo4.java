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
import android.widget.TextView;

import com.example.ashi.irrigatedmanager.level2_5.ManualInspectBasicInfo;
import com.example.ashi.irrigatedmanager.level2_5.ManualInspectBasicInfoAdapter;
import com.example.ashi.irrigatedmanager.level2_6.ProjectInfo3;
import com.example.ashi.irrigatedmanager.level2_6.ProjectInfo3Adpter;
import com.example.ashi.irrigatedmanager.level2_6.ProjectInfo4;
import com.example.ashi.irrigatedmanager.level2_6.ProjectInfo4Adapter;
import com.example.ashi.irrigatedmanager.util.Api;
import com.example.ashi.irrigatedmanager.util.Global;
import com.example.ashi.irrigatedmanager.util.HttpUtil;
import com.example.ashi.irrigatedmanager.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Level2_3_projectInfo4 extends AppCompatActivity {

    ListView listView;

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

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(Global.projectInfoName2);

        listView = (ListView) findViewById(R.id.project_info_list);
//        ManualInspectBasicInfoAdapter adapter = new ManualInspectBasicInfoAdapter(
//                Level2_3_projectInfo4.this, R.layout.fragment_listview_item, new ArrayList<String>(ManualInspectBasicInfo.getInfo().keySet()));
//        listView.setAdapter(adapter);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
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
        // "http://www.boze-tech.com/zfh_manager/a/app/project/projectDetail?userId=1&projectType=channel&id=331d737641434a0bb476265b38d9db1c";
        String url = Api.API_18_projectDetail + "userId=" + Global.user.id + "&projectType=" + Global.projectInfoType
                + "&id=" + Global.projectId;
        Log.d("aijun jectDetail" , url);
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final ProjectInfo4 projectInfo4 = Utility.handleApi18projectDetailResponse(responseText);
                Log.d("aijun, projectDetail;", responseText+"");
                Log.d("aijun, projectDetail;", projectInfo4+"");
                if ( null != projectInfo4 ) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Global.projectDetails = new LinkedHashMap<String, String>();
                            String key = "", value = "";
                            List<String> list = Arrays.asList(projectInfo4.var_00, projectInfo4.var_01, projectInfo4.var_02,
                                    projectInfo4.var_03, projectInfo4.var_04,projectInfo4.var_05, projectInfo4.var_06,projectInfo4.var_07,
                                    projectInfo4.var_08,projectInfo4.var_09,projectInfo4.var_10,projectInfo4.var_11,projectInfo4.var_12,
                                    projectInfo4.var_13,projectInfo4.var_14,projectInfo4.var_15,projectInfo4.var_16,projectInfo4.var_17,
                                    projectInfo4.var_18,projectInfo4.var_19,projectInfo4.var_20);
                            for (String str : list) {
                                if ( null != str && str.contains("@@")) {
                                    key = str.substring(0, str.indexOf("@@"));
                                    if ( Global.projectInfoType.equals("channelHead") && key.equals("建设年代") ) {
                                        continue;
                                    }
                                    value = "";
                                    if (str.length() > str.indexOf("@@")+2) {
                                        value = str.substring(str.indexOf("@@")+2);
                                    }
                                    int index = value.indexOf("-"), lastIndex = value.lastIndexOf("-");
                                    if (index != -1 && lastIndex != -1 && index != lastIndex) {
                                        value = value.substring(0, lastIndex);
                                    }
                                    if (value.contains("&mdash;")) {
                                        value =  value.replace("&mdash;", "--");
                                    }

                                    Global.projectDetails.put(key,value);
                                }
                            }

                            if (Global.projectDetails.keySet().contains("图片")) {
                                String picture = Global.projectDetails.get("图片");
                                Global.projectDetails.remove("图片");
                                if (picture != null && !picture.trim().equals("")) {
                                    Global.projectDetails.put("图片", picture);
                                }
                            }

                            if (null != listView) {
                                ProjectInfo4Adapter adapter = new ProjectInfo4Adapter(
                                        Level2_3_projectInfo4.this, R.layout.fragment_listview_item, new ArrayList<String>(Global.projectDetails.keySet()));
                                listView.setAdapter(adapter);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

}
