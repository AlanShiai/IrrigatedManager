package com.example.ashi.irrigatedmanager;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.ashi.irrigatedmanager.gson.AbnormalAdpter;
import com.example.ashi.irrigatedmanager.gson.HttpResult;
import com.example.ashi.irrigatedmanager.gson.PatrolManager;
import com.example.ashi.irrigatedmanager.gson.PatrolManagerAdpter;
import com.example.ashi.irrigatedmanager.util.Api;
import com.example.ashi.irrigatedmanager.util.Global;
import com.example.ashi.irrigatedmanager.util.HttpUtil;
import com.example.ashi.irrigatedmanager.util.Utility;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Level2_2_5_3_manualInspect extends AppCompatActivity {
    public static final int TAKE_PHOTO = 1;

    public static final int CHOOSE_PHOTO = 2;

    private ImageView replacedByChoose;

    private ImageView takePhoto;

    private ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6;

    private Button manualInspectReportButton, backButton;

    private Uri imageUri;

    public LocationClient mLocationClient;

    private EditText editText;

    private TextView positionText;

    private double latitude = 116.429489;
    private double longitude = 39.87182;

    List<PatrolManager> patrolManagers = new ArrayList<>();

//    private MapView mapView;

//    private BaiduMap baiduMap;

    private boolean isFirstLocate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_level2_2_5_3_manual_inspect);

