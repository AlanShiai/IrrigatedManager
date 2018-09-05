package com.example.ashi.irrigatedmanager.gson;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ashi.irrigatedmanager.R;
import com.example.ashi.irrigatedmanager.level2_2_3.InspectDetailInfo;
import com.example.ashi.irrigatedmanager.level2_2_3.InspectDetailInfoAdpter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ashi on 8/30/2018.
 */

public class AbnormalAdpter extends ArrayAdapter<Abnormal> {

    private int resourceId;

    public AbnormalAdpter(Context context, int textViewResourceId,
                                   List<Abnormal> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Abnormal projectInfo = getItem(position); // 获取当前项的Fruit实例
        View view;
        AbnormalAdpter.ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new AbnormalAdpter.ViewHolder();
            viewHolder.imageView = (CircleImageView) view.findViewById (R.id.image_view);
            viewHolder.projectLabel = (TextView) view.findViewById (R.id.projectLabel);
            viewHolder.yearNumber = (TextView) view.findViewById(R.id.yearNumber);
            viewHolder.yearAbnormalNumber = (TextView) view.findViewById(R.id.yearAbnormalNumber);
            viewHolder.monthNumber = (TextView) view.findViewById(R.id.monthNumber);
            viewHolder.monthAbnormalNumber = (TextView) view.findViewById(R.id.monthAbnormalNumber);
            viewHolder.year_ratio = (TextView) view.findViewById(R.id.year_ratio);
            viewHolder.month_ratio = (TextView) view.findViewById(R.id.month_ratio);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (AbnormalAdpter.ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.projectLabel.setText(projectInfo.projectLabel);
        viewHolder.yearNumber.setText(projectInfo.yearNumber+"");
        viewHolder.yearAbnormalNumber.setText(projectInfo.yearAbnormalNumber+"");
        viewHolder.monthNumber.setText(projectInfo.monthNumber+"");
        viewHolder.monthAbnormalNumber.setText(projectInfo.monthAbnormalNumber+"");

        int yearNumber=0, yearAbnormalNumber=0, monthNumber=0, monthAbnormalNumber=0;
        try {
            yearNumber = Integer.parseInt(projectInfo.yearNumber);
            yearAbnormalNumber = Integer.parseInt(projectInfo.yearAbnormalNumber);
            monthNumber = Integer.parseInt(projectInfo.monthNumber);
            monthAbnormalNumber = Integer.parseInt(projectInfo.monthAbnormalNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (yearNumber != 0 && yearAbnormalNumber != 0) {
            int ratio = yearAbnormalNumber*100/yearNumber;
            viewHolder.year_ratio.setText(ratio+"%");
        }
        if (monthNumber != 0 && monthAbnormalNumber != 0) {
            int ratio = monthAbnormalNumber*100/monthNumber;
            viewHolder.month_ratio.setText(ratio+"%");
        }
        if (yearAbnormalNumber == 0) {
            viewHolder.year_ratio.setText("0%");
        }
        if (monthAbnormalNumber == 0) {
            viewHolder.month_ratio.setText("0%");
        }
        if (projectInfo.projectLabel.contains("桥") || projectInfo.projectLabel.contains("洞")) {
            viewHolder.imageView.setImageResource(R.drawable.c3);
        } else if (projectInfo.projectLabel.contains("闸")) {
            viewHolder.imageView.setImageResource(R.drawable.c1);
        } else {
            viewHolder.imageView.setImageResource(R.drawable.c4);
        }

        return view;
    }

    class ViewHolder {
        CircleImageView imageView;
        TextView projectLabel;
        TextView yearNumber;
        TextView yearAbnormalNumber;
        TextView monthNumber;
        TextView monthAbnormalNumber;
        TextView year_ratio;
        TextView month_ratio;
    }
}

