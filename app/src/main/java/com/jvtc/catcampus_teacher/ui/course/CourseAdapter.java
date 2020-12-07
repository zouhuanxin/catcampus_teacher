package com.jvtc.catcampus_teacher.ui.course;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jvtc.catcampus_teacher.R;
import com.jvtc.catcampus_teacher.data.model.HomeItem;
import com.jvtc.catcampus_teacher.ui.home.HomeItemAdapter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    private List<CourseViewModel.CourseItem> list;
    private CourseAdapter.ItemClick itemClick;
    private Context context;
    private int parentHeight;
    private String[] colors = {"#81c1ff", "#ff9494", "#789fff", "#ff9e81", "#b79bff", "#b4d36b", "#8ce0c9", "#e5e5e5", "#a7f07b", "#66d4fc", "#f8c384", "#d4eaa0", "#cbe86b", "#62cfe4", "#eaa680", "#6dd2e6", "#62cd94", "#9881F5", "#ec8785"};

    public interface ItemClick {
        void click(HomeItem homeItem, int index);
    }

    public CourseAdapter(List<CourseViewModel.CourseItem> list, CourseAdapter.ItemClick itemClick) {
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        parentHeight = parent.getHeight();
        View view = null;
        ViewGroup.LayoutParams layoutParams = null;
        if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_headitem, parent, false);
            layoutParams = view.getLayoutParams();
            layoutParams.height = parentHeight / 13;
        } else if (viewType == 2) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_leftitem, parent, false);
            layoutParams = view.getLayoutParams();
            layoutParams.height = parentHeight / 13 * 2;
        } else if (viewType == 3) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_mainitem, parent, false);
            layoutParams = view.getLayoutParams();
            layoutParams.height = parentHeight / 13 * 2;
        }
        view.setLayoutParams(layoutParams);
        ViewHolder viewHolder = new ViewHolder(view, viewType);
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < 8) {
            //前八个数据为头部
            return 1;
        } else if (position % 8 == 0) {
            //余数为0表示左侧数据
            return 2;
        } else {
            return 3;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position < 8) {
            holder.courseHeaditemText.setTextColor(Color.parseColor("#888888"));
            holder.courseHeaditemText.getPaint().setFakeBoldText(false);
            if (list.get(position).kcname.equals(getWeekOfDate())){
                holder.courseHeaditemText.setTextColor(Color.parseColor("#333333"));
                holder.courseHeaditemText.getPaint().setFakeBoldText(true);
            }
            holder.courseHeaditemText.setText(list.get(position).kcname);
        } else if (position % 8 == 0) {
            holder.courseLeftitemText.setText(list.get(position).kcname);
        } else {
            if (!TextUtils.isEmpty(list.get(position).kcname)){
                holder.courseMainitemText.setText(list.get(position).kcname);
                int index = list.get(position).kcname.hashCode() % colors.length;
                String color = colors[0];
                if (index < 0){
                    index = index * -1;
                }
                if (index < colors.length){
                    color = colors[index];
                }
                holder.courseMainitemGroup.setBackgroundColor(Color.parseColor(color));
                holder.courseMainitemGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popWindow(list.get(position).kcdescribe);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private FrameLayout courseHeaditemGroup;
        private TextView courseHeaditemText;
        private FrameLayout courseLeftitemGroup;
        private TextView courseLeftitemText;
        private FrameLayout courseMainitemGroup;
        private TextView courseMainitemText;

        public ViewHolder(@NonNull View itemView, @NonNull int viewType) {
            super(itemView);
            if (viewType == 1) {
                courseHeaditemGroup = (FrameLayout) itemView.findViewById(R.id.course_headitem_group);
                courseHeaditemText = (TextView) itemView.findViewById(R.id.course_headitem_text);
            } else if (viewType == 2) {
                courseLeftitemGroup = (FrameLayout) itemView.findViewById(R.id.course_leftitem_group);
                courseLeftitemText = (TextView) itemView.findViewById(R.id.course_leftitem_text);
            } else {
                courseMainitemGroup = (FrameLayout) itemView.findViewById(R.id.course_mainitem_group);
                courseMainitemText = (TextView) itemView.findViewById(R.id.course_mainitem_text);
            }
        }
    }

    private void popWindow(String str){
        Dialog bottomDialog = new Dialog(context, R.style.BottomDialog);
        View contentView = LayoutInflater.from(context).inflate(R.layout.course_dialog, null);
        ((TextView)contentView.findViewById(R.id.course_dialog_text)).setText(str);
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
        layoutParams.height = (int) context.getResources().getDimension(R.dimen.dp_200);
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.TOP);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }

    private String getWeekOfDate() {
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
}
