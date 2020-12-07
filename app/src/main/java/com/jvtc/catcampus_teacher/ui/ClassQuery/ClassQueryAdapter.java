package com.jvtc.catcampus_teacher.ui.ClassQuery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.jvtc.catcampus_teacher.R;
import com.jvtc.catcampus_teacher.data.model.ClassQueryInItem;

import java.util.List;

public class ClassQueryAdapter extends BaseExpandableListAdapter {
    private List<ClassQueryInItem> list;

    public ClassQueryAdapter(List<ClassQueryInItem> list) {
        this.list = list;
    }

    @Override
    public int getGroupCount() {//返回第一级List长度
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {//返回指定groupPosition的第二级List长度
        return list.get(groupPosition).getDetails().size();
    }

    @Override
    public Object getGroup(int groupPosition) {//返回一级List里的内容
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {//返回二级List的内容
        return list.get(groupPosition).getDetails().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {//返回一级View的id 保证id唯一
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {//返回二级View的id 保证id唯一
        return groupPosition + childPosition;
    }

    /**
     * 指示在对基础数据进行更改时子ID和组ID是否稳定
     *
     * @return
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * 返回一级父View
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.classquery_groupitem, parent, false);
        TextView textView = convertView.findViewById(R.id.groupitem_text);
        textView.setText(list.get(groupPosition).getWeek());
        return convertView;
    }

    /**
     * 返回二级子View
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.classquery_childitem, parent, false);
        TextView textView = convertView.findViewById(R.id.childitem_text);
        textView.setText("节次:" + list.get(groupPosition).getDetails().get(childPosition).getNode()
                + "\n\n课程:" + list.get(groupPosition).getDetails().get(childPosition).getText());
        return convertView;
    }

    /**
     * 指定位置的子项是否可选
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
