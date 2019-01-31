package com.example.ashi.irrigatedmanager;

import android.app.Dialog;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.bumptech.glide.Glide;
import com.example.ashi.irrigatedmanager.gson.InspectNoteDetails;
import com.example.ashi.irrigatedmanager.gson.PatrolAdpter;
import com.example.ashi.irrigatedmanager.gson.PatrolNote;
import com.example.ashi.irrigatedmanager.level2_5.ManualInspectBasicInfoAdapter;
import com.example.ashi.irrigatedmanager.level2_5.ManualInspectItem2;
import com.example.ashi.irrigatedmanager.level2_5.PatrolItem;
import com.example.ashi.irrigatedmanager.level2_6.ProjectInfo4;
import com.example.ashi.irrigatedmanager.util.Api;
import com.example.ashi.irrigatedmanager.util.DialogSelectItemAdapter;
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

public class Level2_2_2_inspectNoteDetails extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        SDKInitializer.setCoordType(CoordType.GCJ02);
        setContentView(R.layout.activity_level2_2_2_inspect_note_details);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_2_inspectNoteDetails.this, Level2_2_2_inspectNote.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != mapView) {
            mapView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != mapView) {
            mapView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mapView) {
            mapView.onDestroy();
            baiduMap.setMyLocationEnabled(false);
        }
    }



    private static MapView mapView;

    private static BaiduMap baiduMap;


    public static class PlaceholderForTab1 extends Fragment {

        private TextView result;
        private TextView userName;
        private TextView createDate;
        private TextView longitude;
        private TextView latitude;
        private TextView remarks;
        private LinearLayout itemResultLayout;
        private TextView imageview_title;
        private ImageView imageview1, imageview2, imageview3, imageview4, imageview5, imageview6;
        private List<ImageView> imageviews;

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
            remarks = (TextView) rootView.findViewById(R.id.remarks);
            itemResultLayout = (LinearLayout) rootView.findViewById(R.id.tab1_layout);
            itemResultLayout.removeAllViews();

            mapView = (MapView) rootView.findViewById(R.id.bmapView);
            baiduMap = mapView.getMap();
            baiduMap.setMyLocationEnabled(true);

            imageview_title = (TextView) rootView.findViewById(R.id.imageview_title);
            imageview1 = (ImageView) rootView.findViewById(R.id.imageview1);
            imageview2 = (ImageView) rootView.findViewById(R.id.imageview2);
            imageview3 = (ImageView) rootView.findViewById(R.id.imageview3);
            imageview4 = (ImageView) rootView.findViewById(R.id.imageview4);
            imageview5 = (ImageView) rootView.findViewById(R.id.imageview5);
            imageview6 = (ImageView) rootView.findViewById(R.id.imageview6);
            imageviews = new ArrayList<>();
            imageviews.add(imageview1);
            imageviews.add(imageview2);
            imageviews.add(imageview3);
            imageviews.add(imageview4);
            imageviews.add(imageview5);
            imageviews.add(imageview6);

            View.OnClickListener imageViewListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Object tag = v.getTag(R.id.tag_first);
                    if (tag instanceof  String) {
                        String picUrl = (String) tag;
                        final Dialog builder = new Dialog(getContext(), R.style.update_dialog);
                        View view = View.inflate(getContext(), R.layout.dialog_imageview, null);
                        final ImageView imageview = (ImageView) view.findViewById(R.id.imageview);
                        imageview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                builder.dismiss();
                            }
                        });
                        Log.d("aijun picUrl", picUrl);
                        Glide.with(getContext()).load(picUrl).into(imageview);
                        builder.setContentView(view);//这里还可以指定布局参数
                        builder.show();
                    }
                }
            };
            imageview1.setOnClickListener(imageViewListener);
            imageview2.setOnClickListener(imageViewListener);
            imageview3.setOnClickListener(imageViewListener);
            imageview4.setOnClickListener(imageViewListener);
            imageview5.setOnClickListener(imageViewListener);
            imageview6.setOnClickListener(imageViewListener);

