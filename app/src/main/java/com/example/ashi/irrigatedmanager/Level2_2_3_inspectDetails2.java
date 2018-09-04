package com.example.ashi.irrigatedmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashi.irrigatedmanager.level2_2_3.InspectDetailInfo;
import com.example.ashi.irrigatedmanager.level2_2_3.InspectDetailInfoAdpter;
import com.example.ashi.irrigatedmanager.util.Api;
import com.example.ashi.irrigatedmanager.util.Global;
import com.example.ashi.irrigatedmanager.util.HttpUtil;
import com.example.ashi.irrigatedmanager.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Level2_2_3_inspectDetails2 extends AppCompatActivity {

    private List<InspectDetailInfo> dataList = new ArrayList<InspectDetailInfo>();

    static List<String> itemKeys = new ArrayList<>();
    final static Map<String, String> items = new LinkedHashMap<>();
    static {
        items.put("渠道", "channel");
        items.put("水闸", "sluice");
        items.put("涵洞", "culvert");
        items.put("渡槽", "aqueduct");
        items.put("桥梁", "bridge");
        items.put("倒虹吸", "inverted");
        itemKeys.addAll(items.keySet());
    }
    int oldSelector = 0;
    int newSelector = 0;
    TextView type_text;

    static List<String> monthKeys = new ArrayList<>();
    final static Map<String, String> months = new LinkedHashMap<>();
    static {
        months.put("一月", "01");
        months.put("二月", "02");
        months.put("三月", "03");
        months.put("四月", "04");
        months.put("五月", "05");
        months.put("六月", "06");
        months.put("七月", "07");
        months.put("八月", "08");
        months.put("九月", "09");
        months.put("十月", "10");
        months.put("十一月", "11");
        months.put("十二月", "12");
        monthKeys.addAll(months.keySet());
    }
    int monthOldSelector = Utility.getThisMonth() - 1;
    int monthNewSelector = 0;
    TextView month_text;

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
        setContentView(R.layout.activity_level2_2_3_inspect_details2);

        listView = (ListView) findViewById(R.id.level_2_2_3_inspect_details);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Global.inspectDetails_projectType =  items.get(itemKeys.get(oldSelector));
                try {
                    Global.inspectDetails_month = Integer.parseInt(months.get(monthKeys.get(monthOldSelector)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if ( null != dataList && dataList.size() > position ) {
                    Global.inspectDetails_officeId = dataList.get(position).id;
                }
                Intent intent = new Intent(Level2_2_3_inspectDetails2.this, Level2_2_3_1_inspectDetails.class);
                startActivity(intent);
            }
        });

//        initData();
//        InspectDetailInfoAdpter adapter = new InspectDetailInfoAdpter(
//                Level2_2_3_inspectDetails2.this, R.layout.inspect_details, dataList);
//        listView.setAdapter(adapter);

        findViewById(R.id.leve1_2_1_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_3_inspectDetails2.this, Level2_2_projectInspection2.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.type_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingleChoiceDialog();
            }
        });
        findViewById(R.id.month_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMonthSingleChoiceDialog();
            }
        });

        type_text = (TextView) findViewById(R.id.type_text);
        type_text.setText(itemKeys.get(oldSelector));

        month_text = (TextView) findViewById(R.id.month_text);
        month_text.setText(monthKeys.get(monthOldSelector));

        getDataFromServerAndUpdateListView();
    }

    private void getDataFromServerAndUpdateListView() {
        // "http://www.boze-tech.com/zfh_manager/a/app/patrol/officeStatistic?endDate=2018-07-12&startDate=2018-07-11&userId="+ Global.userId+"&dayType=&office=";
        String url = Api.API_25_officeStatistic + "userId=" + Global.user.id  + "&office=" + Global.user.officeId
                + "&projectType=" + items.get(itemKeys.get(oldSelector)) +
                "&month=" + months.get(monthKeys.get(monthOldSelector));
        Log.d("aijun officeStatistic", url);
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final List<InspectDetailInfo> list = Utility.handleApi25officeStatisticResponse(responseText);
                Log.d("aijun officeStatistic", list.size()+"");
                if ( ! list.isEmpty() ) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ( null != listView ) {
                                dataList.clear();
                                dataList.addAll(list);
                                InspectDetailInfoAdpter adapter = new InspectDetailInfoAdpter(
                                        Level2_2_3_inspectDetails2.this, R.layout.inspect_details, dataList);
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

    private void showSingleChoiceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Level2_2_3_inspectDetails2.this);
        builder.setTitle("选择对话框");
        //千万不要加这句，不然列表显示不出来
//        builder.setMessage("这是一个简单的列表对话框");
//        builder.setIcon(R.mipmap.launcher);
        builder.setSingleChoiceItems(itemKeys.toArray(new String[0]), oldSelector, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newSelector = which;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (oldSelector != newSelector) {
                    oldSelector = newSelector;
                    type_text.setText(itemKeys.get(oldSelector));
                    getDataFromServerAndUpdateListView();
                }
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }

    private void showMonthSingleChoiceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Level2_2_3_inspectDetails2.this);
        builder.setTitle("选择对话框");
        //千万不要加这句，不然列表显示不出来
//        builder.setMessage("这是一个简单的列表对话框");
//        builder.setIcon(R.mipmap.launcher);
        builder.setSingleChoiceItems(monthKeys.toArray(new String[0]), monthOldSelector, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                monthNewSelector = which;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (monthOldSelector != monthNewSelector) {
                    monthOldSelector = monthNewSelector;
                    month_text.setText(monthKeys.get(monthOldSelector));
                    getDataFromServerAndUpdateListView();
                }
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }

    private void showText(String text) {
        Toast.makeText(Level2_2_3_inspectDetails2.this, text, Toast.LENGTH_SHORT).show();
    }

    private void initData() {
        dataList.add(new InspectDetailInfo("西闸管理所", "0/10"));
        dataList.add(new InspectDetailInfo("曲周管理所", "0/0"));
        dataList.add(new InspectDetailInfo("肥乡管理所", "0/0"));
        dataList.add(new InspectDetailInfo("高级渠管理所", "0/0"));
        dataList.add(new InspectDetailInfo("张庄桥管理所", "0/0"));
        dataList.add(new InspectDetailInfo("广平管理所", "0/0"));
        dataList.add(new InspectDetailInfo("西八闸管理所", "0/0"));
        dataList.add(new InspectDetailInfo("成安管理所", "7/1"));
        dataList.add(new InspectDetailInfo("马头管理所", "0/0"));
    }
}
