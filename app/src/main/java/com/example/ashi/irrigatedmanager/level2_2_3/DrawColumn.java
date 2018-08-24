package com.example.ashi.irrigatedmanager.level2_2_3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by ashi on 8/24/2018.
 */

public class DrawColumn extends View {

    private Paint mPaint;
    private int mCurWidth;            //当前屏幕的宽 pixel
    private int mCurHeight;           //当前屏幕的高 pixel
    private float mDensity;           //当前屏幕的dpi密度的比值. 720*1080(比值为2), 1080*1920(比值为3), 1440*2550(比值为4)

    private int touched_x = 0, touched_y = 0;
    private Rect canalHeadRect, sluiceGateRect, bridgeRect, launderRect, culvertRect;

    public DrawColumn(Context context) {
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
        mPaint.setStrokeWidth(mDensity * 1);                    //设置边框的宽度. 如矩形的边宽, 文字的宽度. 接收实参为像素单位
        mPaint.setTextSize(mDensity * 12);                      //设置当绘制文字的时候的字体大小
        //mPaint.setTypeface(Typeface.DEFAULT_BOLD);              //设置当绘制文字时候的字体粗细
//        mPaint.setShadowLayer(mDensity*3, mDensity*10, mDensity*10, Color.RED );      //设置文字的阴影, 参数分别为:每一点像素模糊的半径, x轴偏移的距离, y轴偏移的距离, 阴影的颜色
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int left = getLeft(), right = getRight();
        int top = getTop(),   bottom = getBottom();

        /*
        ----------------------------------------------
        |(left, top)                (right, top)
        |
        |(left, bottom)             (right, bottom)
        ----------------------------------------------
         */
        /*
        mPaint.setColor(Color.RED);
        canvas.drawLine(left, top, right, bottom, mPaint);
        mPaint.setColor(Color.BLUE);
        canvas.drawLine(left, bottom, right, top, mPaint);
        */
        String text = "0";
        Rect fontRect = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), fontRect);
        int fontSize = fontRect.height();
        int width_0 = getStringWidth("0");
        int width_100 = getStringWidth("100");
        int width_text = getStringWidth("渠首");

        // draw x-y coordinate
        int x_space = width_100 + 40;
        int y_space = 100;
        mPaint.setColor(0xFF000000);
