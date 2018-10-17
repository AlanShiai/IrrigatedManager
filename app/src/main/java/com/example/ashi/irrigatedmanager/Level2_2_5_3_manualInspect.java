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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
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
import com.example.ashi.irrigatedmanager.gson.UploadImage;
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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Level2_2_5_3_manualInspect extends AppCompatActivity {
    public static final int TAKE_PHOTO = 1;

    public static final int CHOOSE_PHOTO = 2;

    private ImageView replacedByChoose;
    private ImageView takePhoto;
    private File takePhotoFile;
    private ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6;
    private HashMap<ImageView, File> images = new LinkedHashMap<>();
    private ImageView imageView1_close, imageView2_close, imageView3_close, imageView4_close, imageView5_close, imageView6_close;
    private HashMap<ImageView, ImageView> imageCloses = new LinkedHashMap<>();

    private ConcurrentHashMap<ImageView, String> uploadedFiles = new ConcurrentHashMap<>();

    private Button manualInspectReportButton;

    private Uri imageUri;

    public LocationClient mLocationClient;

    private EditText editText;

    private TextView positionText;

    private double latitude = 39.87182;
    private double longitude = 116.429489;

    List<PatrolManager> patrolManagers = new ArrayList<>();

    private MapView mapView;

    private BaiduMap baiduMap;

    private boolean isFirstLocate = true;

    private LinearLayout photo_layout, map_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.activity_level2_2_5_3_manual_inspect);

        mapView = (MapView) findViewById(R.id.bmapView);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);

        ImageView located = (ImageView) findViewById(R.id.located);
        located.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photo_layout.setVisibility(View.GONE);
                map_layout.setVisibility(View.VISIBLE);
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

        myFindViewsById();
        setOnClickListeners();
        updatePatrolManagerList();

        initImages();
    }

    private void initImages() {
        images.put(imageView1, null);
        images.put(imageView2, null);
        images.put(imageView3, null);
        images.put(imageView4, null);
        images.put(imageView5, null);
        images.put(imageView6, null);

        imageCloses.put(imageView1, imageView1_close);
        imageCloses.put(imageView2, imageView2_close);
        imageCloses.put(imageView3, imageView3_close);
        imageCloses.put(imageView4, imageView4_close);
        imageCloses.put(imageView5, imageView5_close);
        imageCloses.put(imageView6, imageView6_close);
    }

    private void myFindViewsById() {
        photo_layout = (LinearLayout) findViewById(R.id.photo_layout);
        map_layout = (LinearLayout) findViewById(R.id.map_layout);
        editText = (EditText) findViewById(R.id.editText);
        takePhoto = (ImageView) findViewById(R.id.take_photo);
        imageView1 = (ImageView) findViewById(R.id.imageview1);
        imageView2 = (ImageView) findViewById(R.id.imageview2);
        imageView3 = (ImageView) findViewById(R.id.imageview3);
        imageView4 = (ImageView) findViewById(R.id.imageview4);
        imageView5 = (ImageView) findViewById(R.id.imageview5);
        imageView6 = (ImageView) findViewById(R.id.imageview6);
        imageView1_close = (ImageView) findViewById(R.id.imageview1_close);
        imageView2_close = (ImageView) findViewById(R.id.imageview2_close);
        imageView3_close = (ImageView) findViewById(R.id.imageview3_close);
        imageView4_close = (ImageView) findViewById(R.id.imageview4_close);
        imageView5_close = (ImageView) findViewById(R.id.imageview5_close);
        imageView6_close = (ImageView) findViewById(R.id.imageview6_close);
        manualInspectReportButton = (Button) findViewById(R.id.manual_inspect_report);
    }

    int addImage = R.drawable.d2;
    private void setOnClickListeners() {
        View.OnClickListener imageListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView supportSelectImageFromAlbumView = null;
                for (ImageView imageView : images.keySet()) {
                    if ( null == images.get(imageView) ) {
                        supportSelectImageFromAlbumView = imageView;
                        break;
                    }
                }
                if (supportSelectImageFromAlbumView != (ImageView) v) {
                    return;
                }

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

        View.OnClickListener closeimageListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView closeImage = (ImageView) v;
                ImageView selectImageView = null;
                for (ImageView imageView : imageCloses.keySet()) {
                    if ( closeImage == imageCloses.get(imageView) ) {
                        selectImageView = imageView;
                        break;
                    }
                }
                if (null == selectImageView) {
                    return;
                }

                images.put(selectImageView, null);

                if(uploadedFiles.containsKey(selectImageView)) {
                    uploadedFiles.remove(selectImageView);
                }

                // move image in hashmap
                List<File> files = new ArrayList(images.values());
                List<ImageView>  imageViews = new ArrayList(images.keySet());

                images.put(imageView1, null);
                images.put(imageView2, null);
                images.put(imageView3, null);
                images.put(imageView4, null);
                images.put(imageView5, null);
                images.put(imageView6, null);

                File file = null;
                for (int i = 0, j = 0; i < files.size(); i++ ) {
                    file = files.get(i);
                    if ( null != file) {
                        images.put(imageViews.get(j), file);
                        j++;
                    }
                }

                // set all image view null
                for (ImageView imageView : images.keySet()) {
                    imageView.setImageDrawable(null);
                }

                // update image view
                ImageView addImageView = null;
                for (ImageView imageView : images.keySet()) {
                    if ( null != images.get(imageView)) {
                        Bitmap bitmap = BitmapFactory.decodeFile(images.get(imageView).getAbsolutePath());
                        imageView.setImageBitmap(bitmap);
                        imageCloses.get(imageView).setVisibility(View.VISIBLE);
                    } else {
                        addImageView = imageView;
                        break;
                    }
                }

                // update + image
                if (null != addImageView) {
                    addImageView.setImageResource(R.drawable.d2);
                    imageCloses.get(addImageView).setVisibility(View.INVISIBLE);
                }

            }
        };
        imageView1_close.setOnClickListener(closeimageListener);
        imageView2_close.setOnClickListener(closeimageListener);
        imageView3_close.setOnClickListener(closeimageListener);
        imageView4_close.setOnClickListener(closeimageListener);
        imageView5_close.setOnClickListener(closeimageListener);
        imageView6_close.setOnClickListener(closeimageListener);

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (View.GONE == photo_layout.getVisibility()) {
                    photo_layout.setVisibility(View.VISIBLE);
                    map_layout.setVisibility(View.GONE);
                    return;
                }

                takePhotoFile = new File(getExternalCacheDir(), "output_image" + System.currentTimeMillis()  + ".jpg");
                try {
                    if (takePhotoFile.exists()) {
                        takePhotoFile.delete();
                    }
                    takePhotoFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT < 24) {
                    imageUri = Uri.fromFile(takePhotoFile);
                } else {
                    imageUri = FileProvider.getUriForFile(Level2_2_5_3_manualInspect.this, "com.example.ashi.irrigatedmanager.fileprovider", takePhotoFile);
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
                    if (editText.getText().toString().trim().equals("")) {
                        showNeedDescriptionDialog();
                    } else {
                        showExceptiontDialog();
                    }
                } else {
                    showNormalReportDialog();
                }
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_2_5_3_manualInspect.this, Level2_2_5_2_manualInspect.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showNeedDescriptionDialog() {
        final Dialog builder = new Dialog(Level2_2_5_3_manualInspect.this, R.style.update_dialog);
        View view = View.inflate(Level2_2_5_3_manualInspect.this, R.layout.dialog_info, null);

        final TextView info = (TextView) view.findViewById(R.id.info);
        info.setText("请输入巡检信息。");

        view.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        builder.setContentView(view);
        builder.show();
    }

    private void updatePatrolManagerList() {
        String url = Api.API_32_getUserOfPatrol + "userId=" + Global.user.id + "&flag=1";
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

    private String getPatrolManagerUserId() {
        String managerString = "";
        String managerId = "1";
        if ( null != listView) {
            for (View listChild : listView.getTouchables()) {
                if (listChild instanceof RadioButton) {
                    RadioButton radioButton = (RadioButton) listChild;
                    if (radioButton.isChecked()) {
                        managerString = radioButton.getText().toString();
                        for (PatrolManager patrolManager : patrolManagers) {
                            if (managerString.trim().equals(patrolManager.userName)) {
                                managerId = patrolManager.userId;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return managerId;
    }

    ListView listView;

    private void showExceptiontDialog() {
        final Dialog builder = new Dialog(this, R.style.update_dialog);
        View view = View.inflate(Level2_2_5_3_manualInspect.this, R.layout.update_dialog, null);
        listView = (ListView) view.findViewById(R.id.list_view);
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

            @Override
            public void onClick(View view) {
                commitItWithImage();
                builder.dismiss();
            }
        });
        builder.setContentView(view);//这里还可以指定布局参数
        builder.setCancelable(false);// 不可以用“返回键”取消
        builder.show();
    }

    private void uploadFile(final ImageView selectedView, File file) {
        String url = Api.API_34_uploadImage;
        HttpUtil.uploadFile(url, file, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Log.d("aijun, updateImageFile", responseText);
                responseText = responseText.replace("\\", "/");
                Log.d("aijun, updateImageFile", responseText);
//                responseText = "{\"status\":\"1\",\"paths\":[{\"path\":\"C:/zfh_project/zfh_manager/userfiles/patrolImage/output_image15397365799921539736583662.jpg\"},]}";
//                responseText = "{\"status\":\"1\",\"paths\":[{\"path\":\"C\\:\\zfh_project\\zfh_manager\\userfiles\\patrolImage\\output_image15397365799921539736583662.jpg\"},]}";
                UploadImage uploadImage = Utility.handleApi34uploadImageResponse(responseText);
                if ( null != uploadImage && uploadImage.status.equals("1")) {
                    String image = "";
                    if (null != uploadImage.paths && ! uploadImage.paths.isEmpty()) {
                        image = uploadImage.paths.get(0).path;
                        Log.d("aijun, updateImageFile", image);
                        if ( image.contains("userfiles") ) {
                            image = image.substring(image.indexOf("userfiles") + "userfiles".length());
                        }
                        Log.d("aijun, updateImageFile", image);
                    }
                    uploadedFiles.put(selectedView, image);
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                showText("照片上传失败");
                e.printStackTrace();
            }
        });
    }

    private void commitItWithImage() {
        String images = "";
        boolean isFirst = true;
        for (String image : uploadedFiles.values()) {
            if (isFirst) {
                images = image;
                isFirst = false;
            } else {
                images += "," + image;
            }
        }
        commitItWithImage(images);
    }

//    private void updateImageFile() {
//        List<File> imageFiles = new ArrayList<>();
//        for(File imageFile : images.values()) {
//            if ( null != imageFile && imageFile.exists()) {
//                imageFiles.add(imageFile);
//            }
//        }
//        if ( ! imageFiles.isEmpty() ) {
//            String url = Api.API_34_uploadImage;
//            HttpUtil.uploadFile(url, imageFiles, new Callback() {
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    final String responseText = response.body().string();
//                    Log.d("aijun, updateImageFile", responseText);
//                    UploadImage uploadImage = Utility.handleApi34uploadImageResponse(responseText);
//                    if ( null != uploadImage && uploadImage.status.equals("1")) {
//                        String image = "";
//                        if (null != uploadImage.paths && ! uploadImage.paths.isEmpty()) {
//                            image = uploadImage.paths.get(0).path.substring("/home/zfh/uploads/zfh_manager/userfiles".length());
//                        }
//                        commitItWithImage(image);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    showText("照片上传失败");
//                    e.printStackTrace();
//                }
//            });
//        } else {
//            commitItWithImage("");
//        }
//    }

    private void commitItWithImage(String image) {
        String url = Api.API_22_patrolSave + "userId=" + Global.user.id + "&images=" + image + "&type=" + Global.patrolType
                + "&latitude=" + latitude + "&longitude=" + longitude
                + "&goalId=" + Global.patrolId + "&contents=" + editText.getText().toString().trim()
                + "&itemResults=" + Global.itemResults
                + "&createBy=" + getPatrolManagerUserId();
        Log.d("aijun, patrolSave", url);
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                HttpResult httpResult = Utility.handleNormalFormResponse(responseText);
                Log.d("aijun, patrolSave", responseText);
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showText("巡检提交失败");
                    }
                });
                e.printStackTrace();
            }
        });
    }

    private void showNormalReportDialog() {
        final Dialog builder = new Dialog(Level2_2_5_3_manualInspect.this, R.style.update_dialog);
        View view = View.inflate(Level2_2_5_3_manualInspect.this, R.layout.dialog_info, null);

        final TextView info = (TextView) view.findViewById(R.id.info);
        info.setText("确认没有发现异常。");

        view.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                commitItWithImage();
            }
        });
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });

        builder.setContentView(view);
        builder.show();
    }

    private void showText(String text) {
        Toast.makeText(Level2_2_5_3_manualInspect.this, text, Toast.LENGTH_SHORT).show();
    }

    private void navigateTo(BDLocation location) {
        if (isFirstLocate) {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();
        baiduMap.setMyLocationData(locationData);
    }



    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
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
                        ImageView nullViewOrLastView = null;
                        for (ImageView imageView : images.keySet()) {
                            nullViewOrLastView = imageView;
                            if ( null == images.get(imageView) ) {
                                break;
                            }
                        }
                        addImageFile(nullViewOrLastView, takePhotoFile);

                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        nullViewOrLastView.setImageBitmap(bitmap);
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
            File file = new File(imagePath);
            Log.d("aijun image", imagePath);
            if (file.exists()) {
                Log.d("aijun image", file.getAbsolutePath());
                addImageFile(replacedByChoose, file);
            }
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            replacedByChoose.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    private void addImageFile(ImageView currentImageView, File imageFile) {
        // store imageFile, for upload.
        images.put(currentImageView, imageFile);

        // upload it.
        uploadFile(currentImageView, imageFile);

        // set close image visible
        imageCloses.get(currentImageView).setVisibility(View.VISIBLE);

        // update "+" image
        for (ImageView imageView : images.keySet()) {
            if (images.get(imageView) == null) {
                imageView.setImageResource(R.drawable.d2);
                break;
            }
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
//                    showText(currentPosition.toString());
                    latitude = bdLocation.getLatitude();
                    longitude = bdLocation.getLongitude();
                    mLocationClient.stop();
                }
            });
        }
    }


}