package com.jvtc.catcampus_teacher.ui.roster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jvtc.catcampus_teacher.R;
import com.jvtc.catcampus_teacher.data.model.HomeItem;
import com.jvtc.catcampus_teacher.data.model.RosterInItem;
import com.kproduce.roundcorners.RoundTextView;

import java.util.List;

public class RosterItemAdapter extends RecyclerView.Adapter<RosterItemAdapter.ViewHolder> {
    private List<RosterInItem> list;
    private ItemClick itemClick;

    public interface ItemClick {
        void click(RosterInItem rosterInItem);
    }

    public RosterItemAdapter(List<RosterInItem> list, ItemClick itemClick) {
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.roster_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.coursename.setText(list.get(position).getKc());
        holder.classname.setText(list.get(position).getBj());
        holder.hmc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClick != null){
                    itemClick.click(list.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView coursename;
        private TextView classname;
        private RoundTextView hmc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coursename = (TextView) itemView.findViewById(R.id.coursename);
            classname = (TextView) itemView.findViewById(R.id.classname);
            hmc = (RoundTextView) itemView.findViewById(R.id.hmc);
        }
    }

}
