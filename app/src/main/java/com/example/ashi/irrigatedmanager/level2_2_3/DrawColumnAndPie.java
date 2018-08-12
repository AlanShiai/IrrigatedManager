package com.example.ashi.irrigatedmanager.level2_2_3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by AShi on 8/8/2018.
 */
public class DrawColumnAndPie extends View {

    private Paint mPaint;
    private int mCurWidth;            //当前屏幕的宽 pixel
    private int mCurHeight;           //当前屏幕的高 pixel
    private float mDensity;           //当前屏幕的dpi密度的比值. 720*1080(比值为2), 1080*1920(比值为3), 1440*2550(比值为4)

    public DrawColumnAndPie(Context context) {
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
        //mPaint.setTypeface(Typeface.DEFAULT_BOLD);              //设置当绘制文字时候的字体粗细
//        mPaint.setShadowLayer(mDensity*3, mDensity*10, mDensity*10, Color.RED );      //设置文字的阴影, 参数分别为:每一点像素模糊的半径, x轴偏移的距离, y轴偏移的距离, 阴影的颜色
    }

    private int getStringWidth(String str) {
        Rect rect = new Rect();
        mPaint.getTextBounds(str, 0, str.length(), rect);
        int width = rect.width();//文字宽
        return width;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //给整个画布设置颜色 或者canvas.drawColor(int RGB);
//        canvas.drawRGB(0x00, 0x3D, 0x79);
        canvas.drawRGB(0xFF, 0xFF, 0xFF);
        mPaint.setColor(0xFF003D79);

        String text = "0";
        Rect rect = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), rect);
        int fontSize = rect.height();
        int width_0 = getStringWidth("0");
        int width_100 = getStringWidth("100");
        int width_text = getStringWidth("渠首");

        int bottom = getBottom(), right = getRight();

        // draw sparator line.
        canvas.drawLine(0, bottom/2, right, bottom/2, mPaint);

        // draw x-y coordinate
        int x_space = width_100 + 40;
        int y_space = 100;
        canvas.drawLine(0 + x_space, bottom/2 - y_space, right, bottom/2 - y_space, mPaint); // x - axls
        canvas.drawLine(0 + x_space, bottom/2 - y_space, 0 + x_space, 0, mPaint); // y - axls

        // draw x string
        String[] items = new String[] {
                "渠首",
                "闸门",
                "桥梁",
                "渡槽",
                "涵洞",
        };
        float x_grid = (right - x_space) / (items.length * 4);
        canvas.drawText("渠首", 0 + x_space + x_grid + x_grid*0, bottom/2 - y_space + fontSize + 20, mPaint);
        canvas.drawText("闸门", 0 + x_space + x_grid + x_grid*4, bottom/2 - y_space + fontSize + 20, mPaint);
        canvas.drawText("桥梁", 0 + x_space + x_grid + x_grid*8, bottom/2 - y_space + fontSize + 20, mPaint);
        canvas.drawText("渡槽", 0 + x_space + x_grid + x_grid*12, bottom/2 - y_space + fontSize + 20, mPaint);
        canvas.drawText("涵洞", 0 + x_space + x_grid + x_grid*16, bottom/2 - y_space + fontSize + 20, mPaint);

        // y axis string
        float y_grid = (bottom/2 - y_space) / 6;
        canvas.drawText("0",   0 + x_space - width_0 - 20 , bottom/2 - y_space - y_grid * 0, mPaint);
        canvas.drawText("100", 0 + x_space - width_100 - 20, bottom/2 - y_space - y_grid * 1, mPaint);
        canvas.drawText("200", 0 + x_space - width_100 - 20, bottom/2 - y_space - y_grid * 2, mPaint);
        canvas.drawText("300", 0 + x_space - width_100 - 20, bottom/2 - y_space - y_grid * 3, mPaint);
        canvas.drawText("400", 0 + x_space - width_100 - 20, bottom/2 - y_space - y_grid * 4, mPaint);
        canvas.drawText("500", 0 + x_space - width_100 - 20, bottom/2 - y_space - y_grid * 5, mPaint);

        // draw column
        int [] times = new int[] {
                532,
                166,
                433,
                344,
                124,
        };
