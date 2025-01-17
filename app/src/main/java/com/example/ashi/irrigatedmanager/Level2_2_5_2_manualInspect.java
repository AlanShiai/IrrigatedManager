package com.example.ashi.irrigatedmanager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ashi.irrigatedmanager.gson.InspectNote;
import com.example.ashi.irrigatedmanager.level2_5.ManualInspectBasicInfo;
import com.example.ashi.irrigatedmanager.level2_5.ManualInspectBasicInfoAdapter;
import com.example.ashi.irrigatedmanager.level2_5.ManualInspectItem2;
import com.example.ashi.irrigatedmanager.level2_5.ManualInspectItemAdapter;
import com.example.ashi.irrigatedmanager.level2_5.PatrolItem;
import com.example.ashi.irrigatedmanager.level2_6.ProjectInfo4;
import com.example.ashi.irrigatedmanager.level2_6.ProjectInfo4Adapter;
import com.example.ashi.irrigatedmanager.util.Api;
import com.example.ashi.irrigatedmanager.util.Global;
import com.example.ashi.irrigatedmanager.util.HttpUtil;
import com.example.ashi.irrigatedmanager.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Level2_2_5_2_manualInspect extends AppCompatActivity {

    static List<CheckBox> checkBoxList = new ArrayList<>();

    private static List<ManualInspectItem2> dataList = new ArrayList<ManualInspectItem2>();

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_2_5_2_manual_inspect);

//        initData();

        mSectionsPagerAdapter = new Level2_2_5_2_manualInspect.SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_5_2_manualInspect.this, Level2_2_5_1_manualInspect.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.manual_inspect_report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkIsExceptionFound();
                Intent intent = new Intent(Level2_2_5_2_manualInspect.this, Level2_2_5_3_manualInspect.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void checkIsExceptionFound() {
        Global.isExceptionFound = false;
        for (CheckBox checkBox : checkBoxList) {
            if (checkBox.isChecked()) {
                Log.d("aijun, checked", checkBox.getText().toString());
                PatrolItem patrolItem =  (PatrolItem) checkBox.getTag();
                if (Global.itemResults.trim().equals("")) {
                    Global.itemResults = patrolItem.id;
                } else {
                    Global.itemResults += "," +patrolItem.id;
                }
                Global.isExceptionFound = true;
            }
        }
    }

    private void initData() {
        ManualInspectItem2 item;

        item = new ManualInspectItem2("堤坝");
        item.items.add(new PatrolItem("检查渠道是否被损坏，堤坝是否决堤"));
        dataList.add(item);

        item = new ManualInspectItem2("渠底");
        item.items.add(new PatrolItem("有明显影响输水的水藻、杂草等杂物"));
        item.items.add(new PatrolItem("有向渠道内排放污水、废液，倾倒工业废渣、垃圾等废弃物现象"));
        item.items.add(new PatrolItem("有影响泄洪和输水的建筑物、障碍物等"));
        dataList.add(item);

        item = new ManualInspectItem2("渠身");
        item.items.add(new PatrolItem("土渠有严重雨淋沟、渗透、裂缝、塌陷等缺陷"));
        item.items.add(new PatrolItem("混凝土渠表面不整洁，有严重脱壳、剥落、渗漏等现象"));
        item.items.add(new PatrolItem("浆砌石渠有严重的塌陷，松动，隆起，底部掏空，垫层流失等现象"));
        dataList.add(item);

        item = new ManualInspectItem2("渠堤");
        item.items.add(new PatrolItem("土渠有严重雨淋沟、渗透、裂缝、塌陷等缺陷"));
        item.items.add(new PatrolItem("混凝土渠表面不整洁，有严重脱壳、剥落、渗漏等现象"));
        item.items.add(new PatrolItem("浆砌石渠有严重的塌陷，松动，隆起，底部掏空，垫层流失等现象"));
        dataList.add(item);

        item = new ManualInspectItem2("渠堤");
        item.items.add(new PatrolItem("土渠有严重雨淋沟、渗透、裂缝、塌陷等缺陷"));
        item.items.add(new PatrolItem("混凝土渠表面不整洁，有严重脱壳、剥落、渗漏等现象"));
        item.items.add(new PatrolItem("浆砌石渠有严重的塌陷，松动，隆起，底部掏空，垫层流失等现象"));
        dataList.add(item);

        item = new ManualInspectItem2("渠堤");
        item.items.add(new PatrolItem("土渠有严重雨淋沟、渗透、裂缝、塌陷等缺陷"));
        item.items.add(new PatrolItem("混凝土渠表面不整洁，有严重脱壳、剥落、渗漏等现象"));
        item.items.add(new PatrolItem("浆砌石渠有严重的塌陷，松动，隆起，底部掏空，垫层流失等现象"));
        dataList.add(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment1 extends Fragment {

        public LinearLayout itemResultLayout;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_my_tab, container, false);
            itemResultLayout = (LinearLayout) rootView.findViewById(R.id.tab1_layout);
            itemResultLayout.removeAllViews();

//            int index = 1;
//            for (ManualInspectItem2 item : dataList) {
//                if ( ! item.getItems().isEmpty()) {
//                    View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_base_info,
//                            layout, false);
//                    TextView text = (TextView) view.findViewById(R.id.fragment_base_info);
//                    text.setText(index + ". " +item.getName());
//                    layout.addView(view);
//
//                    for(String subItem : item.getItems()) {
//                        View view2 = LayoutInflater.from(getContext()).inflate(R.layout.fragment_base_info2,
//                                layout, false);
//                        CheckBox checkBox = (CheckBox) view2.findViewById(R.id.checkBox);
//                        checkBoxList.add(checkBox);
//                        checkBox.setText(subItem);
//                        layout.addView(view2);
//                    }
//                }
//                index ++;
//            }

            getDataFromServerAndUpdateListView();

            return rootView;
        }

        private void getDataFromServerAndUpdateListView() {
            // "http://www.boze-tech.com/zfh_manager/a/app/patrol/patrolItem?type=channel";
            String url = Api.API_21_patrolItem + "type=" + Global.patrolType;
            Log.d("aijun patrolItem", url+"");
            HttpUtil.sendOkHttpRequest(url, new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String responseText = response.body().string();
                    final List<PatrolItem> list = Utility.handleApi21patrolItemResponse(responseText);
                    Log.d("aijun patrolItem", responseText+"");
                    Log.d("aijun patrolItem", list.size()+"");

                    if ( null != list ) {
                        dataList = Utility.patrolItemsToManualInspectItems(list);
                        Log.d("aijun patrolItem 2", dataList+"");
                        Log.d("aijun patrolItem 2", dataList.size()+"");
                        if ( null != getActivity() ) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (null != itemResultLayout) {
                                        checkBoxList.clear();
                                        itemResultLayout.removeAllViews();

                                        int index = 1;
                                        for (ManualInspectItem2 item : dataList) {
                                            if (!item.items.isEmpty()) {
                                                View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_base_info,
                                                        itemResultLayout, false);
                                                TextView text = (TextView) view.findViewById(R.id.fragment_base_info);
                                                text.setText(index + ". " + item.name);
                                                itemResultLayout.addView(view);

                                                for (PatrolItem patrolItem : item.items) {
                                                    View view2 = LayoutInflater.from(getContext()).inflate(R.layout.fragment_base_info2,
                                                            itemResultLayout, false);
                                                    CheckBox checkBox = (CheckBox) view2.findViewById(R.id.checkBox);
                                                    checkBoxList.add(checkBox);
                                                    checkBox.setText(patrolItem.contents);
                                                    checkBox.setTag(patrolItem);
                                                    itemResultLayout.addView(view2);
                                                }
                                            }
                                            index++;
                                        }
                                    }
                                }
                            });
                        }
                    }
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static class PlaceholderFragment2 extends Fragment {

        private ListView listView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_listview, container, false);

