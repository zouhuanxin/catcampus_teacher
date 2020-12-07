package com.jvtc.catcampus_teacher.ui.roster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jvtc.catcampus_teacher.R;
import com.jvtc.catcampus_teacher.data.model.RosterDetailsInItem;
import com.kproduce.roundcorners.RoundTextView;

import java.util.List;

public class RosterDetailsItemAdapter extends RecyclerView.Adapter<RosterDetailsItemAdapter.ViewHolder> {
    private List<RosterDetailsInItem> list;

    public RosterDetailsItemAdapter(List<RosterDetailsInItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rosterdetails_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.serialnumber.setText(list.get(position).getId());
        holder.coursename.setText(list.get(position).getName());
        holder.grade.setText(list.get(position).getSex() + " - " +list.get(position).getGrade() + "级");
        holder.college.setText(list.get(position).getCollege());
        holder.studentid.setText(list.get(position).getStudentid());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private RoundTextView serialnumber;
        private TextView coursename;
        private TextView studentid;
        private TextView college;
        private TextView grade;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serialnumber = (RoundTextView) itemView.findViewById(R.id.serialnumber);
            coursename = (TextView) itemView.findViewById(R.id.coursename);
            studentid = (TextView) itemView.findViewById(R.id.studentid);
            college = (TextView) itemView.findViewById(R.id.college);
            grade = (TextView) itemView.findViewById(R.id.grade);
        }
    }

}
