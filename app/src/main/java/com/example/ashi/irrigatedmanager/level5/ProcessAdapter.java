package com.example.ashi.irrigatedmanager.level5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ashi.irrigatedmanager.R;

import java.util.List;

/**
 * Created by ashi on 8/26/2018.
 */

public class ProcessAdapter extends ArrayAdapter<Process> {

    private int resourceId;

    public ProcessAdapter(Context context, int textViewResourceId,
                         List<Process> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Process projectInfo = getItem(position);
        View view;
        ProcessAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ProcessAdapter.ViewHolder();
            viewHolder.name = (TextView) view.findViewById (R.id.appval_name);
            viewHolder.time = (TextView) view.findViewById (R.id.time);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ProcessAdapter.ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.name.setText(projectInfo.title);
        viewHolder.time.setText(projectInfo.startDate);
        return view;
    }

    class ViewHolder {
        TextView name;
        TextView time;
    }
}
