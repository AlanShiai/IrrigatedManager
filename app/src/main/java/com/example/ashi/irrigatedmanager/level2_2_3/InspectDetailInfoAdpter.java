package com.example.ashi.irrigatedmanager.level2_2_3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ashi.irrigatedmanager.IrrigationScheduleInfo;
import com.example.ashi.irrigatedmanager.IrrigationScheduleInfoAdpter;
import com.example.ashi.irrigatedmanager.R;

import java.util.List;

/**
 * Created by AShi on 7/20/2018.
 */

public class InspectDetailInfoAdpter extends ArrayAdapter<InspectDetailInfo>  {

    private int resourceId;

    public InspectDetailInfoAdpter(Context context, int textViewResourceId,
                                        List<InspectDetailInfo> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InspectDetailInfo projectInfo = getItem(position); // 获取当前项的Fruit实例
        View view;
        InspectDetailInfoAdpter.ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new InspectDetailInfoAdpter.ViewHolder();
            viewHolder.projectName = (TextView) view.findViewById (R.id.inspect_detail_name);
            viewHolder.total = (TextView) view.findViewById(R.id.total);
            viewHolder.abnormalTotal = (TextView) view.findViewById(R.id.abnormalTotal);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (InspectDetailInfoAdpter.ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.projectName.setText(projectInfo.name);
        viewHolder.total.setText(projectInfo.total+"");
        viewHolder.abnormalTotal.setText(projectInfo.abnormalTotal+"");
        return view;
    }

    class ViewHolder {
        TextView projectName;
        TextView total;
        TextView abnormalTotal;
    }
}
