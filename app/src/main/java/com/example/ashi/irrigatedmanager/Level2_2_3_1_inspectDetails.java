package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.ashi.irrigatedmanager.gson.PatrolAdpter;
import com.example.ashi.irrigatedmanager.gson.PatrolNote;
import com.example.ashi.irrigatedmanager.util.Api;
import com.example.ashi.irrigatedmanager.util.Global;
import com.example.ashi.irrigatedmanager.util.HttpUtil;
import com.example.ashi.irrigatedmanager.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Level2_2_3_1_inspectDetails extends AppCompatActivity {

    List<PatrolNote> dataList = new ArrayList<>();

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
        setContentView(R.layout.activity_level2_2_3_1_inspect_details);

        listView = (ListView) findViewById(R.id.list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if ( null != dataList && dataList.size() > position ) {
                    Global.inspectNoteId = dataList.get(position).id;
                }
                Intent intent = new Intent(Level2_2_3_1_inspectDetails.this, Level2_2_3_2_inspectDetails.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_3_1_inspectDetails.this, Level2_2_3_inspectDetails2.class);
                startActivity(intent);
                finish();
            }
        });

        getDataFromServerAndUpdateListView2();
    }

    private void getDataFromServerAndUpdateListView2() {
        // http://www.boze-tech.com/zfh_manager/a/app/patrol/officeUserStatistic?userId=1
        // &office=06b21ce1eaec48e59e2a40025b0991ce&projectType=channel&name=%E6%BB%8F%E9%98%B3%E6%B2%B3%E7%A3%81%E5%8E%BF%E6%AE%B5
        // &startDate=2018-05-16&endDate=2018-05-19
        String url = Api.API_28_officeUserStatistic + "userId=" + Global.user.id  +
                "&office=" + Global.user.officeId +
                "&projectType=" + Global.inspectDetails_projectType +
                "&startDate=" + Utility.toDayString(Utility.getThisYear(), Global.inspectDetails_month, 1) +
                "&endDate=" + Utility.toDayString(Utility.getThisYear(), Global.inspectDetails_month, Utility.getMonthLastDay(Utility.getThisYear(), Global.inspectDetails_month)) +
                "&name=";
        Log.d("aijun officeUserStatic", url);
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final List<PatrolNote> list = Utility.handleApi28officeUserStatisticResponse(responseText);
                Log.d("aijun officeUserStistic", responseText+"");
                if ( null != list ) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dataList.clear();
                            dataList.addAll(list);
                            if ( null != listView ) {
                                PatrolAdpter patrolAdpter = new PatrolAdpter(
                                        Level2_2_3_1_inspectDetails.this, R.layout.item_inspect_note, dataList);
                                listView.setAdapter(patrolAdpter);
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
