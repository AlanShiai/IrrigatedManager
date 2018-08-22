package com.example.ashi.irrigatedmanager.level2_5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ashi.irrigatedmanager.R;

import java.util.List;

/**
 * Created by AShi on 7/20/2018.
 */

public class ManualInspectItemAdapter extends ArrayAdapter<ManualInspectItem> {

    private int resourceId;

    public ManualInspectItemAdapter(Context context, int textViewResourceId,
                             List<ManualInspectItem> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ManualInspectItem projectInfo = getItem(position);
        View view;
        ManualInspectItemAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ManualInspectItemAdapter.ViewHolder();
            viewHolder.sluiceName = (TextView) view.findViewById (R.id.manual_inspect_name);
            viewHolder.imageView = (ImageView)  view.findViewById(R.id.image_view);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ManualInspectItemAdapter.ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.sluiceName.setText(projectInfo.getName());
        if (projectInfo.getName().contains("桥")) {
            viewHolder.imageView.setBackgroundResource(R.drawable.c3);
        } else if (projectInfo.getName().contains("闸")) {
            viewHolder.imageView.setBackgroundResource(R.drawable.c1);
        }
        return view;
    }

    class ViewHolder {
        TextView sluiceName;
        ImageView imageView;
    }

}
