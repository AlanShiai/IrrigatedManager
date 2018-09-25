package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ashi.irrigatedmanager.gson.InspectNoteDetails;
import com.example.ashi.irrigatedmanager.gson.TaskFlow;
import com.example.ashi.irrigatedmanager.gson.TaskFlowAdapter;
import com.example.ashi.irrigatedmanager.level2_5.ManualInspectBasicInfoAdapter;
import com.example.ashi.irrigatedmanager.level5.Appval;
import com.example.ashi.irrigatedmanager.level5.AppvalAdapter;
import com.example.ashi.irrigatedmanager.level5.AppvalDetails;
import com.example.ashi.irrigatedmanager.level5.AppvalDetailsAdapter;
import com.example.ashi.irrigatedmanager.level5.BusinessForm;
import com.example.ashi.irrigatedmanager.level5.MyProcess;
import com.example.ashi.irrigatedmanager.level5.ProcessAdapter;
import com.example.ashi.irrigatedmanager.util.Api;
import com.example.ashi.irrigatedmanager.util.Global;
import com.example.ashi.irrigatedmanager.util.HttpUtil;
import com.example.ashi.irrigatedmanager.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Level2_5_1_appvalDetails extends AppCompatActivity {

    private static List<TaskFlow> dataList = new ArrayList<TaskFlow>();

    private Level2_5_1_appvalDetails.SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_5_1_appval_details);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);




