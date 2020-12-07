package com.jvtc.catcampus_teacher.ui.holimanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jvtc.catcampus_teacher.R;

import java.util.List;

public class DestructionAdapter extends RecyclerView.Adapter<DestructionAdapter.ViewHolder> {
    private List<DestructionModel.DestructionItem> list;
    private ItemClick itemClick;
    public interface ItemClick{
        void Click(int type, DestructionModel.DestructionItem item);
    }

    public DestructionAdapter(List<DestructionModel.DestructionItem> list, ItemClick itemClick) {
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.destruction_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(list.get(position).name);
        holder.bjXhXb.setText(list.get(position).classbj+"-"+list.get(position).stu_id+"-"+list.get(position).sex);
        holder.applyTime.setText("请假时间:"+list.get(position).date);
        holder.destructionTime.setText("销假时间:"+list.get(position).x_date);
        holder.eventType.setText("事件类型:"+list.get(position).type);
        holder.address.setText("地点:"+list.get(position).location);
        holder.agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClick != null){
                    itemClick.Click(1,list.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView bjXhXb;
        private TextView applyTime;
        private TextView destructionTime;
        private TextView eventType;
        private TextView address;
        private TextView agree;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            bjXhXb = (TextView) itemView.findViewById(R.id.bj_xh_xb);
            applyTime = (TextView) itemView.findViewById(R.id.apply_time);
            destructionTime = (TextView) itemView.findViewById(R.id.destruction_time);
            eventType = (TextView) itemView.findViewById(R.id.event_type);
            address = (TextView) itemView.findViewById(R.id.address);
            agree = (TextView) itemView.findViewById(R.id.agree);
        }
    }

}
