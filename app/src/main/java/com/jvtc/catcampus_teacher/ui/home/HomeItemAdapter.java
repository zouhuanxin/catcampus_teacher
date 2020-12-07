package com.jvtc.catcampus_teacher.ui.home;

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

import java.util.List;

public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.ViewHolder> {
    private List<HomeItem> list;
    private ItemClick itemClick;

    public interface ItemClick {
        void click(HomeItem homeItem);
    }

    public HomeItemAdapter(List<HomeItem> list, ItemClick itemClick) {
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.homeItmeIcon.setImageResource(list.get(position).getIcon());
        holder.homeItemName.setText(list.get(position).getName());
        holder.homeItemGroup.setOnClickListener(new View.OnClickListener() {
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
        private LinearLayout homeItemGroup;
        private ImageView homeItmeIcon;
        private TextView homeItemName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            homeItemGroup = (LinearLayout) itemView.findViewById(R.id.home_item_group);
            homeItmeIcon = (ImageView) itemView.findViewById(R.id.home_itme_icon);
            homeItemName = (TextView) itemView.findViewById(R.id.home_item_name);
        }
    }

}
