package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ashi.irrigatedmanager.gson.InspectNoteDetails;
import com.example.ashi.irrigatedmanager.gson.PatrolAdpter;
import com.example.ashi.irrigatedmanager.gson.PatrolNote;
import com.example.ashi.irrigatedmanager.util.Api;
import com.example.ashi.irrigatedmanager.util.Global;
import com.example.ashi.irrigatedmanager.util.HttpUtil;
import com.example.ashi.irrigatedmanager.util.Utility;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Level2_2_2_inspectNoteDetails extends AppCompatActivity {

    TextView userName, createDate, name, result, resultItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_level2_2_2_inspect_note_details);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_2_inspectNoteDetails.this, Level2_2_2_inspectNote.class);
                startActivity(intent);
                finish();
            }
        });

        myFindViewById();

        getDataFromServerAndUpdateUI();
    }

    private void myFindViewById() {
        userName = (TextView) findViewById(R.id.userName);
        createDate = (TextView) findViewById(R.id.createDate);
        name = (TextView) findViewById(R.id.name);
        result = (TextView) findViewById(R.id.result);
        resultItem = (TextView) findViewById(R.id.resultItem);
    }

    private void getDataFromServerAndUpdateUI() {
        // http://www.boze-tech.com/zfh_manager/a/app/patrol/patrolDetail?id=3e2971fc02764ee89bc54af0f30b55e8
        String url = Api.API_24_patrolDetail + "id=" + Global.inspectNoteId ;
        Log.d("aijun patrolDetail", url);
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final InspectNoteDetails inspectNoteDetails = Utility.handleApi24patrolDetailResponse(responseText);
                Log.d("aijun patrolDetail", responseText+"");
                Log.d("aijun patrolDetail", inspectNoteDetails+"");
                if ( null != inspectNoteDetails ) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ( null != inspectNoteDetails.detail) {
                                userName.setText(inspectNoteDetails.detail.userName);
                                createDate.setText(inspectNoteDetails.detail.createDate);
                                if ( "1".equals(inspectNoteDetails.detail.result.trim()) ) {
                                    result.setText("异常");
                                } else {
                                    result.setText("正常");
                                }
                                resultItem.setText(inspectNoteDetails.detail.resultItem);
                            }
                            if ( null != inspectNoteDetails.basic) {
                                if( null != inspectNoteDetails.basic.var_01) {
                                    String str = inspectNoteDetails.basic.var_01;
                                    if (str.contains("@@")) {
                                        name.setText(str.substring(str.indexOf("@@") + 2));
                                    }
                                }
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
