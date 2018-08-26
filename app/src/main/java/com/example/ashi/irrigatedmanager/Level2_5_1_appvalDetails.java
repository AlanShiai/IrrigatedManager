package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.ashi.irrigatedmanager.level5.Appval;
import com.example.ashi.irrigatedmanager.level5.AppvalAdapter;
import com.example.ashi.irrigatedmanager.level5.AppvalDetails;
import com.example.ashi.irrigatedmanager.level5.AppvalDetailsAdapter;
import com.example.ashi.irrigatedmanager.level5.BusinessForm;
import com.example.ashi.irrigatedmanager.level5.MyProcess;
import com.example.ashi.irrigatedmanager.level5.ProcessAdapter;
import com.example.ashi.irrigatedmanager.util.Api;
import com.example.ashi.irrigatedmanager.util.HttpUtil;
import com.example.ashi.irrigatedmanager.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Level2_5_1_appvalDetails extends AppCompatActivity {

    private List<AppvalDetails> dataList = new ArrayList<AppvalDetails>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_5_1_appval_details);

        initData();
        AppvalDetailsAdapter adapter = new AppvalDetailsAdapter(
                Level2_5_1_appvalDetails.this, R.layout.appval_item2, dataList);
        ListView listView = (ListView) findViewById(R.id.level_2_5_1_appval_list);
        listView.setAdapter(adapter);

        findViewById(R.id.leve1_2_5_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_5_1_appvalDetails.this, Level2_5_appvalProcess.class);
                startActivity(intent);
            }
        });

        getDataFromServerAndUpdateListView();
    }

    private void getDataFromServerAndUpdateListView() {
        String url = Api.API_16_businessForm;
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final BusinessForm businessForm = Utility.handleApi16businessFormResponse(responseText);
                Log.d("aijun, businessForm", businessForm+"");
                Log.d("aijun, businessForm", businessForm.workflow+"");
                Log.d("aijun, businessForm", businessForm.渠道名称+"");
//                if ( null != myProcess.data &&  ! myProcess.data.isEmpty() ) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if ( null != toDoListView ) {
//                                ProcessAdapter adapter = new ProcessAdapter(getContext(), R.layout.appval_item, myProcess.data);
//                                toDoListView.setAdapter(adapter);
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

    private void initData() {
        dataList.add(new AppvalDetails("系统管理员"));
        dataList.add(new AppvalDetails("大名管理所所长"));
    }
}
