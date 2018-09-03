package com.example.ashi.irrigatedmanager.level5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ashi.irrigatedmanager.R;
import com.example.ashi.irrigatedmanager.level2_4.Rain;
import com.example.ashi.irrigatedmanager.level2_4.RainAdapter;

import java.util.List;

/**
 * Created by AShi on 7/22/2018.
 */

public class AppvalAdapter extends ArrayAdapter<Appval> {


    private int resourceId;

    public AppvalAdapter(Context context, int textViewResourceId,
                       List<Appval> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Appval projectInfo = getItem(position);
        View view;
        AppvalAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new AppvalAdapter.ViewHolder();
            viewHolder.name = (TextView) view.findViewById (R.id.appval_name);
            viewHolder.time = (TextView) view.findViewById (R.id.time);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (AppvalAdapter.ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.name.setText(projectInfo.title);
        viewHolder.time.setText(projectInfo.dealDate);
        return view;
    }

    class ViewHolder {
        TextView name;
        TextView time;
    }

}
