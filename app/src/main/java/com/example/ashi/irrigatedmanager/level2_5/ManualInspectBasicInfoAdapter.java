package com.example.ashi.irrigatedmanager.level2_5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ashi.irrigatedmanager.R;
import com.example.ashi.irrigatedmanager.util.Global;

import java.util.List;

/**
 * Created by AShi on 7/23/2018.
 */

public class ManualInspectBasicInfoAdapter extends ArrayAdapter<String> {

    private int resourceId;

    public ManualInspectBasicInfoAdapter(Context context, int textViewResourceId,
                                    List<String> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String infoKey = getItem(position);
        View view;
        ManualInspectBasicInfoAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ManualInspectBasicInfoAdapter.ViewHolder();
            viewHolder.keyText = (TextView) view.findViewById (R.id.fragment_listview_item1);
            viewHolder.valueText = (TextView) view.findViewById (R.id.fragment_listview_item2);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ManualInspectBasicInfoAdapter.ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.keyText.setText(infoKey);
        viewHolder.valueText.setText(Global.patrolDetails.get(infoKey));
        return view;
    }

    class ViewHolder {
        TextView keyText;
        TextView valueText;
    }
}
