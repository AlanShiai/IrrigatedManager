package com.example.ashi.irrigatedmanager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashi.irrigatedmanager.gson.Abnormal;
import com.example.ashi.irrigatedmanager.gson.AbnormalAdpter;
import com.example.ashi.irrigatedmanager.gson.ScanObject;
import com.example.ashi.irrigatedmanager.gson.TotalCount;
import com.example.ashi.irrigatedmanager.level2_2_3.DrawYearMonthData;
import com.acker.simplezxing.activity.CaptureActivity;
import com.example.ashi.irrigatedmanager.level2_2_3.InspectDetailInfoAdpter;
import com.example.ashi.irrigatedmanager.util.Api;
import com.example.ashi.irrigatedmanager.util.Global;
import com.example.ashi.irrigatedmanager.util.HttpUtil;
import com.example.ashi.irrigatedmanager.util.Utility;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Level2_2_projectInspection2 extends AppCompatActivity {

    private static final int REQ_CODE_PERMISSION = 0x1111;

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
        setContentView(R.layout.activity_level2_2_project_inspection2);

        listView = (ListView) findViewById(R.id.list_view);

//        LinearLayout ll_body = (LinearLayout) findViewById(R.id.draw_year_month_data);
//        DrawYearMonthData view = new DrawYearMonthData(getApplicationContext());
//        ll_body.addView(view);

        addListernerForTopToolbar();
        addListernerForBackButton();
        addListernerForBottomToolbar();
        getDataFromServerAndUpdateTotalCount();
        getDataFromServerAndUpdateListView();
    }

    private void getDataFromServerAndUpdateListView() {
        String url = Api.API_30_patrolInit;
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final List<Abnormal> list = Utility.handleApi30patrolInitResponse(responseText);
                Log.d("aijun patrolInit", responseText+"");
                if ( null != list ) {
                    Global.abnormalList.clear();
                    Global.abnormalList.addAll(list);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AbnormalAdpter adapter = new AbnormalAdpter(
                                    Level2_2_projectInspection2.this, R.layout.item_patrol, list);
                            listView.setAdapter(adapter);
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

    private void getDataFromServerAndUpdateTotalCount() {
        String url = Api.API_29_queryTotalCount;
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                Log.d("aijun queryTotalCount", responseText);
                final TotalCount totalCount = Utility.handleApi29TotalCountResponse(responseText);
                Log.d("aijun queryTotalCount", totalCount.yearTotal);
                if ( null != totalCount.yearTotal ) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView textView = (TextView) findViewById(R.id.yearTotal);
                            textView.setText(totalCount.yearTotal);
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

    private void showText(String text) {
        Toast.makeText(Level2_2_projectInspection2.this, text, Toast.LENGTH_SHORT).show();
    }

    private void addListernerForTopToolbar() {
        findViewById(R.id.scan_inspect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Scan Activity
                if (ContextCompat.checkSelfPermission(Level2_2_projectInspection2.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // Do not have the permission of camera, request it.
                    ActivityCompat.requestPermissions(Level2_2_projectInspection2.this, new String[]{Manifest.permission.CAMERA}, REQ_CODE_PERMISSION);
                } else {
                    // Have gotten the permission
                    startCaptureActivityForResult();
                }

            }
        });
        findViewById(R.id.start_inspect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_projectInspection2.this, Level2_2_5_1_manualInspect.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.inspect_note).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_projectInspection2.this, Level2_2_2_inspectNote.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_projectInspection2.this, Level2_2_3_inspectDetails2.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.details_pie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_projectInspection2.this, Level2_2_3_inspectDetailsPie.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addListernerForBottomToolbar() {
        findViewById(R.id.overview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_projectInspection2.this, Level2_1_irrigateOverview.class);
                startActivity(intent);
                finish();
            }
        });
//        findViewById(R.id.overview_inspect).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Level2_1_irrigateOverview.this, Level2_2_projectInspection2.class);
//                startActivity(intent);
//                finish();
//            }
//        });
        findViewById(R.id.monitor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_projectInspection2.this, Level2_4_realtimeMonitor2.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.overview_appval).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_projectInspection2.this, Level2_5_appvalProcess.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.project_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_projectInspection2.this, Level2_3_projectInfo.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private void startCaptureActivityForResult() {
        Intent intent = new Intent(Level2_2_projectInspection2.this, CaptureActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(CaptureActivity.KEY_NEED_BEEP, CaptureActivity.VALUE_BEEP);
        bundle.putBoolean(CaptureActivity.KEY_NEED_VIBRATION, CaptureActivity.VALUE_VIBRATION);
        bundle.putBoolean(CaptureActivity.KEY_NEED_EXPOSURE, CaptureActivity.VALUE_NO_EXPOSURE);
        bundle.putByte(CaptureActivity.KEY_FLASHLIGHT_MODE, CaptureActivity.VALUE_FLASHLIGHT_OFF);
        bundle.putByte(CaptureActivity.KEY_ORIENTATION_MODE, CaptureActivity.VALUE_ORIENTATION_AUTO);
        bundle.putBoolean(CaptureActivity.KEY_SCAN_AREA_FULL_SCREEN, CaptureActivity.VALUE_SCAN_AREA_FULL_SCREEN);
        bundle.putBoolean(CaptureActivity.KEY_NEED_SCAN_HINT_TEXT, CaptureActivity.VALUE_SCAN_HINT_TEXT);
        intent.putExtra(CaptureActivity.EXTRA_SETTING_BUNDLE, bundle);
        startActivityForResult(intent, CaptureActivity.REQ_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_CODE_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // User agree the permission
                    startCaptureActivityForResult();
                } else {
                    // User disagree the permission
                    Toast.makeText(this, "You must agree the camera permission request before you use the code scan function", Toast.LENGTH_LONG).show();
                }
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CaptureActivity.REQ_CODE:
                switch (resultCode) {
                    case RESULT_OK:
                        String result = data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT);
                        Log.d("aijun scan", result);
                        if (result.contains("projectType")) {
                            ScanObject scanObject = Utility.handleScanResponse(result);
                            if (scanObject.projectId != null && !scanObject.projectId.trim().equals("")
                                    && scanObject.projectType != null && !scanObject.projectType.trim().equals("")) {
                                Global.patrolId = scanObject.projectId;
                                Global.patrolType = scanObject.projectType;
                                Intent intent = new Intent(Level2_2_projectInspection2.this, Level2_2_5_2_manualInspect.class);
                                startActivity(intent);
                                finish();
                            } else {
                                showText("非巡检内容");
                            }
                        } else {
//                        showText(data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT));
                            showText("非巡检内容");
                        }
                        break;
                    case RESULT_CANCELED:
                        if (data != null) {
                            // for some reason camera is not working correctly
                            showText(data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT));
                        }
                        break;
                }
                break;
        }
    }


    private void addListernerForBackButton() {
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_projectInspection2.this, Level2_1_irrigateOverview.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
