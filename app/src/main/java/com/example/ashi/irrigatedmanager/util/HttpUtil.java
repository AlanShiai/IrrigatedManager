package com.example.ashi.irrigatedmanager.util;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.support.annotation.DrawableRes;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ashi on 7/11/2018.
 */

public class HttpUtil {

    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    public static void uploadMultiFile(String fileUri) {
        File file = new File(fileUri);
        if ( ! file.exists() ) {
            Log.d("aijun upload", "uploadMultiFile() file not exists " + file);
            return;
        }
        Log.d("aijun upload", "uploadMultiFile() start upload " + file);
        final String url = "http://www.boze-tech.com/zfh_manager/a/app/upload/file1";
//        final String url = "http://www.boze-tech.com/zfh_manager/a/app/upload/";
//        final String url = "http://www.boze-tech.com/zfh_manager/a/app/upload";
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("filename", "logo_h.png", fileBody)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();


        final okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient  = httpBuilder
                //设置超时
                .connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(500, TimeUnit.SECONDS)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("aijun upload", "uploadMultiFile() e=" + e);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("aijun upload", "uploadMultiFile() response=" + response.body().string());
                Log.i("aijun upload", "uploadMultiFile() response=" + response.toString());
            }
        });
    }

}
