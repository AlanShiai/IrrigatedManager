package com.example.ashi.irrigatedmanager.gson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.ashi.irrigatedmanager.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ashi on 8/31/2018.
 */

public class PatrolManagerAdpter extends ArrayAdapter<PatrolManager> {

    private int resourceId;

    public HashMap<Integer, Boolean> states = new HashMap<Integer, Boolean>();

    public PatrolManagerAdpter(Context context, int textViewResourceId,
                          List<PatrolManager> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        states.put(0, true);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PatrolManager projectInfo = getItem(position); // 获取当前项的Fruit实例
        View view;
        PatrolManagerAdpter.ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new PatrolManagerAdpter.ViewHolder();
            viewHolder.rb = (RadioButton) view.findViewById (R.id.user_name);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (PatrolManagerAdpter.ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.rb.setText(projectInfo.userName);

        final int tmp_position = position;
        //当RadioButton被选中时，将其状态记录进States中，并更新其他RadioButton的状态使它们不被选中
        viewHolder.rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //把所有的按钮的状态设置为没选中
                for (int i = 0; i < getCount(); i++) {
                    states.put(i, false);
                }
                //然后设置点击的那个按钮设置状态为选中
                states.put(tmp_position, true);    //这样所有的条目中只有一个被选中！
                notifyDataSetChanged();//刷新适配器
            }
        });

        //上面是点击后设置状态，但是也是需要设置显示样式,通过判断状态设置显示的样式
        if (states.get((Integer) position) == null || states.get((Integer) position) == false) {  //true说明没有被选中
            viewHolder.rb.setChecked(false);
        } else {
            viewHolder.rb.setChecked(true);
        }

        return view;
    }

    class ViewHolder {
        RadioButton rb;
    }
}
