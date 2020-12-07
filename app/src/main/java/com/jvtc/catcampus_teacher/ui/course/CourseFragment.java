package com.jvtc.catcampus_teacher.ui.course;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jvtc.catcampus_teacher.R;
import com.jvtc.catcampus_teacher.data.model.HomeItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CourseFragment extends Fragment {
    private View root;
    private CourseViewModel courseViewModel;
    private TextView lastweek;
    private TextView thisweek;
    private TextView nextweek;
    private FrameLayout fragmentCourseGroup;
    private RecyclerView courserecy;
    private CourseAdapter courseAdapter;
    private String currentdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    private ProgressBar courseProgress;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        root = inflater.inflate(R.layout.fragment_course, container, false);
        initView();
        initData();
        return root;
    }

    private void initView() {
        lastweek = (TextView) root.findViewById(R.id.lastweek);
        thisweek = (TextView) root.findViewById(R.id.thisweek);
        nextweek = (TextView) root.findViewById(R.id.nextweek);
        courserecy = (RecyclerView) root.findViewById(R.id.courserecy);
        fragmentCourseGroup = (FrameLayout) root.findViewById(R.id.fragment_course_group);
        courseProgress = (ProgressBar) root.findViewById(R.id.course_progress);

        lastweek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseProgress.setVisibility(View.VISIBLE);
                operationDate(-7);
                courseViewModel.initCourses(currentdate,-7);
            }
        });
        nextweek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseProgress.setVisibility(View.VISIBLE);
                operationDate(7);
                courseViewModel.initCourses(currentdate,7);
            }
        });
    }

    private void initData() {
        courseViewModel.getWeek().observe(getViewLifecycleOwner(), new Observer<CourseViewModel.CourseWeek>() {
            @Override
            public void onChanged(CourseViewModel.CourseWeek courseWeek) {
                thisweek.setText("第" + courseWeek.currentWeek + "周\n" + courseWeek.currentDate);
            }
        });
        courseViewModel.getCourses().observe(getViewLifecycleOwner(), new Observer<CourseViewModel.CourseResult>() {
            @Override
            public void onChanged(CourseViewModel.CourseResult courseResult) {
                if (courseResult.courseItemList == null) {
                    //error
                    courseProgress.setVisibility(View.GONE);
                    Toast.makeText(getContext(), courseResult.error, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (courseResult.courseItemList != null) {
                    ChangeAdapter(courseResult.courseItemList);
                }
            }
        });
        courseViewModel.initWeekData();
        courseViewModel.initCourses(currentdate,0);
    }

    private void operationDate(int index) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(currentdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, index);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime();   //这个时间就是日期往后推一天的结果
        currentdate = simpleDateFormat.format(date);
    }

    private void ChangeAdapter(List<CourseViewModel.CourseItem> list) {
        if (courseAdapter == null) {
            GridLayoutManager manager = new GridLayoutManager(getContext(), 8);
            courseAdapter = new CourseAdapter(list, new CourseAdapter.ItemClick() {
                @Override
                public void click(HomeItem homeItem, int index) {

                }
            });
            courserecy.setLayoutManager(manager);
            courserecy.setAdapter(courseAdapter);
        } else {
            courseAdapter.notifyDataSetChanged();
        }
        courseProgress.setVisibility(View.GONE);
    }
}