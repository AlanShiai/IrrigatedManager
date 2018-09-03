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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashi.irrigatedmanager.gson.User;
import com.example.ashi.irrigatedmanager.level2_2_3.InspectDetailInfo;
import com.example.ashi.irrigatedmanager.level2_2_3.InspectDetailInfoAdpter;
import com.example.ashi.irrigatedmanager.level2_4.Rain;
import com.example.ashi.irrigatedmanager.level2_4.RainAdapter;
import com.example.ashi.irrigatedmanager.level2_5.ManualInspectBasicInfo;
import com.example.ashi.irrigatedmanager.level2_5.ManualInspectBasicInfoAdapter;
import com.example.ashi.irrigatedmanager.level5.Appval;
import com.example.ashi.irrigatedmanager.level5.AppvalAdapter;
import com.example.ashi.irrigatedmanager.level5.AppvalHistory;
import com.example.ashi.irrigatedmanager.level5.MyProcess;
import com.example.ashi.irrigatedmanager.level5.ProcessAdapter;
import com.example.ashi.irrigatedmanager.util.Api;
import com.example.ashi.irrigatedmanager.util.Const;
import com.example.ashi.irrigatedmanager.util.Global;
import com.example.ashi.irrigatedmanager.util.HttpUtil;
import com.example.ashi.irrigatedmanager.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Level2_5_appvalProcess extends AppCompatActivity {

    private static List<Appval> dataList = new ArrayList<Appval>();

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    static AppvalHistory appvalHistory;

    static MyProcess myProcess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_level2_5_appval_process);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

//        initData();

        findViewById(R.id.leve1_2_5_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_5_appvalProcess.this, Level2_1_irrigateOverview.class);
                startActivity(intent);
            }
        });

        addListernerForBottomToolbar();
    }

    private void addListernerForBottomToolbar() {
        findViewById(R.id.overview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_5_appvalProcess.this, Level2_1_irrigateOverview.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.overview_inspect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_5_appvalProcess.this, Level2_2_projectInspection2.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.monitor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_5_appvalProcess.this, Level2_4_realtimeMonitor2.class);
                startActivity(intent);
                finish();
            }
        });
