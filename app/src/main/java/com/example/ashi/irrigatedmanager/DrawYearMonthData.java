package com.example.ashi.irrigatedmanager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by ashi on 7/9/2018.
 */

public class DrawYearMonthData extends View {

    private Paint mPaint;
    private int mCurWidth;            //当前屏幕的宽 pixel
    private int mCurHeight;           //当前屏幕的高 pixel
    private float mDensity;           //当前屏幕的dpi密度的比值. 720*1080(比值为2), 1080*1920(比值为3), 1440*2550(比值为4)

    public DrawYearMonthData(Context context) {
        super(context);
        init();
    }

    private void init() {
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
        mPaint.setTextSize(mDensity * 20);                      //设置当绘制文字的时候的字体大小
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);              //设置当绘制文字时候的字体粗细
//        mPaint.setShadowLayer(mDensity*3, mDensity*10, mDensity*10, Color.RED );      //设置文字的阴影, 参数分别为:每一点像素模糊的半径, x轴偏移的距离, y轴偏移的距离, 阴影的颜色
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //给整个画布设置颜色 或者canvas.drawColor(int RGB);
        canvas.drawRGB(0, 0, 255);     //设置画布的背景 参数为RGB对应的0~255的色值, 还有同名函数,接收为ARGB

        //画空圆
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(mDensity * 100, mDensity * 100, mDensity * 80, mPaint);
        canvas.drawCircle(mDensity * 240, mDensity * 140, mDensity * 40, mPaint);

        mPaint.setTextSize(30 * mDensity);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText("0", mDensity * (100-10) , mDensity * (100+10), mPaint);
        canvas.drawText("0", mDensity * (240-10) , mDensity * (140+10), mPaint);

        mPaint.setTextSize(20 * mDensity);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(mDensity * (240-23) , mDensity * (140+20),  mDensity * (240+23),  mDensity * (140+80), mPaint);
        canvas.drawRect(mDensity * (100-43) , mDensity * (140+20),  mDensity * (100+43),  mDensity * (140+80), mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawText("年度累计", mDensity * (100-40) , mDensity * (100+80), mPaint);
        canvas.drawText("本月", mDensity * (240-20) , mDensity * (140+40), mPaint);

    }

}
