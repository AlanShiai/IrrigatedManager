package com.example.ashi.irrigatedmanager.util;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.support.annotation.DrawableRes;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.net.sip.SipErrorCode.TIME_OUT;

/**
 * Created by ashi on 7/11/2018.
 */

public class HttpUtil {

    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    public static void uploadMultiFile() {
        File file = new File("/data/data/com.example.ashi.irrigatedmanager/files/assets/logo_h.png");
//        File file = new File(fileUri);
        if ( ! file.exists() ) {
            Log.d("aijun upload", "uploadMultiFile() file not exists " + file);
            return;
        }
        Log.d("aijun upload", "uploadMultiFile() start upload " + file);
//        final String url = "http://www.boze-tech.com/zfh_manager/a/app/upload/file1";
        final String url = "http://www.boze-tech.com/zfh_manager/a/app/upload/file";
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

    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10*10000000;   //超时时间
    private static final String CHARSET = "utf-8"; //设置编码
    public static final String SUCCESS="1";
    public static final String FAILURE="0";
//    public static final String RequestURL="http://www.boze-tech.com/zfh_manager/a/app/upload/file1";
    public static final String RequestURL="http://www.boze-tech.com/zfh_manager/a/app/upload/file";
    public static String uploadFile() {
        File file = new File("/data/data/com.example.ashi.irrigatedmanager/files/assets/logo_h.png");
        if ( ! file.exists() ) {
            Log.d("aijun upload", "uploadFile() file not exists " + file);
            return "";
        }
        Log.d("aijun upload", "uploadFile() start upload " + file);

        String  BOUNDARY =  UUID.randomUUID().toString();  //边界标识   随机生成
        String PREFIX = "--" , LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data";   //内容类型
        //StringBuilder stringBuilder = new StringBuilder();
        try {
            Log.d("aijun upload", "RequestURL " + RequestURL);
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true);  //允许输入流
            conn.setDoOutput(true); //允许输出流
            conn.setUseCaches(false);  //不允许使用缓存
            conn.setRequestMethod("POST");  //请求方式
            conn.setRequestProperty("Charset", CHARSET);  //设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            if(file != null) {
                /**
                 * 当文件不为空，把文件包装并且上传
                 */
                OutputStream outputSteam = conn.getOutputStream();
                DataOutputStream dos = new DataOutputStream(outputSteam);
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意：
                 * name里面的值为服务器端需要key   只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的   比如:abc.png
                 */

                sb.append("Content-Disposition: form-data; name=\"img\"; filename=\""+file.getName()+"\""+LINE_END);
                sb.append("Content-Type: application/octet-stream; charset="+CHARSET+LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while((len=is.read(bytes))!=-1)
                {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();
                /**
                 * 获取响应码  200=成功
                 * 当响应成功，获取响应的流
                 */
                int res = conn.getResponseCode();
                Log.e("aijun", "response code:"+res);
                if(res==200) {
                    StringBuilder stringBuilder = new StringBuilder();
					String ret="";
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					while((ret = br.readLine())!= null){
						stringBuilder.append(ret);
					}
                    Log.d("aijun upload", "uploadFile() start upload " + stringBuilder.toString());
                    return SUCCESS;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FAILURE;
    }

}
