package com.example.ashi.irrigatedmanager.gson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ashi.irrigatedmanager.R;
import com.example.ashi.irrigatedmanager.level5.AppvalDetails;
import com.example.ashi.irrigatedmanager.level5.AppvalDetailsAdapter;

import java.util.List;

/**
 * Created by ashi on 9/3/2018.
 */

public class TaskFlowAdapter extends ArrayAdapter<TaskFlow> {

    private int resourceId;

    public TaskFlowAdapter(Context context, int textViewResourceId,
                                List<TaskFlow> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TaskFlow itemData = getItem(position);
        View view;
        TaskFlowAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new TaskFlowAdapter.ViewHolder();
            viewHolder.assigneeName = (TextView) view.findViewById (R.id.assigneeName);
            viewHolder.entTime = (TextView) view.findViewById (R.id.entTime);
            viewHolder.activityName = (TextView) view.findViewById (R.id.activityName);
            viewHolder.comment = (TextView) view.findViewById (R.id.comment);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (TaskFlowAdapter.ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.assigneeName.setText(itemData.assigneeName);
        viewHolder.entTime.setText(itemData.entTime);
        viewHolder.activityName.setText(itemData.activityName);
        viewHolder.comment.setText(itemData.comment);
        return view;
    }

    class ViewHolder {
        TextView assigneeName;
        TextView entTime;
        TextView activityName;
        TextView comment;
    }


}
