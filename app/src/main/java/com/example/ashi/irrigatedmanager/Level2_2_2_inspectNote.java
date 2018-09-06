package com.example.ashi.irrigatedmanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashi.irrigatedmanager.gson.InspectNote;
import com.example.ashi.irrigatedmanager.gson.PatrolAdpter;
import com.example.ashi.irrigatedmanager.gson.PatrolNote;
import com.example.ashi.irrigatedmanager.util.Api;
import com.example.ashi.irrigatedmanager.util.Global;
import com.example.ashi.irrigatedmanager.util.HttpUtil;
import com.example.ashi.irrigatedmanager.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class Level2_2_2_inspectNote extends AppCompatActivity {

    private LinearLayout query_layout;

    static List<String> itemKeys = new ArrayList<>();
    final static Map<String, String> items = new LinkedHashMap<>();
    static {
        items.put("全部", "");
        items.put("渠道", "channel");
        items.put("水闸", "sluice");
        items.put("涵洞", "culvert");
        items.put("渡槽", "aqueduct");
        items.put("桥梁", "bridge");
        items.put("倒虹吸", "inverted");
        itemKeys.addAll(items.keySet());
    }

    ListView listView1, listView2;
    List<PatrolNote> dataList = new ArrayList<>();

    int typeSelector = 0;
    int start_year = Utility.getThisYear(), start_month = Utility.getThisMonth(), start_day = 1;
    TextView start_text;
    int end_year = Utility.getThisYear(), end_month = Utility.getThisMonth(), end_day = Utility.getCurrentMonthLastDay();;
    TextView end_text;

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_level2_2_2_inspect_note);

        query_layout = (LinearLayout) findViewById(R.id.query_layout);

        spinner = (Spinner) findViewById(R.id.type_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itemKeys);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,items);
//        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,items);
//        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.spinner_array,
//                R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
//        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeSelector = position;
//                Toast.makeText(Level2_2_2_inspectNote.this, itemKeys.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_2_inspectNote.this, Level2_2_projectInspection2.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.patrol_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.start_year = start_year; Global.start_month = start_month; Global.start_day = start_day;
                Global.end_year = end_year; Global.end_month = end_month; Global.end_day = end_day;
                Global.typeSelector = typeSelector;
                Global.search = ((EditText) findViewById(R.id.name)).getText().toString();

                getDataFromServerAndUpdateListView2();
            }
        });

        findViewById(R.id.start_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePicker=new DatePickerDialog(Level2_2_2_inspectNote.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        start_year = year; start_month = monthOfYear + 1; start_day = dayOfMonth;
                        updateDayTextView(start_text, start_year, start_month, start_day);
                    }
                }, start_year, start_month-1, start_day);
                datePicker.show();
            }
        });
        start_text = (TextView) findViewById(R.id.start_date_text);
        updateDayTextView(start_text, start_year, start_month, start_day);

        findViewById(R.id.end_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePicker=new DatePickerDialog(Level2_2_2_inspectNote.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        end_year = year; end_month = monthOfYear + 1; end_day = dayOfMonth;
                        updateDayTextView(end_text, end_year, end_month, end_day);
                    }
                }, end_year, end_month-1, end_day);
                datePicker.show();
            }
        });
        end_text = (TextView) findViewById(R.id.end_date_text);
        updateDayTextView(end_text, end_year, end_month, end_day);

        findViewById(R.id.query_image).setFocusable(true);
        findViewById(R.id.query_image).setFocusableInTouchMode(true);
        findViewById(R.id.query_image).requestFocus();
        findViewById(R.id.query_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != query_layout) {
                    query_layout.setVisibility(View.GONE);
                }
            }
        });

        dataList = new ArrayList<>();
//        dataList.add(new PatrolNote());
//        dataList.add(new PatrolNote("异常"));
//        dataList.add(new PatrolNote());

        listView1 = (ListView) findViewById(R.id.inspect_note_list1);
        PatrolAdpter patrolAdpter = new PatrolAdpter(
                Level2_2_2_inspectNote.this, R.layout.item_inspect_note, dataList);
        listView1.setAdapter(patrolAdpter);

        listView2 = (ListView) findViewById(R.id.inspect_note_list2);
        PatrolAdpter patrolAdpter2 = new PatrolAdpter(
                Level2_2_2_inspectNote.this, R.layout.item_inspect_note, dataList);
        listView2.setAdapter(patrolAdpter2);

//        getDataFromServerAndUpdateListView();
        getDataFromServerAndUpdateListView2();

        restoreSearchValue();
    }

    private void restoreSearchValue() {
        updateDayTextView(start_text, Global.start_year, Global.start_month, Global.start_day);
        updateDayTextView(end_text, Global.end_year, Global.end_month, Global.end_day);
        spinner.setSelection(Global.typeSelector);
        ((EditText) findViewById(R.id.name)).setText(Global.search);
    }

    private void updateDayTextView(TextView textView, int year, int month, int day) {
        textView.setText(Utility.toDayString(year, month, day));
    }

    private void getDataFromServerAndUpdateListView() {
        String url = Api.API_20_patrolResult;
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final List<InspectNote> list = Utility.handleApi20patrolResultResponse(responseText);
                Log.d("aijun patrolResult", responseText+"");
                Log.d("aijun patrolResult", list.size()+"");
//                if ( ! list.isEmpty() ) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if ( null != listView ) {
//                                IrrigationScheduleInfoAdpter adapter = new IrrigationScheduleInfoAdpter(
//                                        Level2_6_irrigationSchedule2.this, R.layout.irrigation_schedule, list);
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

    private void getDataFromServerAndUpdateListView2() {
        // http://www.boze-tech.com/zfh_manager/a/app/patrol/officeUserStatistic?userId=1
        // &office=06b21ce1eaec48e59e2a40025b0991ce&projectType=channel&name=%E6%BB%8F%E9%98%B3%E6%B2%B3%E7%A3%81%E5%8E%BF%E6%AE%B5
        // &startDate=2018-05-16&endDate=2018-05-19
        String url = Api.API_28_officeUserStatistic + "userId=" + Global.user.id  + "&office=06b21ce1eaec48e59e2a40025b0991ce" +
                "&projectType=" + items.get(itemKeys.get(typeSelector)) +
                "&startDate=" + Utility.toDayString(start_year, start_month, start_day) +
                "&endDate=" + Utility.toDayString(end_year, end_month, end_day) +
                "&name=" + ((EditText) findViewById(R.id.name)).getText();
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
                            if ( null != listView1 ) {
                                PatrolAdpter patrolAdpter = new PatrolAdpter(
                                        Level2_2_2_inspectNote.this, R.layout.item_inspect_note, dataList);
                                listView1.setAdapter(patrolAdpter);
                            }
                            if ( null != listView2 ) {;
                                PatrolAdpter patrolAdpter2 = new PatrolAdpter(
                                        Level2_2_2_inspectNote.this, R.layout.item_inspect_note, dataList);
                                listView2.setAdapter(patrolAdpter2);
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
