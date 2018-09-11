package com.example.ashi.irrigatedmanager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import com.example.ashi.irrigatedmanager.util.Utility;

import java.util.List;

/**
 * Created by ashi on 7/9/2018.
 */

public class IrrigationScheduleInfoAdpter extends ArrayAdapter<IrrigationScheduleInfo> {

    private int resourceId;

    public IrrigationScheduleInfoAdpter(Context context, int textViewResourceId,
                             List<IrrigationScheduleInfo> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IrrigationScheduleInfo projectInfo = getItem(position);
        View view;
        IrrigationScheduleInfoAdpter.ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

            viewHolder = new IrrigationScheduleInfoAdpter.ViewHolder();
            viewHolder.projectName = (TextView) view.findViewById (R.id.irrigation_schedule_name);
            viewHolder.schedule = (TextView) view.findViewById(R.id.irrigation_schedule);
            viewHolder.totalArea = (TextView) view.findViewById(R.id.totalArea);
            viewHolder.irrigationArea = (TextView) view.findViewById(R.id.irrigationArea);
            viewHolder.progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            viewHolder.progressBar_text = (TextView) view.findViewById(R.id.progressBar_text);
            viewHolder.progressBar_text_layout = (RelativeLayout) view.findViewById(R.id.progressBar_text_layout);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (IrrigationScheduleInfoAdpter.ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        int irrigationArea = 0, totalArea = 0;
        try {
            irrigationArea = Integer.parseInt(projectInfo.irrigationArea);
            totalArea = Integer.parseInt(projectInfo.totalArea);
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewHolder.projectName.setText(projectInfo.areaName);
        viewHolder.schedule.setText("100%");
        viewHolder.totalArea.setText(projectInfo.totalArea);
        viewHolder.irrigationArea.setText(projectInfo.irrigationArea);
        viewHolder.progressBar.setProgress(100);
        viewHolder.progressBar_text.setText("100%");
        if (irrigationArea != 0 && totalArea != 0) {
            int ratio = irrigationArea*100/totalArea;
            viewHolder.schedule.setText(ratio+"%");
            viewHolder.progressBar.setProgress(ratio);
            viewHolder.progressBar_text.setText(ratio+"%");

            moveProgressBar_text(view, viewHolder, irrigationArea, totalArea);
        } else {
            viewHolder.schedule.setText("0%");
            viewHolder.progressBar.setProgress(0);
            viewHolder.progressBar_text.setText("0%");
            moveProgressBar_text(view, viewHolder, 100, 100);

        }

        return view;
    }

    private void moveProgressBar_text(View view, ViewHolder viewHolder, int irrigationArea, int totalArea) {
        int totalWidth = view.getResources().getDisplayMetrics().widthPixels - 40;
        int rightMargin = (int) (totalWidth * (totalArea - irrigationArea) / (float) totalArea);
//        int widgetWidth = 30;
//        if (totalWidth - leftMargin < widgetWidth) {
//            leftMargin = totalWidth - widgetWidth;
//        }
        if (rightMargin < 0) {
            rightMargin = 0;
        }
        Utility.margin(viewHolder.progressBar_text, 0, 0, rightMargin, 0);
    }

    class ViewHolder {
        TextView projectName;
        TextView schedule;
        TextView totalArea;
        TextView irrigationArea;
        ProgressBar progressBar;
        TextView progressBar_text;
        RelativeLayout progressBar_text_layout;
    }
}
