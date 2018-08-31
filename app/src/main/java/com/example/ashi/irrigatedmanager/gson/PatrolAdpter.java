package com.example.ashi.irrigatedmanager.gson;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ashi.irrigatedmanager.R;

import java.util.List;

/**
 * Created by ashi on 8/31/2018.
 */

public class PatrolAdpter extends ArrayAdapter<PatrolNote> {

    private int resourceId;

    public PatrolAdpter(Context context, int textViewResourceId,
                          List<PatrolNote> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PatrolNote projectInfo = getItem(position); // 获取当前项的Fruit实例
        View view;
        PatrolAdpter.ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new PatrolAdpter.ViewHolder();
            viewHolder.goalName = (TextView) view.findViewById (R.id.goalName);
            viewHolder.result = (TextView) view.findViewById (R.id.result);
            viewHolder.updateDate = (TextView) view.findViewById (R.id.updateDate);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (PatrolAdpter.ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.goalName.setText(projectInfo.goalName);
        if ("异常".equals(projectInfo.result)) {
            viewHolder.result.setTextColor(0xFFF24646);
        } else {
            viewHolder.result.setTextColor(0xFF3BBA85);
        }
        viewHolder.result.setText(projectInfo.result);
        viewHolder.updateDate.setText(projectInfo.updateDate.trim());

        return view;
    }

    class ViewHolder {
        TextView goalName;
        TextView result;
        TextView updateDate;
    }
}

