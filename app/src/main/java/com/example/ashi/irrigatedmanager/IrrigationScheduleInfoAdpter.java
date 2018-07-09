package com.example.ashi.irrigatedmanager;

import android.content.Context;
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

    private int resourceId;

    public IrrigationScheduleInfoAdpter(Context context, int textViewResourceId,
                             List<IrrigationScheduleInfo> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IrrigationScheduleInfo projectInfo = getItem(position); // 获取当前项的Fruit实例
        View view;
        IrrigationScheduleInfoAdpter.ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
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
