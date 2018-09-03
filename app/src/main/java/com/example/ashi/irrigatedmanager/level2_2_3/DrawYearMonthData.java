package com.example.ashi.irrigatedmanager.level2_2_3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.example.ashi.irrigatedmanager.gson.TotalCount;
import com.example.ashi.irrigatedmanager.gson.User;
import com.example.ashi.irrigatedmanager.util.Api;
import com.example.ashi.irrigatedmanager.util.Global;
import com.example.ashi.irrigatedmanager.util.HttpUtil;
import com.example.ashi.irrigatedmanager.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by ashi on 7/9/2018.
 */

public class DrawYearMonthData extends View {

    private Paint mPaint;
    private int mCurWidth;            //当前屏幕的宽 pixel
    private int mCurHeight;           //当前屏幕的高 pixel
    private float mDensity;           //当前屏幕的dpi密度的比值. 720*1080(比值为2), 1080*1920(比值为3), 1440*2550(比值为4)

    private String urlText;

    private Handler handler;

    public DrawYearMonthData(Context context) {
        super(context);
        init();
    }

    private void init() {
        handler=new Handler();

        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);

        //获得屏幕宽高信息
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        mCurHeight = displayMetrics.heightPixels;
        mCurWidth = displayMetrics.widthPixels;
        mDensity = displayMetrics.density;

        //开始设置画笔的基本信息
        mPaint.setAntiAlias(true);                              //设置画笔的抗锯齿
        mPaint.setColor(Color.WHITE);                           //设置画笔的颜色
        mPaint.setStyle(Paint.Style.FILL);                      //设置画出的图形填充的类型,fill为内部填充,stroke为只有边框,内容不填充
        mPaint.setStrokeWidth(mDensity * 2);                    //设置边框的宽度. 如矩形的边宽, 文字的宽度. 接收实参为像素单位
        mPaint.setTextSize(mDensity * 60);                      //设置当绘制文字的时候的字体大小
//        mPaint.setTypeface(Typeface.DEFAULT_BOLD);              //设置当绘制文字时候的字体粗细
//        mPaint.setShadowLayer(mDensity*3, mDensity*10, mDensity*10, Color.RED );      //设置文字的阴影, 参数分别为:每一点像素模糊的半径, x轴偏移的距离, y轴偏移的距离, 阴影的颜色
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //给整个画布设置颜色 或者canvas.drawColor(int RGB);
//        canvas.drawRGB(0x00, 0x3D, 0x79);
        canvas.drawRGB(0xFF, 0xFF, 0xFF);

        int bottom = getBottom(), right = getRight();

        String text;
        if (urlText != null) {
            text = urlText + "次";
        } else {
            text = "1195次";
        }
        Rect rect = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), rect);
        int fontWidth = rect.width();//文字宽
        int fontHeight = rect.height();

        mPaint.setColor(0xFF003D79);
        canvas.drawText(text, right/2 - fontWidth/2, bottom/2 + fontHeight/2, mPaint);

        getTotalCountFromUrl();
    }

    private void getTotalCountFromUrl() {
        String url = Api.API_29_queryTotalCount + "userId=" + Global.userId;
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                Log.d("aijun login", responseText);
                final TotalCount totalCount = Utility.handleApi29TotalCountResponse(responseText);
//                final Weather weather = Utility.handleWeatherResponse(responseText);
                urlText = totalCount.yearTotal;
                handler.post(new Runnable() {
                    public void run() {
                        invalidate();
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
//                urlText = false;
            }
        });
    }
}
