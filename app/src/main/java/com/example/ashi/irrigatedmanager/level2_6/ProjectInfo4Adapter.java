package com.example.ashi.irrigatedmanager.level2_6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ashi.irrigatedmanager.R;
import com.example.ashi.irrigatedmanager.level2_5.ManualInspectBasicInfo;
import com.example.ashi.irrigatedmanager.util.Global;

import java.util.List;

/**
 * Created by ashi on 8/28/2018.
 */

public class ProjectInfo4Adapter extends ArrayAdapter<String> {

    private int resourceId;

    public ProjectInfo4Adapter(Context context, int textViewResourceId,
                              List<String> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String infoKey = getItem(position);
        View view;
        ProjectInfo4Adapter.ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ProjectInfo4Adapter.ViewHolder();
            viewHolder.keyText = (TextView) view.findViewById (R.id.fragment_listview_item1);
            viewHolder.valueText = (TextView) view.findViewById (R.id.fragment_listview_item2);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ProjectInfo4Adapter.ViewHolder) view.getTag(); // 重新获取ViewHolder
        }

        viewHolder.keyText.setText(infoKey);
        viewHolder.valueText.setText(Global.projectDetails.get(infoKey));

        return view;
    }

    class ViewHolder {
        TextView keyText;
        TextView valueText;
    }

}
