package com.jvtc.catcampus_teacher.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.ItemKeyedDataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;
import androidx.paging.PositionalDataSource;

import com.jvtc.catcampus_teacher.R;
import com.jvtc.catcampus_teacher.data.HomeRepsitory;
import com.jvtc.catcampus_teacher.data.PageDataSourceFactory;
import com.jvtc.catcampus_teacher.data.Result;
import com.jvtc.catcampus_teacher.data.model.HomeItem;
import com.jvtc.catcampus_teacher.data.model.NetModel;
import com.jvtc.catcampus_teacher.data.model.NetModel2;
import com.jvtc.catcampus_teacher.http.OkHttps;
import com.jvtc.catcampus_teacher.http.RxApis;
import com.jvtc.catcampus_teacher.http.RxHttpCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<HomeItem>> homeItems;
    private MutableLiveData<TodayData> todaydata;
    private MutableLiveData<List<HeadItem>> headLivewData;

    public HomeViewModel() {
        homeItems = new MutableLiveData<>();
        homeItems.setValue(new ArrayList<>());
        homeItems.getValue().add(new HomeItem(10, R.mipmap.card, "校园卡流水"));
        homeItems.getValue().add(new HomeItem(0, R.mipmap.bjhmc, "班级花名册"));
        // homeItems.getValue().add(new HomeItem(1, R.mipmap.jskbcx,"教师课表查询"));
        homeItems.getValue().add(new HomeItem(2, R.mipmap.jskbcx2, "教室课表查询"));
        homeItems.getValue().add(new HomeItem(3, R.mipmap.jwxtmmxg, "教务系统密码修改"));
        // homeItems.getValue().add(new HomeItem(4, R.mipmap.xgptgrxx,"学工平台个人信息"));
        homeItems.getValue().add(new HomeItem(5, R.mipmap.xsmmxg, "学生密码修改"));
        homeItems.getValue().add(new HomeItem(6, R.mipmap.rcqjsh, "日常请假审核"));
        homeItems.getValue().add(new HomeItem(7, R.mipmap.rcxjgl, "日常销假管理"));
        homeItems.getValue().add(new HomeItem(8, R.mipmap.xgmmxg, "学工密码修改"));
        // homeItems.getValue().add(new HomeItem(9, R.mipmap.tk,"调课"));
        homeItems.setValue(homeItems.getValue());

        TodayData td = new TodayData();
        todaydata = new MutableLiveData<>();
        todaydata.setValue(td);

        headLivewData = new MutableLiveData<>();
    }

    public LiveData<List<HomeItem>> getHomeItems() {
        return homeItems;
    }

    public MutableLiveData<TodayData> getTodaydata() {
        return todaydata;
    }

    public MutableLiveData<List<HeadItem>> getHeadLivewData() {
        return headLivewData;
    }

    public void initTodayData() {
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

    public void initHead() {
        HomeRepsitory.getInstance().getShufflingFigure(new RxHttpCallBack() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Result.Error data) {

            }

            @Override
            public void onSuccess(Result.Success data) {
                JSONArray array = (JSONArray) data.getData();
                List<HeadItem> list = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    HeadItem item = new HeadItem();
                    try {
                        item.imageurl = array.getJSONObject(i).getString("imageurl");
                        item.url = array.getJSONObject(i).getString("url");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    list.add(item);
                }
                headLivewData.setValue(list);
            }
        });
    }

    class TodayData {
        public int toDayApiNum;
        public int toMonthApisNum;
        public int toAllUserNum;
        public int toDayNewUserNum;
    }

    class HeadItem {
        public int id;
        public String title;
        public String imageurl;
        public String url;
        public String createdtime;
        public String updatedtime;
    }
}