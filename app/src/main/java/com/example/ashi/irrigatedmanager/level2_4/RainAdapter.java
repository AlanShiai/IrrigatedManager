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
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (RainAdapter.ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.name.setText(projectInfo.project_name);
        return view;
    }

    class ViewHolder {
        TextView name;
    }

}
