package com.example.ashi.irrigatedmanager.level2_4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ashi.irrigatedmanager.R;

import java.util.List;

/**
 * Created by AShi on 7/22/2018.
 */

public class RainAdapter extends ArrayAdapter<Rain> {

    private int resourceId;

    public RainAdapter(Context context, int textViewResourceId,
                             List<Rain> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Rain projectInfo = getItem(position);
        View view;
        RainAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new RainAdapter.ViewHolder();
            viewHolder.name = (TextView) view.findViewById (R.id.rain_name);
            viewHolder.time = (TextView) view.findViewById (R.id.time);
            viewHolder.day_rain = (TextView) view.findViewById (R.id.day_rain);
            viewHolder.month_rain = (TextView) view.findViewById (R.id.month_rain);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (RainAdapter.ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.name.setText(projectInfo.project_name);
        viewHolder.time.setText(projectInfo.time);
        if ( null != projectInfo.dataMonth ) {
            viewHolder.month_rain.setText(projectInfo.dataMonth.rainData + "");
        }
        if ( null != projectInfo.dataDay  && !projectInfo.dataDay.isEmpty()) {
            viewHolder.day_rain.setText(projectInfo.dataDay.get(0).rainData + "");
        }
        return view;
    }

    class ViewHolder {
        TextView name;
        TextView time;
        TextView day_rain;
        TextView month_rain;
    }

}
