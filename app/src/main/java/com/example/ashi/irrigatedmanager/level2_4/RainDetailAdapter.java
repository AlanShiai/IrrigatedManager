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
 * Created by AShi on 8/22/2018.
 */

public class RainDetailAdapter extends ArrayAdapter<RainDetail> {

    private int resourceId;

    public RainDetailAdapter(Context context, int textViewResourceId,
                       List<RainDetail> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RainDetail projectInfo = getItem(position);
        View view;
        RainDetailAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new RainDetailAdapter.ViewHolder();
            viewHolder.time1 = (TextView) view.findViewById (R.id.time1);
            viewHolder.rain1 = (TextView) view.findViewById (R.id.rain1);
            viewHolder.time2 = (TextView) view.findViewById (R.id.time2);
            viewHolder.rain2 = (TextView) view.findViewById (R.id.rain2);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (RainDetailAdapter.ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.time1.setText(projectInfo.time1);
        viewHolder.rain1.setText(projectInfo.rain1);
        viewHolder.time2.setText(projectInfo.time2);
        viewHolder.rain2.setText(projectInfo.rain2);
        return view;
    }

    class ViewHolder {
        TextView time1;
        TextView rain1;
        TextView time2;
        TextView rain2;
    }

}