//        canvas.drawLine(0 + x_space - 10, bottom - y_space, right, bottom - y_space, mPaint); // x - axls
        canvas.drawLine(0 + x_space, bottom - y_space, 0 + x_space, 0, mPaint); // y - axls

        // draw x string
        String[] items = new String[] {
                "渠首",
                "闸门",
                "桥梁",
                "渡槽",
                "涵洞",
        };
        int x_grid = (right - x_space) / (items.length * 4);
        canvas.drawText("渠首", x_space + x_grid + x_grid*0, bottom - y_space + fontSize + 20, mPaint);
        canvas.drawText("闸门", x_space + x_grid + x_grid*4, bottom - y_space + fontSize + 20, mPaint);
        canvas.drawText("桥梁", x_space + x_grid + x_grid*8, bottom - y_space + fontSize + 20, mPaint);
        canvas.drawText("渡槽", x_space + x_grid + x_grid*12, bottom - y_space + fontSize + 20, mPaint);
        canvas.drawText("涵洞", x_space + x_grid + x_grid*16, bottom - y_space + fontSize + 20, mPaint);

        // y axis string
        float y_grid = (bottom - y_space) / 6;
        canvas.drawText("0",   x_space - width_0 - 20 , bottom - y_space - y_grid * 0, mPaint);
        canvas.drawText("100", x_space - width_100 - 20, bottom - y_space - y_grid * 1, mPaint);
        canvas.drawText("200", x_space - width_100 - 20, bottom - y_space - y_grid * 2, mPaint);
        canvas.drawText("300", x_space - width_100 - 20, bottom - y_space - y_grid * 3, mPaint);
        canvas.drawText("400", x_space - width_100 - 20, bottom - y_space - y_grid * 4, mPaint);
        canvas.drawText("500", x_space - width_100 - 20, bottom - y_space - y_grid * 5, mPaint);

        // x axis line
        mPaint.setColor(0xFFC0C0C0);
        canvas.drawLine(x_space - 10, bottom - y_space - y_grid * 0, right, bottom - y_space - y_grid * 0, mPaint); // x - axls
        canvas.drawLine(x_space - 10, bottom - y_space - y_grid * 1, right, bottom - y_space - y_grid * 1, mPaint); // x - axls
        canvas.drawLine(x_space - 10, bottom - y_space - y_grid * 2, right, bottom - y_space - y_grid * 2, mPaint); // x - axls
        canvas.drawLine(x_space - 10, bottom - y_space - y_grid * 3, right, bottom - y_space - y_grid * 3, mPaint); // x - axls
        canvas.drawLine(x_space - 10, bottom - y_space - y_grid * 4, right, bottom - y_space - y_grid * 4, mPaint); // x - axls
        canvas.drawLine(x_space - 10, bottom - y_space - y_grid * 5, right, bottom - y_space - y_grid * 5, mPaint); // x - axls

        // draw column
        int [] times = new int[] {
                532,
                166,
                433,
                344,
                124,
        };

        int rectWidth = width_text;
        int rectHigh = (int) ((600-532)/600.0 * (bottom - y_space));
        int rectX = x_space + x_grid + x_grid*0;
        mPaint.setColor(0xFFFF69B4);
        canalHeadRect = new Rect(rectX, rectHigh, (rectX+rectWidth), bottom - y_space - 2);
        canvas.drawRect(canalHeadRect, mPaint);
        mPaint.setColor(Color.WHITE);
        if (canalHeadRect.contains(touched_x, touched_y)) {
            canvas.drawText("22", rectX + 5, rectHigh + fontSize + 20, mPaint);
        } else {
            canvas.drawText("532", rectX + 5, rectHigh + fontSize + 20, mPaint);
        }

        rectX = x_space + x_grid + x_grid*4;
        rectHigh = (int) ((600-166)/600.0 * (bottom - y_space));
        sluiceGateRect = new Rect(rectX, rectHigh, rectX+rectWidth, bottom - y_space - 2);
        mPaint.setColor(0xFF90EE90);
        canvas.drawRect(sluiceGateRect, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawText("166", rectX + 5, rectHigh + fontSize + 20, mPaint);

        rectX = x_space + x_grid + x_grid*8;
        rectHigh = (int) ((600-433)/600.0 * (bottom - y_space));
        bridgeRect = new Rect(rectX, rectHigh, rectX+rectWidth, bottom - y_space - 2);
        mPaint.setColor(0xFF6495ED);
        canvas.drawRect(bridgeRect, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawText("433", rectX + 5, rectHigh + fontSize + 20, mPaint);

        rectX = x_space + x_grid + x_grid*12;
        rectHigh = (int) ((600-344)/600.0 * (bottom - y_space));
        launderRect = new Rect(rectX, rectHigh, rectX+rectWidth, bottom - y_space - 2);
        mPaint.setColor(0xFF87CEFA);
        canvas.drawRect(launderRect, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawText("344", rectX + 5, rectHigh + fontSize + 20, mPaint);

        rectX = x_space + x_grid + x_grid*16;
        rectHigh = (int) ((600-124)/600.0 * (bottom - y_space));
        culvertRect = new Rect(rectX, rectHigh, rectX+rectWidth, bottom - y_space - 2);
        mPaint.setColor(0xFFD19275);
        canvas.drawRect(culvertRect, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawText("124", rectX + 5, rectHigh + fontSize + 20, mPaint);
    }

    private int getStringWidth(String str) {
        Rect rect = new Rect();
//        rect.contains(x, y);
        mPaint.getTextBounds(str, 0, str.length(), rect);
        int width = rect.width();//文字宽
        return width;
    }
}
