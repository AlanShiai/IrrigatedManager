package com.example.ashi.irrigatedmanager;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ashi.irrigatedmanager.level2_5.ManualInspectBasicInfo;
import com.example.ashi.irrigatedmanager.level2_5.ManualInspectBasicInfoAdapter;
import com.example.ashi.irrigatedmanager.level2_5.ManualInspectItem2;
import com.example.ashi.irrigatedmanager.level2_5.ManualInspectItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class Level2_2_5_2_manualInspect extends AppCompatActivity {

    private static List<ManualInspectItem2> dataList = new ArrayList<ManualInspectItem2>();

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_2_5_2_manual_inspect);

        initData();

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new Level2_2_5_2_manualInspect.SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        findViewById(R.id.leve1_2_2_5_2_back).setOnClickListener(new View.OnClickListener() {
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
                Intent intent = new Intent(Level2_2_5_2_manualInspect.this, Level2_2_5_3_manualInspect.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initData() {
        ManualInspectItem2 item;

        item = new ManualInspectItem2("堤坝");
        item.getItems().add("检查渠道是否被损坏，堤坝是否决堤");
        dataList.add(item);

        item = new ManualInspectItem2("渠底");
        item.getItems().add("有明显影响输水的水藻、杂草等杂物");
        item.getItems().add("有向渠道内排放污水、废液，倾倒工业废渣、垃圾等废弃物现象");
        item.getItems().add("有影响泄洪和输水的建筑物、障碍物等");
        dataList.add(item);

        item = new ManualInspectItem2("渠身");
        item.getItems().add("土渠有严重雨淋沟、渗透、裂缝、塌陷等缺陷");
        item.getItems().add("混凝土渠表面不整洁，有严重脱壳、剥落、渗漏等现象");
        item.getItems().add("浆砌石渠有严重的塌陷，松动，隆起，底部掏空，垫层流失等现象");
        dataList.add(item);

        item = new ManualInspectItem2("渠堤");
        item.getItems().add("土渠有严重雨淋沟、渗透、裂缝、塌陷等缺陷");
        item.getItems().add("混凝土渠表面不整洁，有严重脱壳、剥落、渗漏等现象");
        item.getItems().add("浆砌石渠有严重的塌陷，松动，隆起，底部掏空，垫层流失等现象");
        dataList.add(item);

        item = new ManualInspectItem2("渠堤");
        item.getItems().add("土渠有严重雨淋沟、渗透、裂缝、塌陷等缺陷");
        item.getItems().add("混凝土渠表面不整洁，有严重脱壳、剥落、渗漏等现象");
        item.getItems().add("浆砌石渠有严重的塌陷，松动，隆起，底部掏空，垫层流失等现象");
        dataList.add(item);

        item = new ManualInspectItem2("渠堤");
        item.getItems().add("土渠有严重雨淋沟、渗透、裂缝、塌陷等缺陷");
        item.getItems().add("混凝土渠表面不整洁，有严重脱壳、剥落、渗漏等现象");
        item.getItems().add("浆砌石渠有严重的塌陷，松动，隆起，底部掏空，垫层流失等现象");
        dataList.add(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment1 extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_my_tab, container, false);
            LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.tab1_layout);
            layout.removeAllViews();

            int index = 1;
            for (ManualInspectItem2 item : dataList) {
                if ( ! item.getItems().isEmpty()) {
                    View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_base_info,
                            layout, false);
                    TextView text = (TextView) view.findViewById(R.id.fragment_base_info);
                    text.setText(index + ". " +item.getName());
                    layout.addView(view);

                    for(String subItem : item.getItems()) {
                        View view2 = LayoutInflater.from(getContext()).inflate(R.layout.fragment_base_info2,
                                layout, false);
                        CheckBox checkBox = (CheckBox) view2.findViewById(R.id.checkBox);
                        checkBox.setText(subItem);
                        layout.addView(view2);
                    }
                }
                index ++;
            }

            return rootView;
        }
    }

    public static class PlaceholderFragment2 extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_listview, container, false);

            ManualInspectBasicInfoAdapter adapter = new ManualInspectBasicInfoAdapter(
                    getContext(), R.layout.fragment_listview_item, new ArrayList<String>(ManualInspectBasicInfo.getInfo().keySet()));
            ListView listView = (ListView) rootView.findViewById(R.id.fragment_listview_list);
            listView.setAdapter(adapter);

//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText("Hello world 2");
            return rootView;
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
