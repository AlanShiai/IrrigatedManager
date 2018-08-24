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
import android.view.View;

/**
 * Created by ashi on 8/24/2018.
 */

public class DrawPie extends View {

    private Paint mPaint;
    private int mCurWidth;            //当前屏幕的宽 pixel
    private int mCurHeight;           //当前屏幕的高 pixel
    private float mDensity;           //当前屏幕的dpi密度的比值. 720*1080(比值为2), 1080*1920(比值为3), 1440*2550(比值为4)

    private int touched_x = 0, touched_y = 0;
    private Rect canalHeadRect, sluiceGateRect, bridgeRect, launderRect, culvertRect;

    public DrawPie(Context context) {
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
        int top = getTop(), bottom = getBottom();

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

        // draw pie
        int min = Math.min(bottom, right);
        int width = bottom * 3/ 4;
        RectF pieRectF = new RectF((right-width)/2, bottom/8, right - (right-width)/2, bottom*7/8);

        float mRadius = width/2;
        float circle_x = pieRectF.centerX(), circle_y = pieRectF.centerY();
        float sum = 21 + 20 + 9 + 2 + 8;

        float startAngle = 0;
        float sweepAngle = 21/sum * 360;
        mPaint.setColor(0xFFFF69B4);
        canvas.drawArc(pieRectF, startAngle, sweepAngle, true, mPaint);
        float pxs = (float) (mRadius * Math.cos(Math.toRadians(startAngle + sweepAngle / 2)));
        float pys = (float) (mRadius * Math.sin(Math.toRadians(startAngle + sweepAngle / 2)));
        mPaint.setColor(Color.BLACK);
        canvas.drawText("21", circle_x + pxs, circle_y + pys, mPaint);

        startAngle = startAngle + sweepAngle;
        sweepAngle = 20 / sum * 360;
        mPaint.setColor(0xFF90EE90);
        canvas.drawArc(pieRectF, startAngle, sweepAngle, true, mPaint);
        pxs = (float) (mRadius * Math.cos(Math.toRadians(startAngle + sweepAngle / 2)));
        pys = (float) (mRadius * Math.sin(Math.toRadians(startAngle + sweepAngle / 2)));
        mPaint.setColor(Color.BLACK);
        canvas.drawText("20", circle_x + pxs, circle_y + pys, mPaint);

        startAngle = startAngle + sweepAngle;
        sweepAngle = 9 / sum * 360;
        mPaint.setColor(0xFF6495ED);
        canvas.drawArc(pieRectF, startAngle, sweepAngle, true, mPaint);
        pxs = (float) (mRadius * Math.cos(Math.toRadians(startAngle + sweepAngle / 2)));
        pys = (float) (mRadius * Math.sin(Math.toRadians(startAngle + sweepAngle / 2)));
        mPaint.setColor(Color.BLACK);
        canvas.drawText("9", circle_x + pxs, circle_y + pys, mPaint);

        startAngle = startAngle + sweepAngle;
        sweepAngle = 2 / sum * 360;
        mPaint.setColor(0xFF87CEFA);
        canvas.drawArc(pieRectF, startAngle, sweepAngle, true, mPaint);
        pxs = (float) (mRadius * Math.cos(Math.toRadians(startAngle + sweepAngle / 2)));
        pys = (float) (mRadius * Math.sin(Math.toRadians(startAngle + sweepAngle / 2)));
        mPaint.setColor(Color.BLACK);
        canvas.drawText("2", circle_x + pxs, circle_y + pys, mPaint);

        startAngle = startAngle + sweepAngle;
        sweepAngle = 8 / sum * 360;
        mPaint.setColor(0xFFD19275);
        canvas.drawArc(pieRectF, startAngle, sweepAngle, true, mPaint);
        pxs = (float) (mRadius * Math.cos(Math.toRadians(startAngle + sweepAngle / 2)));
        pys = (float) (mRadius * Math.sin(Math.toRadians(startAngle + sweepAngle / 2)));
        mPaint.setColor(Color.BLACK);
        canvas.drawText("8", circle_x + pxs, circle_y + pys, mPaint);

        mPaint.setColor(Color.WHITE);
//        canvas.drawArc(pieRectF., 0, 360, true, mPaint);
        canvas.drawCircle(pieRectF.centerX(), pieRectF.centerY(), width/4, mPaint);

        mPaint.setColor(Color.BLACK);
        String str = "异常次数";
        int str_width = getStringWidth(str);
        canvas.drawText(str, pieRectF.centerX() - str_width / 2, pieRectF.centerY() + 30, mPaint);

        str = "全年";
        mPaint.setTextSize(mDensity * 16);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        str_width = getStringWidth(str);
        canvas.drawText(str, pieRectF.centerX() - str_width / 2, pieRectF.centerY() - 30, mPaint);
    }

    private int getStringWidth(String str) {
        Rect rect = new Rect();
//        rect.contains(x, y);
        mPaint.getTextBounds(str, 0, str.length(), rect);
        int width = rect.width();//文字宽
        return width;
    }
}