//        findViewById(R.id.overview_appval).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Level2_4_realtimeMonitor2.this, Level2_5_appvalProcess.class);
//                startActivity(intent);
//                finish();
//            }
//        });
        findViewById(R.id.project_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_5_appvalProcess.this, Logout.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initData() {
        dataList.add(new Appval("呵呵干渠"));
        dataList.add(new Appval("八支渠节制闸"));
        dataList.add(new Appval("巡查异常处理"));
        dataList.add(new Appval("巡查异常处理"));
        dataList.add(new Appval("巡查异常处理"));
        dataList.add(new Appval("巡查异常处理"));
        dataList.add(new Appval("巡查异常处理"));
        dataList.add(new Appval("巡查异常处理"));
        dataList.add(new Appval("巡查异常处理"));
    }

    public static class PlaceholderFragment1 extends Fragment {

        ListView toDoListView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_listview, container, false);

            AppvalAdapter adapter = new AppvalAdapter(getContext(), R.layout.appval_item, dataList);

            toDoListView = (ListView) rootView.findViewById(R.id.fragment_listview_list);
            toDoListView.setAdapter(adapter);
            toDoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if ( dataList.size() > position ) {
                        Global.businessKey = dataList.get(position).businessKey;
                        Global.processInstanceId = dataList.get(position).processInstanceId;
                        Global.lastPageIsTodo = true;
                        Global.appval = dataList.get(position);
                        Log.d("aijun, Global.appval", Global.appval + "");
                    }
                    Intent intent = new Intent(getContext(), Level2_5_1_appvalDetails.class);
                    startActivity(intent);
                }
            });

            getDataFromServerAndUpdateToDoListView();
            return rootView;
        }

        private void getDataFromServerAndUpdateToDoListView() {
            String url = Api.API_12_todoActList;
            Log.d("aijun todoActList", url);
            HttpUtil.sendOkHttpRequest(url, new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String responseText = response.body().string();
                    final List<Appval> list = Utility.handleApi12TodoActListResponse(responseText);
                    if ( ! list.isEmpty() ) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if ( null != toDoListView ) {
                                    dataList.clear();
                                    dataList.addAll(list);
                                    AppvalAdapter adapter = new AppvalAdapter(getContext(), R.layout.appval_item, dataList);
                                    toDoListView.setAdapter(adapter);
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

    public static class PlaceholderFragment2 extends Fragment {

        ListView toDoListView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_listview, container, false);
            toDoListView = (ListView) rootView.findViewById(R.id.fragment_listview_list);

//            AppvalAdapter adapter = new AppvalAdapter(getContext(), R.layout.appval_item, dataList);
//            toDoListView.setAdapter(adapter);
            toDoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if ( null != appvalHistory.data &&  null != appvalHistory.data &&  appvalHistory.data.size() > position ) {
                        Global.businessKey = appvalHistory.data.get(position).businessKey;
                        Global.processInstanceId = appvalHistory.data.get(position).processInstanceId;
                        Global.lastPageIsTodo = false;
                    }
                    Intent intent = new Intent(getContext(), Level2_5_1_appvalDetails.class);
                    startActivity(intent);
                }
            });

            getDataFromServerAndUpdateToDoListView();
            return rootView;
        }

        private void getDataFromServerAndUpdateToDoListView() {
            String url = Api.API_13_historyActList;
            Log.d("aijun 13_historyActList", url);
            HttpUtil.sendOkHttpRequest(url, new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String responseText = response.body().string();
                    appvalHistory = Utility.handleApi13HisoryActListResponse(responseText);
                    if ( null != appvalHistory.data && ! appvalHistory.data.isEmpty() ) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if ( null != toDoListView ) {
                                    AppvalAdapter adapter = new AppvalAdapter(getContext(), R.layout.appval_item, appvalHistory.data);
                                    toDoListView.setAdapter(adapter);
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


    public static class PlaceholderFragment3 extends Fragment {

        ListView toDoListView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_listview, container, false);

//            AppvalAdapter adapter = new AppvalAdapter(getContext(), R.layout.appval_item, dataList);
//            toDoListView.setAdapter(adapter);

            toDoListView = (ListView) rootView.findViewById(R.id.fragment_listview_list);
            toDoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if ( null != myProcess.data && null != myProcess.data && myProcess.data.size() > position ) {
                        Global.businessKey = myProcess.data.get(position).businessKey;
                        Global.processInstanceId = myProcess.data.get(position).processInstanceId;
                        Global.lastPageIsTodo = false;
                    }
                    Intent intent = new Intent(getContext(), Level2_5_1_appvalDetails.class);
                    startActivity(intent);
                }
            });

            getDataFromServerAndUpdateToDoListView();
            return rootView;
        }

        private void getDataFromServerAndUpdateToDoListView() {
            String url = Api.API_14_getMyProcess;
            Log.d("aijun 14_getMyProcess", url);
            HttpUtil.sendOkHttpRequest(url, new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String responseText = response.body().string();
                    myProcess = Utility.handleApi14getMyProcessResponse(responseText);
                    if ( null != myProcess.data && null != myProcess.data && ! myProcess.data.isEmpty() ) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if ( null != toDoListView ) {
                                    ProcessAdapter adapter = new ProcessAdapter(getContext(), R.layout.appval_item, myProcess.data);
                                    toDoListView.setAdapter(adapter);
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
                case 2:
                    return new PlaceholderFragment3();
            }
            return new PlaceholderFragment1();
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
                    return "待办任务";
                case 1:
                    return "已办任务";
                case 2:
                    return "我发起的";
            }
            return null;
        }
    }
}
