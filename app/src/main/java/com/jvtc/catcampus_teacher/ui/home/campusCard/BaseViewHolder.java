package com.jvtc.catcampus_teacher.ui.home.campusCard;

import android.util.SparseArray;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> viewSparseArray;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        viewSparseArray = new SparseArray<>();
    }

    public <T extends View> T getView(@IdRes int id) {
        View view = viewSparseArray.get(id);
        if (null == view) {
            view = itemView.findViewById(id);
            viewSparseArray.put(id, view);
        }
        return (T) view;
    }
}