//            String picUrl = "http://www.boze-tech.com/zfh_manager/userfiles/patrolImage/output_image15390458446441539045847805.jpg";
//            String picUrl = Api.API_34_userfiles + "/patrolImage/output_image15390458446441539045847805.jpg";
//            Glide.with(getContext()).load(picUrl).into(imageview1);
//            imageview1.setVisibility(View.VISIBLE);

            return rootView;
        }

        @Override
        public void onResume() {
            super.onResume();
            getDataFromServerAndUpdateUI();
        }

        private void getDataFromServerAndUpdateUI() {
            // http://www.boze-tech.com/zfh_manager/a/app/patrol/patrolDetail?id=3e2971fc02764ee89bc54af0f30b55e8
            String url = Api.API_24_patrolDetail + "id=" + Global.inspectNoteId + "&flag=1";
            Log.d("aijun patrolDetail", url);
            HttpUtil.sendOkHttpRequest(url, new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String responseText = response.body().string();
                    final InspectNoteDetails inspectNoteDetails = Utility.handleApi24patrolDetailResponse(responseText);
                    Log.d("aijun patrolDetail", responseText+"");
                    Log.d("aijun patrolDetail", inspectNoteDetails+"");
                    if ( null != inspectNoteDetails ) {

                        if ( null != inspectNoteDetails.detail && null != inspectNoteDetails.detail.images ) {
                            String images = inspectNoteDetails.detail.images.trim();
                            Log.d("aijun images", images+"");
                            if ( ! images.equals("")) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        imageview_title.setVisibility(View.VISIBLE);
                                    }
                                });
                                String[] imageArray = images.split(",");
                                if ( imageArray.length > 0 ) {
                                    for (int i = 0 ; i < imageArray.length; i++ ) {
                                        if ( i == imageviews.size() ) {
                                            break;
                                        }
                                        final ImageView imageView = imageviews.get(i);
                                        Log.d("aijun imageArray", imageArray[i]+"");
//                                    final String picUrl = Api.API_34_userfiles + Utility.toURLEncoded(imageArray[i]);
                                        final String picUrl = Api.API_34_userfiles + imageArray[i];
                                        imageView.setTag(R.id.tag_first, picUrl);
                                        Log.d("aijun picUrl", picUrl);
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Glide.with(getActivity()).load(picUrl).into(imageView);
                                                imageView.setVisibility(View.VISIBLE);
                                            }
                                        });
                                    }
                                }
                            }
                        }

                        if ( null != inspectNoteDetails.detail.type && null != inspectNoteDetails.detail.resultItem ) {
                            // "http://www.boze-tech.com/zfh_manager/a/app/patrol/patrolItem?type=channel";
                            String url = Api.API_21_patrolItem + "type=" + inspectNoteDetails.detail.type;
                            Log.d("aijun patrolItem", url+"");
                            HttpUtil.sendOkHttpRequest(url, new Callback() {
                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    final String responseText = response.body().string();
                                    final List<PatrolItem> list = Utility.handleApi21patrolItemResponse(responseText);
                                    Log.d("aijun patrolItem", responseText + "");
                                    Log.d("aijun patrolItem", list.size() + "");

                                    if ( null != list ) {
                                        final List<ManualInspectItem2> dataList = Utility.patrolItemsToManualInspectItems(list);
                                        Log.d("aijun patrolItem 2", dataList + "");
                                        Log.d("aijun patrolItem 2", dataList.size() + "");
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (null != itemResultLayout) {
                                                    itemResultLayout.removeAllViews();

                                                    int index = 1;
                                                    for (ManualInspectItem2 item : dataList) {
                                                        if (!item.items.isEmpty()) {
                                                            View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_base_info,
                                                                    itemResultLayout, false);
                                                            TextView text = (TextView) view.findViewById(R.id.fragment_base_info);
                                                            text.setText(index + ". " + item.name);
                                                            itemResultLayout.addView(view);

                                                            for (PatrolItem patrolItem : item.items) {
                                                                View view2 = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_base_info2,
                                                                        itemResultLayout, false);
                                                                CheckBox checkBox = (CheckBox) view2.findViewById(R.id.checkBox);
                                                                checkBox.setText(patrolItem.contents);
                                                                checkBox.setEnabled(false);
                                                                if (inspectNoteDetails.detail.resultItem.contains(patrolItem.id)) {
                                                                    checkBox.setChecked(true);
                                                                }
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

                                @Override
                                public void onFailure(Call call, IOException e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                        getActivity().runOnUiThread(new Runnable() {
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
                                    if (null != inspectNoteDetails.detail.latitude && null != inspectNoteDetails.detail.longitude) {
                                        navigateTo(Double.valueOf(inspectNoteDetails.detail.latitude), Double.valueOf(inspectNoteDetails.detail.longitude));
                                    }
                                    if ( null !=  remarks && null != inspectNoteDetails.detail.remarks) {
                                        remarks.setText(inspectNoteDetails.detail.remarks);
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
                                                getContext(), R.layout.fragment_listview_item, new ArrayList<String>(Global.patrolDetails.keySet()));
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

        private void navigateTo(double latitude, double longitude) {
            LatLng ll = new LatLng(latitude, longitude);
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);

            MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
            locationBuilder.latitude(latitude);
            locationBuilder.longitude(longitude);
            MyLocationData locationData = locationBuilder.build();
            baiduMap.setMyLocationData(locationData);
        }

    }

    private static ListView basicInfoListView;
    public static class PlaceholderForTab2 extends Fragment {

//        private ListView listView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_listview, container, false);

//            ManualInspectBasicInfoAdapter adapter = new ManualInspectBasicInfoAdapter(
//                    getContext(), R.layout.fragment_listview_item, new ArrayList<String>(ManualInspectBasicInfo.getInfo().keySet()));
            basicInfoListView = (ListView) rootView.findViewById(R.id.fragment_listview_list);

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
                    return new PlaceholderForTab1();
                case 1:
                    return new PlaceholderForTab2();
            }
            return new PlaceholderForTab1();
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
                    return "巡检详情";
                case 1:
                    return "基本信息";
            }
            return null;
        }
    }

}
