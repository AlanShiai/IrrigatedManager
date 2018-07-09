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

public class ProjectInfoAdpter extends ArrayAdapter<ProjectInfo> {

    private int resourceId;

    public ProjectInfoAdpter(Context context, int textViewResourceId,
                        List<ProjectInfo> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProjectInfo projectInfo = getItem(position); // 获取当前项的Fruit实例
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.projectName = (TextView) view.findViewById (R.id.projectName);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.projectName.setText(projectInfo.getName());
        return view;
    }

    class ViewHolder {
        TextView projectName;
    }

}
