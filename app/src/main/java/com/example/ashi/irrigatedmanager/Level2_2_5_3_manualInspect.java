package com.example.ashi.irrigatedmanager;

import android.Manifest;
import android.annotation.TargetApi;
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
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.ashi.irrigatedmanager.gson.HttpResult;
import com.example.ashi.irrigatedmanager.util.Api;
import com.example.ashi.irrigatedmanager.util.Global;
import com.example.ashi.irrigatedmanager.util.HttpUtil;
import com.example.ashi.irrigatedmanager.util.Utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Level2_2_5_3_manualInspect extends AppCompatActivity {
    public static final int TAKE_PHOTO = 1;

    public static final int CHOOSE_PHOTO = 2;

    private ImageView picture;

    private Uri imageUri;

    public LocationClient mLocationClient;

    private TextView positionText;

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

        TextView normal_exception_info = (TextView) findViewById(R.id.normal_exception_info);
        normal_exception_info.setText("\n在 民有一干渠成安段 发现异常\n"
                + "堤坝：\n"
                + "\t检查渠道是否被损坏，堤坝是否决堤。\n"
                + "渠底：\n"
                + "\t有明显影响输水的水藻、杂草等杂物。\n"
                + "\t有向渠道内排放污水、废液，倾倒工业废渣、垃圾等废弃物现象。"
        );

        Button takePhoto = (Button) findViewById(R.id.take_photo);
        Button chooseFromAlbum = (Button) findViewById(R.id.choose_from_album);
        picture = (ImageView) findViewById(R.id.picture);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建File对象，用于存储拍照后的图片
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
                // 启动相机程序
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });
        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Level2_2_5_3_manualInspect.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Level2_2_5_3_manualInspect.this, new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
                } else {
                    openAlbum();
                    requestLocation();
                }
            }
        });

        findViewById(R.id.leve1_2_2_5_2_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_5_3_manualInspect.this, Level2_2_5_2_manualInspect.class);
                startActivity(intent);
                finish();
            }
        });

//        mapView = (MapView) findViewById(R.id.bmapView);
//        baiduMap = mapView.getMap();
//        baiduMap.setMyLocationEnabled(true);

        positionText = (TextView) findViewById(R.id.position_text_view);
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

        findViewById(R.id.manual_inspect_report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (Global.isExceptionFound) {
//                    showExceptionReportDialog();
//                } else {
//                    showNormalReportDialog();
//                }
                if (Global.isExceptionFound) {
                    showExceptionReportDialog();
                } else {
                    showNormalReportDialog();
                }
            }
        });
    }

    private void showNormalReportDialogtst() {
        final Dialog builder = new Dialog(this, R.style.update_dialog);
//        View view = LayoutInflater.from(Level2_2_5_3_manualInspect.this).inflate(R.layout.update_dialog, null, false);
        View view = View.inflate(Level2_2_5_3_manualInspect.this, R.layout.update_dialog, null);
//        View view = UIUtil.inflateView(R.layout.update_dialog);//加载自己的布局
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
            @Override
            public void onClick(View view) {
                String url = Api.API_22_patrolSave;
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

    private void showExceptionReportDialog() {
        final String[] items = new String[]{"张元一", "王文",};
        AlertDialog.Builder customizeDialog = new AlertDialog.Builder(Level2_2_5_3_manualInspect.this, android.R.style.Theme_Translucent);
        final View  dialogView = LayoutInflater.from(Level2_2_5_3_manualInspect.this)
                .inflate(R.layout.dialog_customize, null);
//        customizeDialog.setTitle("我是一个自定义Dialog");
        customizeDialog.setCustomTitle(dialogView);
        customizeDialog.setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        customizeDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String url = Api.API_22_patrolSave;
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
        customizeDialog.show();
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
                    String url = Api.API_22_patrolSave;
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
                    openAlbum();
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
                        picture.setImageBitmap(bitmap);
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
            picture.setImageBitmap(bitmap);
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
                    currentPosition.append("weidu:").append(bdLocation.getLatitude()).append("\n");
                    currentPosition.append("jindu:").append(bdLocation.getLongitude()).append("\n");
                    currentPosition.append("Country:").append(bdLocation.getCountry()).append("\n");
                    currentPosition.append("Province:").append(bdLocation.getProvince()).append("\n");
                    currentPosition.append("City:").append(bdLocation.getCity()).append("\n");
                    currentPosition.append("District:").append(bdLocation.getDistrict()).append("\n");
                    currentPosition.append("Street:").append(bdLocation.getStreet()).append("\n");
                    currentPosition.append("location method:");
                    if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
                        currentPosition.append("GPS");
                    } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                        currentPosition.append("NetWork");
                    }
                    positionText.setText(currentPosition);
                }
            });
        }
    }


}