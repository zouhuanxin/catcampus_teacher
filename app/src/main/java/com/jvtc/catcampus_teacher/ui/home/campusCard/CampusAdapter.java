package com.jvtc.catcampus_teacher.ui.home.campusCard;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.internal.LinkedTreeMap;
import com.jvtc.catcampus_teacher.R;
import com.jvtc.catcampus_teacher.data.model.MealCard;

public class CampusAdapter extends PagedListAdapter<LinkedTreeMap, BaseViewHolder> {
    private Context mContext;

    protected CampusAdapter(@NonNull DiffUtil.ItemCallback<LinkedTreeMap> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.campus_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        TextView tvName = holder.getView(R.id.name);
        TextView tvAddress = holder.getView(R.id.address);
        TextView tvTime = holder.getView(R.id.time);
        String money = getItem(position).get("JYJE").toString();
        if (money.indexOf("-") == -1) {
            tvName.setBackgroundColor(Color.parseColor("#ea413f"));
        } else {
            tvName.setBackgroundColor(Color.parseColor("#6bc26c"));
        }
        tvName.setText(money);
        tvAddress.setText(getItem(position).get("ZDMC").toString());
        tvTime.setText(getItem(position).get("JYSJ").toString());
    }
}