package com.jvtc.catcampus_teacher.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jvtc.catcampus_teacher.R;
import com.jvtc.catcampus_teacher.data.HomeRepsitory;
import com.jvtc.catcampus_teacher.data.Result;
import com.jvtc.catcampus_teacher.data.model.HomeItem;
import com.jvtc.catcampus_teacher.http.RxHttpCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<String>> banners;
    private MutableLiveData<List<HomeItem>> homeItems;
    private MutableLiveData<TodayData> todaydata;

    public HomeViewModel() {
        banners = new MutableLiveData<>();
        banners.setValue(new ArrayList<>());
        banners.getValue().add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3356833154,898474305&fm=26&gp=0.jpg");
        banners.getValue().add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=139373873,4083642889&fm=26&gp=0.jpg");
        banners.setValue(banners.getValue());

        homeItems = new MutableLiveData<>();
        homeItems.setValue(new ArrayList<>());
        homeItems.getValue().add(new HomeItem(0, R.mipmap.bjhmc,"班级花名册"));
        // homeItems.getValue().add(new HomeItem(1, R.mipmap.jskbcx,"教师课表查询"));
        homeItems.getValue().add(new HomeItem(2, R.mipmap.jskbcx2,"教室课表查询"));
        homeItems.getValue().add(new HomeItem(3, R.mipmap.jwxtmmxg,"教务系统密码修改"));
        // homeItems.getValue().add(new HomeItem(4, R.mipmap.xgptgrxx,"学工平台个人信息"));
        homeItems.getValue().add(new HomeItem(5, R.mipmap.xsmmxg,"学生密码修改"));
        homeItems.getValue().add(new HomeItem(6, R.mipmap.rcqjsh,"日常请假审核"));
        homeItems.getValue().add(new HomeItem(7, R.mipmap.rcxjgl,"日常销假管理"));
        homeItems.getValue().add(new HomeItem(8, R.mipmap.xgmmxg,"学工密码修改"));
        // homeItems.getValue().add(new HomeItem(9, R.mipmap.tk,"调课"));
        homeItems.setValue(homeItems.getValue());

        TodayData td = new TodayData();
        todaydata = new MutableLiveData<>();
        todaydata.setValue(td);
    }

    public LiveData<List<String>> getBanners(){
        return banners;
    }

    public LiveData<List<HomeItem>> getHomeItems() {
        return homeItems;
    }

    public MutableLiveData<TodayData> getTodaydata() {
        return todaydata;
    }

    public void initTodayData(){
        HomeRepsitory.getInstance().getData(new RxHttpCallBack() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Result.Error data) {

            }

            @Override
            public void onSuccess(Result.Success data) {
                JSONObject jsonObject = (JSONObject) data.getData();
                TodayData td = todaydata.getValue();
                try {
                    td.toDayApiNum = jsonObject.getInt("toDayApiNum");
                    td.toMonthApisNum = jsonObject.getInt("toMonthApisNum");
                    td.toAllUserNum = jsonObject.getInt("toAllUserNum");
                    td.toDayNewUserNum = jsonObject.getInt("toDayNewUserNum");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                todaydata.setValue(td);
            }
        });
    }

    class TodayData{
        public int toDayApiNum;
        public int toMonthApisNum;
        public int toAllUserNum;
        public int toDayNewUserNum;
    }
}