//        initData();
//        TaskFlowAdapter adapter = new TaskFlowAdapter(
//                Level2_5_1_appvalDetails.this, R.layout.appval_item2, dataList);
//        listView.setAdapter(adapter);


        if (Global.lastPageIsTodo) {
            findViewById(R.id.next_step).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.next_step).setVisibility(View.INVISIBLE);
        }

        findViewById(R.id.next_step).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_5_1_appvalDetails.this, Level2_5_2_appvalProcess.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_5_1_appvalDetails.this, Level2_5_appvalProcess.class);
                startActivity(intent);
                finish();
            }
        });

        getDataFromServerAndUpdateListView();
        getDataFromServerAndUpdateListView2();
    }

    private void getDataFromServerAndUpdateListView() {
        // address + "/a/app/actTask/businessForm?businessKey=pro_patrol_result_deal:98eaf7ee37354b48b875caf30bbad7a9";
        String url = Api.API_16_businessForm + "businessKey=" + Global.businessKey;
        Log.d("aijun ", url);
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final BusinessForm businessForm = Utility.handleApi16businessFormResponse(responseText);
                Log.d("aijun, businessForm", responseText+"");
                Log.d("aijun, businessForm", businessForm+"");
                if ( null != businessForm) {
                    Log.d("aijun, businessForm", businessForm.name + "");
                    Log.d("aijun, businessForm", businessForm.workflow + "");
                }
                if ( null != businessForm ) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ( null != appval_details_title) {
                                appval_details_title_str = businessForm.name.replace("@@", "      ");
                                appval_details_title.setText(appval_details_title_str);
                            }
                        }
                    });

                    if ( null != businessForm.workflow) {
                        if ( businessForm.workflow.contains("@@") ) {
                            String id = businessForm.workflow.substring(0, businessForm.workflow.indexOf("@@"));
                            Log.d("aijun, businessForm", id + "");

                            String url = Api.API_24_patrolDetail + "id=" + id + "&flag=0";
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
                                                    if ( null !=  result && null != inspectNoteDetails.detail.result) {
                                                        if (inspectNoteDetails.detail.result.trim().equals("1")) {
                                                            result.setText("异常");
                                                        } else {
                                                            result.setText("正常");
                                                        }
                                                    }
                                                    if ( null !=  userName && null != inspectNoteDetails.detail.userName) {
                                                        userName.setText(inspectNoteDetails.detail.userName);
                                                    }
                                                    if ( null !=  createDate && null != inspectNoteDetails.detail.createDate) {
                                                        createDate.setText(inspectNoteDetails.detail.createDate);
                                                    }
                                                    if ( null !=  longitude && null != inspectNoteDetails.detail.longitude) {
                                                        longitude.setText(inspectNoteDetails.detail.longitude);
                                                    }
                                                    if ( null !=  latitude && null != inspectNoteDetails.detail.latitude) {
                                                        latitude.setText(inspectNoteDetails.detail.latitude);
                                                    }
                                                    if ( null !=  resultItem && null != inspectNoteDetails.detail.resultItem) {
                                                        resultItem.setText(inspectNoteDetails.detail.resultItem);
                                                    }
                                                }
                                                if ( null != inspectNoteDetails.basic) {
                                                    Global.patrolDetails = new LinkedHashMap<String, String>();
                                                    String key = "", value = "";
                                                    List<String> list = Arrays.asList(inspectNoteDetails.basic.var_00, inspectNoteDetails.basic.var_01, inspectNoteDetails.basic.var_02,
                                                            inspectNoteDetails.basic.var_03, inspectNoteDetails.basic.var_04, inspectNoteDetails.basic.var_05, inspectNoteDetails.basic.var_06, inspectNoteDetails.basic.var_07,
                                                            inspectNoteDetails.basic.var_08, inspectNoteDetails.basic.var_09, inspectNoteDetails.basic.var_10, inspectNoteDetails.basic.var_11, inspectNoteDetails.basic.var_12,
                                                            inspectNoteDetails.basic.var_13, inspectNoteDetails.basic.var_14, inspectNoteDetails.basic.var_15, inspectNoteDetails.basic.var_16, inspectNoteDetails.basic.var_17,
                                                            inspectNoteDetails.basic.var_18, inspectNoteDetails.basic.var_19, inspectNoteDetails.basic.var_20);
                                                    for (String str : list) {
                                                        if (null != str && str.contains("@@")) {
                                                            key = str.substring(0, str.indexOf("@@"));
                                                            value = "";
                                                            if (str.length() > str.indexOf("@@") + 2) {
                                                                value = str.substring(str.indexOf("@@") + 2);
                                                            }
                                                            Global.patrolDetails.put(key, value);
                                                        }
                                                    }

                                                    if (Global.patrolDetails.keySet().contains("图片")) {
                                                        String picture = Global.patrolDetails.get("图片");
                                                        Global.patrolDetails.remove("图片");
                                                        if (picture != null && !picture.trim().equals("")) {
                                                            Global.patrolDetails.put("图片", picture);
                                                        }
                                                    }

                                                    if (null != basicInfoListView) {
                                                        ManualInspectBasicInfoAdapter adapter = new ManualInspectBasicInfoAdapter(
                                                                Level2_5_1_appvalDetails.this, R.layout.fragment_listview_item, new ArrayList<String>(Global.patrolDetails.keySet()));
                                                        basicInfoListView.setAdapter(adapter);
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
                }
            }
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void getDataFromServerAndUpdateListView2() {
        // address + "/a/app/actTask/taskFlow?procInsId=93acd047627d45edad2102a7cf00cc0e";
        String url = Api.API_33_taskFlow + "procInsId=" + Global.processInstanceId;
        Log.d("aijun API_33_taskFlow", url);
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
//                final BusinessForm businessForm = Utility.handleApi16businessFormResponse(responseText);
                Log.d("aijun, API_33_taskFlow", responseText+"");
                final List<TaskFlow> list = Utility.handleApi33taskFlowResponse(responseText);
                Log.d("aijun, API_33_taskFlow", list.size()+"");
                if ( null != list ) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (null != appval_details_listView) {
                                dataList.clear();
                                dataList.addAll(list);
                                TaskFlowAdapter adapter = new TaskFlowAdapter(
                                        Level2_5_1_appvalDetails.this, R.layout.appval_item2, dataList);
                                appval_details_listView.setAdapter(adapter);
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

    private void initData() {
        dataList.add(new TaskFlow("系统管理员"));
        dataList.add(new TaskFlow("大名管理所所长"));
    }

    static TextView appval_details_title;
    static String appval_details_title_str;
    static ListView appval_details_listView;
    public static class PlaceholderForTab1 extends Fragment {

        public LinearLayout layout;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tab_appval, container, false);
            appval_details_title = (TextView) rootView.findViewById(R.id.appval_details_title);
            appval_details_listView = (ListView) rootView.findViewById(R.id.level_2_5_1_appval_list);

            if ( null != appval_details_title_str) {
                appval_details_title.setText(appval_details_title_str);
            }
            TaskFlowAdapter adapter = new TaskFlowAdapter(
                    getContext(), R.layout.appval_item2, dataList);
            appval_details_listView.setAdapter(adapter);

            return rootView;
        }
    }

    static TextView result;
    static TextView userName;
    static TextView createDate;
    static TextView longitude;
    static TextView latitude;
    static TextView resultItem;
    public static class PlaceholderForTab2 extends Fragment {

        public LinearLayout layout;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tab_patrol, container, false);
            result = (TextView) rootView.findViewById(R.id.result);
            userName = (TextView) rootView.findViewById(R.id.userName);
            createDate = (TextView) rootView.findViewById(R.id.createDate);
            longitude = (TextView) rootView.findViewById(R.id.longitude);
            latitude = (TextView) rootView.findViewById(R.id.latitude);
            resultItem = (TextView) rootView.findViewById(R.id.resultItem);

            return rootView;
        }
    }

    private static ListView basicInfoListView;
    public static class PlaceholderForTab3 extends Fragment {

//        private ListView listView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_listview, container, false);

//            ManualInspectBasicInfoAdapter adapter = new ManualInspectBasicInfoAdapter(
//                    getContext(), R.layout.fragment_listview_item, new ArrayList<String>(ManualInspectBasicInfo.getInfo().keySet()));
            basicInfoListView = (ListView) rootView.findViewById(R.id.fragment_listview_list);
            if ( null != Global.patrolDetails) {
                ManualInspectBasicInfoAdapter adapter = new ManualInspectBasicInfoAdapter(
                        getContext(), R.layout.fragment_listview_item, new ArrayList<String>(Global.patrolDetails.keySet()));
                basicInfoListView.setAdapter(adapter);
            }

            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new PlaceholderForTab1();
                case 1:
                    return new PlaceholderForTab2();
                case 2:
                    return new PlaceholderForTab3();
            }
            return new PlaceholderForTab1();
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "审批详情";
                case 1:
                    return "巡检详情";
                case 2:
                    return "基本信息";
            }
            return null;
        }
    }

}
