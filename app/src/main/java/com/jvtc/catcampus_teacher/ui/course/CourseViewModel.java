package com.jvtc.catcampus_teacher.ui.course;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jvtc.catcampus_teacher.data.CourseRepositroy;
import com.jvtc.catcampus_teacher.data.Result;
import com.jvtc.catcampus_teacher.http.RxHttpCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseViewModel extends ViewModel {

    private MutableLiveData<CourseResult> courses;
    private MutableLiveData<CourseWeek> week;

    public CourseViewModel() {
        courses = new MutableLiveData<>();
        week = new MutableLiveData<>();
    }

    public MutableLiveData<CourseResult> getCourses() {
        return courses;
    }

    public MutableLiveData<CourseWeek> getWeek() {
        return week;
    }

    public void initCourses(String date,int index) {
        if (index < 0 && week.getValue().currentWeek == 1){
            courses.setValue(new CourseResult(null,"第一周了，不能再减了"));
            return;
        }
        CourseRepositroy.getInstance().getCourses(date, new RxHttpCallBack() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Result.Error data) {
                if (data.getError() == null){
                    if (index > 0){
                        week.getValue().currentWeek = week.getValue().currentWeek + 1;
                    } else if (index < 0 && week.getValue().currentWeek > 1){
                        week.getValue().currentWeek = week.getValue().currentWeek - 1;
                    }
                    week.getValue().currentDate = date;
                    week.setValue(week.getValue());
                }
                courses.setValue(new CourseResult(null,data.getMessage()));
            }

            @Override
            public void onSuccess(Result.Success data) {
                List<CourseItem> list = new ArrayList<>();
                JSONArray array = (JSONArray) data.getData();
                for (int i = 0; i < array.length(); i++) {
                    CourseItem item = new CourseItem();
                    try {
                        item.kcdescribe = array.getJSONObject(i).getString("kcdescribe");
                        item.kcname = array.getJSONObject(i).getString("kcname");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    list.add(item);
                    courses.setValue(new CourseResult(list,null));
                }
                if (index > 0){
                    week.getValue().currentWeek = week.getValue().currentWeek + 1;
                } else if (index < 0){
                    week.getValue().currentWeek = week.getValue().currentWeek - 1;
                }
                week.getValue().currentDate = date;
                week.setValue(week.getValue());
            }
        });
    }

    public void initWeekData(){
        CourseRepositroy.getInstance().getWeek(new RxHttpCallBack() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Result.Error data) {

            }

            @Override
            public void onSuccess(Result.Success data) {
                JSONObject jsonObject = (JSONObject) data.getData();
                CourseWeek courseWeek = new CourseWeek();
                try {
                    courseWeek.totalWeek = jsonObject.getInt("totalWeek");
                    courseWeek.currentWeek = jsonObject.getInt("currentWeek");
                    courseWeek.currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                week.setValue(courseWeek);
            }
        });
    }

    class CourseItem {
        public String kcdescribe;
        public String kcname;
    }

    class CourseResult{
        public List<CourseItem> courseItemList;
        public String error;

        public CourseResult(List<CourseItem> courseItemList, String error) {
            this.courseItemList = courseItemList;
            this.error = error;
        }
    }

    class CourseWeek{
        public int totalWeek;
        public int currentWeek;
        public String currentDate;
    }
}