//            ManualInspectBasicInfoAdapter adapter = new ManualInspectBasicInfoAdapter(
//                    getContext(), R.layout.fragment_listview_item, new ArrayList<String>(ManualInspectBasicInfo.getInfo().keySet()));
            listView = (ListView) rootView.findViewById(R.id.fragment_listview_list);
//            listView.setAdapter(adapter);

//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText("Hello world 2");

            getDataFromServerAndUpdateListView();

            return rootView;
        }

        private void getDataFromServerAndUpdateListView() {
            // "http://www.boze-tech.com/zfh_manager/a/app/patrol/basicInfo?id=8502f69d32304ee6a9aacd99920fdcd7&type=channel&userId=1";
            // Api.API_23_basicInfo = http://www.boze-tech.com/zfh_manager/a/app/patrol/basicInfo?userId=1
            String url = Api.API_23_basicInfo  + "userId=" + Global.user.id + "&id=" + Global.patrolId
                    + "&type="+Global.patrolType;
            Log.d("aijun basicInfo", url+"");
            HttpUtil.sendOkHttpRequest(url, new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String responseText = response.body().string();
//                final List<InspectNote> list = Utility.handleApi20patrolResultResponse(responseText);
                    final ProjectInfo4 projectInfo4 = Utility.handleApi18projectDetailResponse(responseText);
                    Log.d("aijun, projectDetail;", responseText+"");
                    Log.d("aijun, projectDetail;", projectInfo4+"");
                    if ( null != projectInfo4 ) {
                        if ( null != getActivity() ) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Global.patrolDetails = new LinkedHashMap<String, String>();
                                    String key = "", value = "";
                                    List<String> list = Arrays.asList(projectInfo4.var_00, projectInfo4.var_01, projectInfo4.var_02,
                                            projectInfo4.var_03, projectInfo4.var_04, projectInfo4.var_05, projectInfo4.var_06, projectInfo4.var_07,
                                            projectInfo4.var_08, projectInfo4.var_09, projectInfo4.var_10, projectInfo4.var_11, projectInfo4.var_12,
                                            projectInfo4.var_13, projectInfo4.var_14, projectInfo4.var_15, projectInfo4.var_16, projectInfo4.var_17,
                                            projectInfo4.var_18, projectInfo4.var_19, projectInfo4.var_20);
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

                                    if (null != listView) {
                                        ManualInspectBasicInfoAdapter adapter = new ManualInspectBasicInfoAdapter(
                                                getContext(), R.layout.fragment_listview_item, new ArrayList<String>(Global.patrolDetails.keySet()));
                                        listView.setAdapter(adapter);
                                    }
                                }
                            });
                        }
                    }
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }
            });
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new PlaceholderFragment1();
                case 1:
                    return new PlaceholderFragment2();
            }
            return new PlaceholderFragment1();
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "巡检情况";
                case 1:
                    return "基本信息";
            }
            return null;
        }
    }
}