//        mapView = (MapView) findViewById(R.id.bmapView);
//        baiduMap = mapView.getMap();
//        baiduMap.setMyLocationEnabled(true);

        positionText = (TextView) findViewById(R.id.position_text_view);


        ImageView located = (ImageView) findViewById(R.id.located);
        located.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("aijun located", "adfasdf tst located.");
                List<String> permissionList = new ArrayList<>();
                if (ContextCompat.checkSelfPermission(Level2_2_5_3_manualInspect.this, Manifest.permission
                        .ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
                }
                if (ContextCompat.checkSelfPermission(Level2_2_5_3_manualInspect.this, Manifest.permission
                        .READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(Manifest.permission.READ_PHONE_STATE);
                }
                if (ContextCompat.checkSelfPermission(Level2_2_5_3_manualInspect.this, Manifest.permission
                        .WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
                if ( ! permissionList.isEmpty() ) {
                    String[] permissions = permissionList.toArray(new String[0]);
                    ActivityCompat.requestPermissions(Level2_2_5_3_manualInspect.this, permissions, 1);
                } else {
                    requestLocation();
                }
            }
        });

        findViewsById();
        setOnClickListeners();
        updatePatrolManagerList();
    }

    private void findViewsById() {
        editText = (EditText) findViewById(R.id.editText);
        takePhoto = (ImageView) findViewById(R.id.take_photo);
        imageView1 = (ImageView) findViewById(R.id.imageview1);
        imageView2 = (ImageView) findViewById(R.id.imageview2);
        imageView3 = (ImageView) findViewById(R.id.imageview3);
        imageView4 = (ImageView) findViewById(R.id.imageview4);
        imageView5 = (ImageView) findViewById(R.id.imageview5);
        imageView6 = (ImageView) findViewById(R.id.imageview6);
        manualInspectReportButton = (Button) findViewById(R.id.manual_inspect_report);
        backButton = (Button) findViewById(R.id.back_button);
    }

    int addImage = R.drawable.d2;
    private void setOnClickListeners() {
        View.OnClickListener imageListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Level2_2_5_3_manualInspect.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Level2_2_5_3_manualInspect.this, new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
                } else {
                    replacedByChoose = (ImageView) v;
                    openAlbum();
                }
            }
        };
        imageView1.setOnClickListener(imageListener);
        imageView2.setOnClickListener(imageListener);
        imageView3.setOnClickListener(imageListener);
        imageView4.setOnClickListener(imageListener);
        imageView5.setOnClickListener(imageListener);
        imageView6.setOnClickListener(imageListener);

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT < 24) {
                    imageUri = Uri.fromFile(outputImage);
                } else {
                    imageUri = FileProvider.getUriForFile(Level2_2_5_3_manualInspect.this, "com.example.ashi.irrigatedmanager.fileprovider", outputImage);
                }
                if (ContextCompat.checkSelfPermission(Level2_2_5_3_manualInspect.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Level2_2_5_3_manualInspect.this, new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    // start take photo
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, TAKE_PHOTO);
                }
            }
        });

        manualInspectReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Global.isExceptionFound) {
                    showExceptiontDialog();
                } else {
                    showNormalReportDialog();
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_5_3_manualInspect.this, Level2_2_5_2_manualInspect.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void updatePatrolManagerList() {
        String url = Api.API_32_getUserOfPatrol;
        Log.d("aijun, PatrolManager", url);
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                List<PatrolManager> list = Utility.handleApi32getUserOfPatrolResponse(responseText);
                if (null != list) {
                    patrolManagers.clear();
                    patrolManagers.addAll(list);
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void showExceptiontDialog() {
        final Dialog builder = new Dialog(this, R.style.update_dialog);
        View view = View.inflate(Level2_2_5_3_manualInspect.this, R.layout.update_dialog, null);
        final ListView listView = (ListView) view.findViewById(R.id.list_view);
        final PatrolManagerAdpter adapter = new PatrolManagerAdpter(
                Level2_2_5_3_manualInspect.this, R.layout.item_check_box, patrolManagers);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View tst = view.findViewById(R.id.user_name);
                Log.d("aijun, tst", tst+"");
            }
        });
        Button noUpdateBtn = (Button) view.findViewById(R.id.alert_no_update_btn);
        Button updateBtn = (Button) view.findViewById(R.id.alert_update_btn);
        noUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //2、如果选择不更新，则直接判断是否是第一次进入
                builder.dismiss();
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {

            private String getPatrolManagerUserId() {
                String managerString = "";
                String managerId = "1";
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
                return managerId;
            }

            @Override
            public void onClick(View view) {
                // http://www.boze-tech.com/zfh_manager/a/app/patrol/patrolSave?type=channel
                // &goalId=8502f69d32304ee6a9aacd99920fdcd7%22%20+%20%22&longitude=116.429489&latitude=39.87182
                // &images=&itemResults=&createBy=1&contents=%E6%98%AF%E6%98%AF%E6%98%AF&userId=1

                String url = Api.API_22_patrolSave + "&type=" + Global.patrolType
                        + "&longitude=" + longitude + "&latitude=" + latitude
                        + "&goalId=" + Global.patrolId + "&contents=" + editText.getText().toString()
                        + "&itemResults=" + Utility.toURLEncoded(Global.exceptionMsg)
//                        + "&createBy=" + 1;
                        + "&createBy=" + getPatrolManagerUserId();

                Log.d("aijun, patrolSave", url);
                HttpUtil.sendOkHttpRequest(url, new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String responseText = response.body().string();
                        HttpResult httpResult = Utility.handleNormalFormResponse(responseText);
                        if (httpResult.isSuccess()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showText("巡检提交成功");
                                    Intent intent = new Intent(getApplicationContext(), Level2_2_projectInspection2.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }
                });
                builder.dismiss();
            }
        });
        builder.setContentView(view);//这里还可以指定布局参数
        builder.setCancelable(false);// 不可以用“返回键”取消
        builder.show();
    }

    private void showNormalReportDialog() {
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(Level2_2_5_3_manualInspect.this);
            builder.setTitle("");
            builder.setIcon(R.drawable.e7);
            builder.setMessage("确认没有发现异常。");

            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String url = Api.API_22_patrolSave + "&type=" + Global.patrolType
                            + "&longitude=" + longitude + "&latitude=" + latitude
                            + "&goalId=" + Global.patrolId + "&contents=" + editText.getText().toString()
                            + "&itemResults=";
                    Log.d("aijun, patrolSave", url);
                    HttpUtil.sendOkHttpRequest(url, new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String responseText = response.body().string();
                            HttpResult httpResult = Utility.handleNormalFormResponse(responseText);
                            if (httpResult.isSuccess()) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showText("巡检提交成功");
                                        Intent intent = new Intent(getApplicationContext(), Level2_2_projectInspection2.class);
                                        startActivity(intent);
                                        finish();
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
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.create().show();
        }

    }

    private void showText(String text) {
        Toast.makeText(Level2_2_5_3_manualInspect.this, text, Toast.LENGTH_SHORT).show();
    }

    private void navigateTo(BDLocation location) {
        if (isFirstLocate) {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
//            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
//            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();
//        baiduMap.setMyLocationData(locationData);
    }



    @Override
    protected void onResume() {
        super.onResume();
//        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
//        mapView.onDestroy();
//        baiduMap.setMyLocationEnabled(false);
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
//        option.scanSpan = 20000;
        mLocationClient.setLocOption(option);
    }


    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    openAlbum();
                    requestLocation();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        // 将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        imageView1.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // 根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            replacedByChoose.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(final BDLocation bdLocation) {
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation
                    || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                navigateTo(bdLocation);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    StringBuilder currentPosition = new StringBuilder();
                    currentPosition.append("纬度：").append(bdLocation.getLatitude()).append("\n");
                    currentPosition.append("经度：").append(bdLocation.getLongitude()).append("\n");
                    currentPosition.append("国家：").append(bdLocation.getCountry()).append("\n");
                    currentPosition.append("省份：").append(bdLocation.getProvince()).append("\n");
                    currentPosition.append("城市：").append(bdLocation.getCity()).append("\n");
                    currentPosition.append("区域：").append(bdLocation.getDistrict()).append("\n");
                    currentPosition.append("街道：").append(bdLocation.getStreet()).append("\n");
//                    currentPosition.append("location method:");
//                    if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
//                        currentPosition.append("GPS");
//                    } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
//                        currentPosition.append("NetWork");
//                    }
                    positionText.setText(currentPosition);
                    showText(currentPosition.toString());
                    latitude = bdLocation.getLatitude();
                    longitude = bdLocation.getLongitude();
                    mLocationClient.stop();
                }
            });
        }
    }


}