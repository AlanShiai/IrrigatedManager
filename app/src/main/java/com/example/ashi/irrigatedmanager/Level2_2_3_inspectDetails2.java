package com.example.ashi.irrigatedmanager;

import android.app.Dialog;
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

import com.example.ashi.irrigatedmanager.gson.PatrolManagerAdpter;
import com.example.ashi.irrigatedmanager.level2_2_3.InspectDetailInfo;
import com.example.ashi.irrigatedmanager.level2_2_3.InspectDetailInfoAdpter;
import com.example.ashi.irrigatedmanager.util.Api;
import com.example.ashi.irrigatedmanager.util.DialogSelectItemAdapter;
import com.example.ashi.irrigatedmanager.util.Global;
import com.example.ashi.irrigatedmanager.util.HttpUtil;
import com.example.ashi.irrigatedmanager.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    int typeSelector = 0;
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
    int monthSelector = Utility.getThisMonth() - 1;
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
                Global.inspectDetails_projectType =  items.get(itemKeys.get(typeSelector));
                try {
                    Global.inspectDetails_month = Integer.parseInt(months.get(monthKeys.get(monthSelector)));
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

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
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
                typeSelectedDialog();
            }
        });
        findViewById(R.id.month_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthSelectedDialog();
            }
        });

        type_text = (TextView) findViewById(R.id.type_text);
        type_text.setText(itemKeys.get(typeSelector));

        month_text = (TextView) findViewById(R.id.month_text);
        month_text.setText(monthKeys.get(monthSelector));

        getDataFromServerAndUpdateListView();
    }

    private void getDataFromServerAndUpdateListView() {
        // "http://www.boze-tech.com/zfh_manager/a/app/patrol/officeStatistic?endDate=2018-07-12&startDate=2018-07-11&userId="+ Global.userId+"&dayType=&office=";
        String url = Api.API_25_officeStatistic + "userId=" + Global.user.id  + "&office=" + Global.user.officeId
                + "&projectType=" + items.get(itemKeys.get(typeSelector)) +
                "&month=" + months.get(monthKeys.get(monthSelector));
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

    private void typeSelectedDialog() {
        final Dialog builder = new Dialog(this, R.style.update_dialog);
        View view = View.inflate(Level2_2_3_inspectDetails2.this, R.layout.dialog_select, null);
        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
            }
        });
        final ListView dialogListView = (ListView) view.findViewById(R.id.list_view);
        final DialogSelectItemAdapter adapter = new DialogSelectItemAdapter(
                Level2_2_3_inspectDetails2.this, R.layout.dialog_select_item, itemKeys, typeSelector);
        dialogListView.setAdapter(adapter);
        dialogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (typeSelector != position) {
                    typeSelector = position;
                    type_text.setText(itemKeys.get(typeSelector));
                    getDataFromServerAndUpdateListView();
                }
                builder.dismiss();
            }
        });
        builder.setContentView(view);//这里还可以指定布局参数
        builder.show();
    }

    private void monthSelectedDialog() {
        final Dialog builder = new Dialog(this, R.style.update_dialog);
        View view = View.inflate(Level2_2_3_inspectDetails2.this, R.layout.dialog_select, null);
        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
            }
        });
        final ListView dialogListView = (ListView) view.findViewById(R.id.list_view);
        final DialogSelectItemAdapter adapter = new DialogSelectItemAdapter(
                Level2_2_3_inspectDetails2.this, R.layout.dialog_select_item, monthKeys, monthSelector);
        dialogListView.setAdapter(adapter);
        dialogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (monthSelector != position) {
                    monthSelector = position;
                    month_text.setText(monthKeys.get(monthSelector));
                    getDataFromServerAndUpdateListView();
                }
                builder.dismiss();
            }
        });
        builder.setContentView(view);//这里还可以指定布局参数
        builder.show();
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
