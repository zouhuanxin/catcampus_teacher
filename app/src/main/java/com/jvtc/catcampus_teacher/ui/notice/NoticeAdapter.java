package com.jvtc.catcampus_teacher.ui.notice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jvtc.catcampus_teacher.R;

import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {
    private List<NoticeActivity.NoticeItem> list;
    private ItemClick itemClick;

    public interface ItemClick {
        void Click(NoticeActivity.NoticeItem item);
    }

    public NoticeAdapter(List<NoticeActivity.NoticeItem> list, ItemClick itemClick) {
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(list.get(position).title);
        holder.content.setText(list.get(position).content);
        holder.createdtime.setText(list.get(position).createdtime);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView content;
        private TextView createdtime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
            createdtime = (TextView) itemView.findViewById(R.id.createdtime);
        }
    }

}
