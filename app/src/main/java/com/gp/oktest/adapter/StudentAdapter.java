package com.gp.oktest.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gp.oktest.R;
import com.gp.oktest.model.Student;

import java.util.List;

/**
 * 学生Adapter
 *
 * Created by michael on 2017/9/22.
 */

public class StudentAdapter extends BaseCompatableAdapter<Student> {
    public StudentAdapter(Context context, List<Student> data) {
        super(context, data);
    }

    private class ViewHolder {
        TextView tvId;
        TextView tvName;
        TextView tvAge;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = super.layoutInflater.inflate(R.layout.list_item_student, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvId = (TextView) convertView.findViewById(R.id.tvId);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.tvAge = (TextView) convertView.findViewById(R.id.tvAge);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Student student = getItem(position);
        viewHolder.tvId.setText(student.getsNo());
        viewHolder.tvName.setText(student.getsName());
        viewHolder.tvAge.setText(student.getsSex());
        return convertView;
    }
}
