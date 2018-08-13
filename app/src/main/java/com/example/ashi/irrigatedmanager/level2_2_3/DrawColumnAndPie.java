package com.example.ashi.irrigatedmanager.level2_2_3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by AShi on 8/8/2018.
 */
public class DrawColumnAndPie extends View {

    private Paint mPaint;
    private int mCurWidth;            //当前屏幕的宽 pixel
    private int mCurHeight;           //当前屏幕的高 pixel
    private float mDensity;           //当前屏幕的dpi密度的比值. 720*1080(比值为2), 1080*1920(比值为3), 1440*2550(比值为4)

    private int touched_x = 0, touched_y = 0;
    private Rect canalHeadRect, sluiceGateRect, bridgeRect, launderRect, culvertRect;

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
//        rect.contains(x, y);
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
        Rect fontRect = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), fontRect);
        int fontSize = fontRect.height();
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
        int x_grid = (right - x_space) / (items.length * 4);
        canvas.drawText("渠首", x_space + x_grid + x_grid*0, bottom/2 - y_space + fontSize + 20, mPaint);
        canvas.drawText("闸门", x_space + x_grid + x_grid*4, bottom/2 - y_space + fontSize + 20, mPaint);
        canvas.drawText("桥梁", x_space + x_grid + x_grid*8, bottom/2 - y_space + fontSize + 20, mPaint);
        canvas.drawText("渡槽", x_space + x_grid + x_grid*12, bottom/2 - y_space + fontSize + 20, mPaint);
        canvas.drawText("涵洞", x_space + x_grid + x_grid*16, bottom/2 - y_space + fontSize + 20, mPaint);

        // y axis string
        float y_grid = (bottom/2 - y_space) / 6;
        canvas.drawText("0",   x_space - width_0 - 20 , bottom/2 - y_space - y_grid * 0, mPaint);
        canvas.drawText("100", x_space - width_100 - 20, bottom/2 - y_space - y_grid * 1, mPaint);
        canvas.drawText("200", x_space - width_100 - 20, bottom/2 - y_space - y_grid * 2, mPaint);
        canvas.drawText("300", x_space - width_100 - 20, bottom/2 - y_space - y_grid * 3, mPaint);
        canvas.drawText("400", x_space - width_100 - 20, bottom/2 - y_space - y_grid * 4, mPaint);
        canvas.drawText("500", x_space - width_100 - 20, bottom/2 - y_space - y_grid * 5, mPaint);

        // draw column
        int [] times = new int[] {
                532,
                166,
                433,
                344,
                124,
        };

        int rectWidth = width_text;
        int rectHigh = (int) ((600-532)/600.0 * (bottom/2 - y_space));
        int rectX = x_space + x_grid + x_grid*0;
        mPaint.setColor(Color.RED);
        canalHeadRect = new Rect(rectX, rectHigh, (rectX+rectWidth), bottom/2 - y_space - 2);
        canvas.drawRect(canalHeadRect, mPaint);
        mPaint.setColor(Color.WHITE);
        if (canalHeadRect.contains(touched_x, touched_y)) {
            canvas.drawText("22", rectX + 10, rectHigh + fontSize + 20, mPaint);
        } else {
            canvas.drawText("532", rectX + 10, rectHigh + fontSize + 20, mPaint);
        }

        rectX = x_space + x_grid + x_grid*4;
        rectHigh = (int) ((600-166)/600.0 * (bottom/2 - y_space));
        sluiceGateRect = new Rect(rectX, rectHigh, rectX+rectWidth, bottom/2 - y_space - 2);
        mPaint.setColor(Color.MAGENTA);
        canvas.drawRect(sluiceGateRect, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawText("166", rectX + 10, rectHigh + fontSize + 20, mPaint);

        rectX = x_space + x_grid + x_grid*8;
        rectHigh = (int) ((600-433)/600.0 * (bottom/2 - y_space));
        bridgeRect = new Rect(rectX, rectHigh, rectX+rectWidth, bottom/2 - y_space - 2);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(bridgeRect, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawText("433", rectX + 10, rectHigh + fontSize + 20, mPaint);

        rectX = x_space + x_grid + x_grid*12;
        rectHigh = (int) ((600-344)/600.0 * (bottom/2 - y_space));
        launderRect = new Rect(rectX, rectHigh, rectX+rectWidth, bottom/2 - y_space - 2);
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(launderRect, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawText("344", rectX + 10, rectHigh + fontSize + 20, mPaint);

        rectX = x_space + x_grid + x_grid*16;
        rectHigh = (int) ((600-124)/600.0 * (bottom/2 - y_space));
        culvertRect = new Rect(rectX, rectHigh, rectX+rectWidth, bottom/2 - y_space - 2);
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(culvertRect, mPaint);
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
        int circle_x = right/2, circle_y = bottom/2 + 40 + (bottom/2-80) / 2;
        float sum = 21 + 20 + 9 + 2 + 8;

        if (canalHeadRect.contains(touched_x, touched_y)
                || sluiceGateRect.contains(touched_x, touched_y)
                || bridgeRect.contains(touched_x, touched_y)
                || launderRect.contains(touched_x, touched_y)
                || culvertRect.contains(touched_x, touched_y)) {

            if (canalHeadRect.contains(touched_x, touched_y)) {
                drawException(canvas, pieRectF, mRadius, circle_x, circle_y,
                        right, width, bottom, 21, 532, "渠首");
            }
            if (sluiceGateRect.contains(touched_x, touched_y)) {
                drawException(canvas, pieRectF, mRadius, circle_x, circle_y,
                        right, width, bottom, 20, 166, "闸门");
            }
            if (bridgeRect.contains(touched_x, touched_y)) {
                drawException(canvas, pieRectF, mRadius, circle_x, circle_y,
                        right, width, bottom, 9, 433, "桥梁");
            }
            if (launderRect.contains(touched_x, touched_y)) {
                drawException(canvas, pieRectF, mRadius, circle_x, circle_y,
                        right, width, bottom, 2, 344, "渡槽");
            }
            if (culvertRect.contains(touched_x, touched_y)) {
                drawException(canvas, pieRectF, mRadius, circle_x, circle_y,
                        right, width, bottom, 8, 124, "涵洞");
            }

        } else {
            float startAngle = 0;
            float sweepAngle = 21/sum * 350;
            mPaint.setColor(Color.RED);
            canvas.drawArc(pieRectF, startAngle, sweepAngle, true, mPaint);
            float pxs = (float) (mRadius * Math.cos(Math.toRadians(startAngle + sweepAngle / 2)));
            float pys = (float) (mRadius * Math.sin(Math.toRadians(startAngle + sweepAngle / 2)));
            mPaint.setColor(Color.WHITE);
            canvas.drawText("21", circle_x + pxs, circle_y + pys, mPaint);

            startAngle = startAngle + sweepAngle + 2;
            sweepAngle = 20 / sum * 350;
            mPaint.setColor(Color.MAGENTA);
            canvas.drawArc(pieRectF, startAngle, sweepAngle, true, mPaint);
            pxs = (float) (mRadius * Math.cos(Math.toRadians(startAngle + sweepAngle / 2)));
            pys = (float) (mRadius * Math.sin(Math.toRadians(startAngle + sweepAngle / 2)));
            mPaint.setColor(Color.WHITE);
            canvas.drawText("20", circle_x + pxs, circle_y + pys, mPaint);

            startAngle = startAngle + sweepAngle + 2;
            sweepAngle = 9 / sum * 350;
            mPaint.setColor(Color.BLUE);
            canvas.drawArc(pieRectF, startAngle, sweepAngle, true, mPaint);
            pxs = (float) (mRadius * Math.cos(Math.toRadians(startAngle + sweepAngle / 2)));
            pys = (float) (mRadius * Math.sin(Math.toRadians(startAngle + sweepAngle / 2)));
            mPaint.setColor(Color.WHITE);
            canvas.drawText("9", circle_x + pxs, circle_y + pys, mPaint);

            startAngle = startAngle + sweepAngle + 2;
            sweepAngle = 2 / sum * 350;
            mPaint.setColor(Color.GREEN);
            canvas.drawArc(pieRectF, startAngle, sweepAngle, true, mPaint);
            pxs = (float) (mRadius * Math.cos(Math.toRadians(startAngle + sweepAngle / 2)));
            pys = (float) (mRadius * Math.sin(Math.toRadians(startAngle + sweepAngle / 2)));
            mPaint.setColor(Color.WHITE);
            canvas.drawText("2", circle_x + pxs, circle_y + pys, mPaint);

            startAngle = startAngle + sweepAngle + 2;
            sweepAngle = 8 / sum * 350;
            mPaint.setColor(Color.BLACK);
            canvas.drawArc(pieRectF, startAngle, sweepAngle, true, mPaint);
            pxs = (float) (mRadius * Math.cos(Math.toRadians(startAngle + sweepAngle / 2)));
            pys = (float) (mRadius * Math.sin(Math.toRadians(startAngle + sweepAngle / 2)));
            mPaint.setColor(Color.WHITE);
            canvas.drawText("8", circle_x + pxs, circle_y + pys, mPaint);

            mPaint.setColor(Color.WHITE);
            canvas.drawCircle(right / 2, bottom / 2 + 40 + (bottom / 2 - 80) / 2, width / 2 - 160, mPaint);

            String str = "异常次数";
            int str_width = getStringWidth(str);
            mPaint.setColor(0xFF003D79);
            canvas.drawText(str, right / 2 - str_width / 2, bottom / 2 + 40 + (bottom / 2 - 80) / 2, mPaint);
        }

    }

    private void drawException(Canvas canvas, RectF pieRectF, float mRadius, int circle_x, int circle_y,
                               int right, int width, int bottom,
                               int exceptionNum, int normalNum, String text) {
        float startAngle = 0;
        float sweepAngle = (float) exceptionNum/(exceptionNum + normalNum) * 356;
        mPaint.setColor(Color.RED);
        canvas.drawArc(pieRectF, startAngle, sweepAngle, true, mPaint);
        float pxs = (float) (mRadius * Math.cos(Math.toRadians(startAngle + sweepAngle / 2)));
        float pys = (float) (mRadius * Math.sin(Math.toRadians(startAngle + sweepAngle / 2)));
        mPaint.setColor(0xFF003D79);
        canvas.drawText(exceptionNum+"", circle_x + pxs, circle_y + pys, mPaint);

        startAngle = startAngle + sweepAngle + 2;
        sweepAngle = (float) normalNum/(exceptionNum + normalNum) * 356;
        mPaint.setColor(Color.GRAY);
        canvas.drawArc(pieRectF, startAngle, sweepAngle, true, mPaint);
        pxs = (float) (mRadius * Math.cos(Math.toRadians(startAngle + sweepAngle / 2)));
        pys = (float) (mRadius * Math.sin(Math.toRadians(startAngle + sweepAngle / 2)));
        mPaint.setColor(0xFF003D79);
        canvas.drawText(normalNum+"", circle_x + pxs, circle_y + pys, mPaint);

        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(right / 2, bottom / 2 + 40 + (bottom / 2 - 80) / 2, width / 2 - 160, mPaint);

        String str = text + "异常";
        int str_width = getStringWidth(str);
        mPaint.setColor(0xFF003D79);
        canvas.drawText(str, right / 2 - str_width / 2, bottom / 2 + 40 + (bottom / 2 - 80) / 2, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touched_x = (int) event.getX();
                touched_y = (int) event.getY();
                invalidate();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

}
