package com.example.ashi.irrigatedmanager.level2_4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ashi.irrigatedmanager.ProjectInfo;
import com.example.ashi.irrigatedmanager.ProjectInfoAdpter;
import com.example.ashi.irrigatedmanager.R;

import java.util.List;

/**
 * Created by ashi on 7/13/2018.
 */

public class SluiceInfoAdapter extends ArrayAdapter<SluiceInfo> {


    private int resourceId;

    public SluiceInfoAdapter(Context context, int textViewResourceId,
                             List<SluiceInfo> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SluiceInfo projectInfo = getItem(position);
        View view;
        SluiceInfoAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new SluiceInfoAdapter.ViewHolder();
            viewHolder.sluiceName = (TextView) view.findViewById (R.id.sluice_name);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (SluiceInfoAdapter.ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.sluiceName.setText(projectInfo.getName());
        return view;
    }

    class ViewHolder {
        TextView sluiceName;
    }

}
