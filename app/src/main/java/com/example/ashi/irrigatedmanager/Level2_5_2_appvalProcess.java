package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.example.ashi.irrigatedmanager.gson.PatrolManager;
import com.example.ashi.irrigatedmanager.gson.PatrolManagerAdpter;
import com.example.ashi.irrigatedmanager.level5.AppvalAdapter;
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

public class Level2_5_2_appvalProcess extends AppCompatActivity {

    List<PatrolManager> patrolManagers = new ArrayList<>();

    String flag = "yes";

    ListView listView;

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_level2_5_2_appval_process);

        listView = (ListView) findViewById(R.id.list_view);
        editText = (EditText) findViewById(R.id.editText);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_5_2_appvalProcess.this, Level2_5_1_appvalDetails.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.agree).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = "yes";
                doProcess();
            }
        });
        findViewById(R.id.disagree).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = "no";
                doProcess();
            }
        });

        updatePatrolManagerList();
    }

    private String getPatrolManagerUserId() {
        String managerString = "";
        String managerId = Global.user.id;
        if ( null != listView ) {
            for (View listChild : listView.getTouchables()) {
                if (listChild instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) listChild;
                    if (checkBox.isChecked()) {
                        managerString = checkBox.getText().toString();
                        for (PatrolManager patrolManager : patrolManagers) {
                            if (managerString.trim().equals(patrolManager.userName)) {
                                managerId = patrolManager.userId;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return managerId;
    }

    private void doProcess() {
        // http://www.boze-tech.com/zfh_manager/a/app/actTask/handleProcess?taskName=异常处理
        // &dealType=1&comment=很好&taskId=8539953ad40a4e0ea8ad86f7f944d1a0&taskDefKey=audit2
        // &procInsId=93acd047627d45edad2102a7cf00cc0e&procDefId=patrol_result_deal_new:4:a1e5c2f5962a4db1820ca13aea26f57a
        // &procDefKey=&businessKey=pro_patrol_result_deal:98eaf7ee37354b48b875caf30bbad7a9&userId=xizha&flag=yes
        Log.d("aijun, agreeProcess", Global.appval + "");
        String url = Api.API_15_handleProcess + "taskName=" + Utility.toURLEncoded(Global.appval.taskName)
                + "&dealType=" + "1" + "&comment=" + Utility.toURLEncoded(editText.getText().toString()) + "&taskId=" + Global.appval.taskId
                + "&taskDefKey=" + Global.appval.taskDefKey + "&procInsId=" + Global.appval.processInstanceId
                + "&procDefId=" + Global.appval.procDefId + "&procDefKey=" + ""
                + "&businessKey=" + Global.appval.businessKey + "&userId=" + getPatrolManagerUserId()
                + "&flag=" + flag;
        Log.d("aijun, 15_handleProcess", url);
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                Log.d("aijun, 15_handleProcess", responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Level2_5_2_appvalProcess.this, Level2_5_appvalProcess.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void updatePatrolManagerList() {
        String url = Api.API_32_getUserOfPatrol + "userId=" + Global.user.id;
        Log.d("aijun, PatrolManager", url);
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                List<PatrolManager> list = Utility.handleApi32getUserOfPatrolResponse(responseText);
                if (null != list) {
                    patrolManagers.clear();
                    patrolManagers.addAll(list);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ( null != listView ) {
                                final PatrolManagerAdpter adapter = new PatrolManagerAdpter(
                                        Level2_5_2_appvalProcess.this, R.layout.item_check_box, patrolManagers);
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
