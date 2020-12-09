package com.jvtc.catcampus_teacher.data;

import com.jvtc.catcampus_teacher.http.HttpCallBack;
import com.jvtc.catcampus_teacher.http.HttpUtils;
import com.jvtc.catcampus_teacher.http.RxApis;
import com.jvtc.catcampus_teacher.http.RxHttpCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeRepsitory {
    private static volatile HomeRepsitory instance;

    public static HomeRepsitory getInstance() {
        if (instance == null) {
            instance = new HomeRepsitory();
        }
        return instance;
    }


    public void getData(RxHttpCallBack httpCallBack){
        HttpUtils.createHttp(HttpUtils.createRxRetrofit(HttpUtils.ncgameUrl).create(RxApis.class).getData(), new HttpCallBack() {
            @Override
            public void onCompleted() {
                httpCallBack.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                httpCallBack.onError(new Result.Error(e,null));
            }

            @Override
            public void onNext(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("code") == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        httpCallBack.onSuccess(new Result.Success(data));
                    } else {
                        httpCallBack.onError(new Result.Error(null, "请求今日数据失败"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getShufflingFigure(RxHttpCallBack httpCallBack){
        HttpUtils.createHttp(HttpUtils.createRxRetrofit(HttpUtils.baseUrl).create(RxApis.class).getAllShufflingFigure(), new HttpCallBack() {
            @Override
            public void onCompleted() {
                httpCallBack.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                httpCallBack.onError(new Result.Error(e,null));
            }

            @Override
            public void onNext(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("code") == 200) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        httpCallBack.onSuccess(new Result.Success(data));
                    } else {
                        httpCallBack.onError(new Result.Error(null, "请求轮播图失败"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
