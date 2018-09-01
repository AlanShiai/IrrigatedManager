package com.example.ashi.irrigatedmanager.gson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.ashi.irrigatedmanager.R;

import java.util.List;

/**
 * Created by ashi on 8/31/2018.
 */

public class PatrolManagerAdpter extends ArrayAdapter<PatrolManager> {

    private int resourceId;

    public PatrolManagerAdpter(Context context, int textViewResourceId,
                          List<PatrolManager> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PatrolManager projectInfo = getItem(position); // 获取当前项的Fruit实例
        View view;
        PatrolManagerAdpter.ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new PatrolManagerAdpter.ViewHolder();
            viewHolder.userName = (CheckBox) view.findViewById (R.id.user_name);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (PatrolManagerAdpter.ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.userName.setText(projectInfo.userName);


        return view;
    }

    class ViewHolder {
        CheckBox userName;
    }
}
