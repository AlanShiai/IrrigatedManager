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
 * Created by AShi on 7/22/2018.
 */

public class AppvalDetailsAdapter  extends ArrayAdapter<AppvalDetails> {

    private int resourceId;

    public AppvalDetailsAdapter(Context context, int textViewResourceId,
                         List<AppvalDetails> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AppvalDetails projectInfo = getItem(position);
        View view;
        AppvalDetailsAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new AppvalDetailsAdapter.ViewHolder();
            viewHolder.assigneeName = (TextView) view.findViewById (R.id.assigneeName);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (AppvalDetailsAdapter.ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.assigneeName.setText(projectInfo.getName());
        return view;
    }

    class ViewHolder {
        TextView assigneeName;
    }

}
