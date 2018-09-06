package com.example.ashi.irrigatedmanager.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ashi.irrigatedmanager.R;
import com.example.ashi.irrigatedmanager.level5.Appval;
import com.example.ashi.irrigatedmanager.level5.AppvalAdapter;

import java.util.List;

/**
 * Created by ashi on 9/6/2018.
 */

public class DialogSelectItemAdapter extends ArrayAdapter<String> {

    private int resourceId;

    private int oldSelector;

    public DialogSelectItemAdapter(Context context, int textViewResourceId,
                         List<String> objects, int oldSelector) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        this.oldSelector = oldSelector;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String projectInfo = getItem(position);
        View view;
        DialogSelectItemAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new DialogSelectItemAdapter.ViewHolder();
            viewHolder.name = (TextView) view.findViewById (R.id.name);
            viewHolder.textViewParent = (LinearLayout) view.findViewById (R.id.TextViewParent);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (DialogSelectItemAdapter.ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.name.setText(projectInfo);
        if (oldSelector == position) {
            viewHolder.name.setTextColor(0xFF27AAFE);
            viewHolder.textViewParent.setBackgroundColor(0xFFF2F2F2);
        } else {
            viewHolder.name.setTextColor(0xFF000000);
            viewHolder.textViewParent.setBackgroundColor(0xFFFFFFFF);
        }
        return view;
    }

    class ViewHolder {
        TextView name;
        LinearLayout textViewParent;
    }

}
