package com.example.ashi.irrigatedmanager.level2_6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ashi.irrigatedmanager.R;
import com.example.ashi.irrigatedmanager.level5.AppvalAdapter;

import java.util.List;

/**
 * Created by AShi on 8/17/2018.
 */

public class ProjectInfo3Adpter extends ArrayAdapter<ProjectInfo3> {

    private int resourceId;

    public ProjectInfo3Adpter(Context context, int textViewResourceId,
                             List<ProjectInfo3> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProjectInfo3 projectInfo = getItem(position); // 获取当前项的Fruit实例
        View view;
        ProjectInfo3Adpter.ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ProjectInfo3Adpter.ViewHolder();
            viewHolder.name = (TextView) view.findViewById (R.id.name);
            viewHolder.officeName = (TextView) view.findViewById (R.id.officeName);
            viewHolder.subType = (TextView) view.findViewById (R.id.subType);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ProjectInfo3Adpter.ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        if ( null != projectInfo.name) {
            viewHolder.name.setText(projectInfo.name);
        }
        if ( null != projectInfo.officeName) {
            viewHolder.officeName.setText(projectInfo.officeName);
        }
        if ( null != projectInfo.subType) {
            viewHolder.subType.setText(projectInfo.subType);
        }

        return view;
    }

    class ViewHolder {
        TextView name;
        TextView officeName;
        TextView subType;
    }

}
