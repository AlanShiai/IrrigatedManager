package com.example.ashi.irrigatedmanager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ashi on 7/9/2018.
 */

public class IrrigationScheduleInfoAdpter extends ArrayAdapter<IrrigationScheduleInfo> {

    static int[] colors25 = new int[] {
            Color.parseColor("#42a0ff"),
            Color.parseColor("#FFFFFF"),
            Color.parseColor("#FFFFFF"),
            Color.parseColor("#FFFFFF"),
    };
    static int[] colors50 = new int[] {
            Color.parseColor("#42a0ff"),
            Color.parseColor("#42a0ff"),
            Color.parseColor("#FFFFFF"),
            Color.parseColor("#FFFFFF"),
    };
    static int[] colors75 = new int[] {
            Color.parseColor("#42a0ff"),
            Color.parseColor("#42a0ff"),
            Color.parseColor("#42a0ff"),
            Color.parseColor("#FFFFFF"),
    };
    static int[] colors100 = new int[] {
            Color.parseColor("#42a0ff"),
            Color.parseColor("#42a0ff"),
            Color.parseColor("#42a0ff"),
            Color.parseColor("#42a0ff"),
    };

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
            if (Build.VERSION.SDK_INT >= 16) {
                GradientDrawable linearDrawable = new GradientDrawable();
                linearDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
                linearDrawable.setColors(colors100);
                if (projectInfo.getSchedule().equals("15.75%")) {
                    linearDrawable.setColors(colors25);
                } else if (projectInfo.getSchedule().equals("40.00%")) {
                    linearDrawable.setColors(colors50);
                } else if (projectInfo.getSchedule().equals("75.00%")) {
                    linearDrawable.setColors(colors75);
                } else if (projectInfo.getSchedule().equals("98.00%")) {
                    linearDrawable.setColors(colors100);
                }
                linearDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                view.setBackground(linearDrawable);

//                Drawable statusQuestionDrawable = getContext().getResources().getDrawable(R.drawable.shape_gradient);
//                view.setBackground(statusQuestionDrawable);
            }
            viewHolder = new IrrigationScheduleInfoAdpter.ViewHolder();
            viewHolder.projectName = (TextView) view.findViewById (R.id.irrigation_schedule_name);
            viewHolder.schedule = (TextView) view.findViewById(R.id.irrigation_schedule);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (IrrigationScheduleInfoAdpter.ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.projectName.setText(projectInfo.getName());
        viewHolder.schedule.setText(projectInfo.getSchedule());
        return view;
    }

    class ViewHolder {
        TextView projectName;
        TextView schedule;
    }
}