//        float rectWidth = x_grid*3;
        float rectWidth = width_text;
        float rectHigh = (600-532)/(float)600 * (bottom/2 - y_space);
        float rectX = 0 + x_space + x_grid + x_grid*0;
        mPaint.setColor(Color.RED);
        canvas.drawRect(rectX, bottom/2 - y_space - 2, rectX+rectWidth, rectHigh, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawText("532", rectX + 10, rectHigh + fontSize + 20, mPaint);

        rectX = 0 + x_space + x_grid + x_grid*4;
        rectHigh = (600-166)/(float)600 * (bottom/2 - y_space);
        mPaint.setColor(Color.MAGENTA);
        canvas.drawRect(rectX, bottom/2 - y_space - 2, rectX+rectWidth, rectHigh, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawText("166", rectX + 10, rectHigh + fontSize + 20, mPaint);

        rectX = 0 + x_space + x_grid + x_grid*8;
        rectHigh = (600-433)/(float)600 * (bottom/2 - y_space);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(rectX, bottom/2 - y_space - 2, rectX+rectWidth, rectHigh, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawText("433", rectX + 10, rectHigh + fontSize + 20, mPaint);

        rectX = 0 + x_space + x_grid + x_grid*12;
        rectHigh = (600-344)/(float)600 * (bottom/2 - y_space);
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(rectX, bottom/2 - y_space - 2, rectX+rectWidth, rectHigh, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawText("344", rectX + 10, rectHigh + fontSize + 20, mPaint);

        rectX = 0 + x_space + x_grid + x_grid*16;
        rectHigh = (600-124)/(float)600 * (bottom/2 - y_space);
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(rectX, bottom/2 - y_space - 2, rectX+rectWidth, rectHigh, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawText("124", rectX + 10, rectHigh + fontSize + 20, mPaint);

        // draw pie
        int min = Math.min(bottom/2, right);
        int width;
        RectF pieRectF;
        if (min == bottom/2) {
            width = bottom/2 - 80;
            pieRectF = new RectF((right-width)/2, bottom/2 + 40, right - (right-width)/2, bottom - 40);
        } else {
            width = right - 80;
            pieRectF = new RectF(40, bottom/2 + (bottom/2 - width)/2, right - 40, bottom - (bottom/2 - width)/2);
        }
        float mRadius = width/2 - 60;
        float circle_x = right/2, circle_y = bottom/2 + 40 + (bottom/2-80) / 2;
        float sum = 21 + 20 + 9 + 2 + 8;
        float startAngle = 0;
        float sweepAngle = 21/sum * 350;
        mPaint.setColor(Color.RED);
        canvas.drawArc(pieRectF, startAngle, sweepAngle, true, mPaint);
        float pxs = (float) (mRadius*Math.cos(Math.toRadians(startAngle+sweepAngle/2)));
        float pys = (float) (mRadius*Math.sin(Math.toRadians(startAngle+sweepAngle/2)));
        mPaint.setColor(Color.WHITE);
        canvas.drawText("21", circle_x + pxs, circle_y + pys, mPaint);

        startAngle = startAngle + sweepAngle + 2;
        sweepAngle = 20/sum * 350;
        mPaint.setColor(Color.MAGENTA);
        canvas.drawArc(pieRectF, startAngle, sweepAngle, true, mPaint);
        pxs = (float) (mRadius*Math.cos(Math.toRadians(startAngle+sweepAngle/2)));
        pys = (float) (mRadius*Math.sin(Math.toRadians(startAngle+sweepAngle/2)));
        mPaint.setColor(Color.WHITE);
        canvas.drawText("20", circle_x + pxs, circle_y + pys, mPaint);

        startAngle = startAngle + sweepAngle + 2;
        sweepAngle = 9/sum * 350;
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(pieRectF, startAngle, sweepAngle, true, mPaint);
        pxs = (float) (mRadius*Math.cos(Math.toRadians(startAngle+sweepAngle/2)));
        pys = (float) (mRadius*Math.sin(Math.toRadians(startAngle+sweepAngle/2)));
        mPaint.setColor(Color.WHITE);
        canvas.drawText("9", circle_x + pxs, circle_y + pys, mPaint);

        startAngle = startAngle + sweepAngle + 2;
        sweepAngle = 2/sum * 350;
        mPaint.setColor(Color.GREEN);
        canvas.drawArc(pieRectF, startAngle, sweepAngle, true, mPaint);
        pxs = (float) (mRadius*Math.cos(Math.toRadians(startAngle+sweepAngle/2)));
        pys = (float) (mRadius*Math.sin(Math.toRadians(startAngle+sweepAngle/2)));
        mPaint.setColor(Color.WHITE);
        canvas.drawText("2", circle_x + pxs, circle_y + pys, mPaint);

        startAngle = startAngle + sweepAngle + 2;
        sweepAngle = 8/sum * 350;
        mPaint.setColor(Color.BLACK);
        canvas.drawArc(pieRectF, startAngle, sweepAngle, true, mPaint);
        pxs = (float) (mRadius*Math.cos(Math.toRadians(startAngle+sweepAngle/2)));
        pys = (float) (mRadius*Math.sin(Math.toRadians(startAngle+sweepAngle/2)));
        mPaint.setColor(Color.WHITE);
        canvas.drawText("8", circle_x + pxs, circle_y + pys, mPaint);

//        mPaint.setColor(0xFF003D79);
        canvas.drawCircle(right/2, bottom/2 + 40 + (bottom/2-80) / 2, width/2 - 160, mPaint);
//        mPaint.setColor(Color.WHITE);
        mPaint.setColor(0xFF003D79);
        String str = "异常次数";
        int str_width = getStringWidth(str);
        mPaint.setColor(0xFF003D79);
        canvas.drawText("异常次数", right/2 - str_width/2, bottom/2 + 40 + (bottom/2-80) / 2, mPaint);

        //画空圆
        /*
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(18);
        canvas.drawCircle(mDensity * 100, mDensity * 100, mDensity * 80, mPaint);
        canvas.drawCircle(mDensity * 240, mDensity * 140, mDensity * 40, mPaint);

        mPaint.setTextSize(30 * mDensity);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText("0", mDensity * (100-10) , mDensity * (100+10), mPaint);
        canvas.drawText("0", mDensity * (240-10) , mDensity * (140+10), mPaint);

        mPaint.setTextSize(20 * mDensity);
        mPaint.setColor(0xFF003D79);
        canvas.drawRect(mDensity * (240-23) , mDensity * (140+20),  mDensity * (240+23),  mDensity * (140+80), mPaint);
        canvas.drawRect(mDensity * (100-43) , mDensity * (140+20),  mDensity * (100+43),  mDensity * (140+80), mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawText("年度累计", mDensity * (100-40) , mDensity * (100+80), mPaint);
        canvas.drawText("本月xx", mDensity * (240-20) , mDensity * (140+40), mPaint);
        */

    }

}
