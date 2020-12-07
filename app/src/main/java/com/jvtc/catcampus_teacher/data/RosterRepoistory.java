package com.jvtc.catcampus_teacher.data;

import androidx.recyclerview.widget.RecyclerView;

import com.jvtc.catcampus_teacher.http.HttpCallBack;
import com.jvtc.catcampus_teacher.http.HttpUtils;
import com.jvtc.catcampus_teacher.http.RxApis;
import com.jvtc.catcampus_teacher.http.RxHttpCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RosterRepoistory {
    private static volatile RosterRepoistory instance;

    public static RosterRepoistory getInstance() {
        if (instance == null) {
            instance = new RosterRepoistory();
        }
        return instance;
    }

    public void getTeachClassInfo(RxHttpCallBack httpCallBack){
        JSONObject req = new JSONObject();
        try {
            req.put("cookie",LoginRepository.getInstance().getLoggedInUser().getCookie());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), req.toString());
        HttpUtils.createHttp(HttpUtils.createRxRetrofit(HttpUtils.baseUrl).create(RxApis.class).teach_teach_info(requestBody), new HttpCallBack() {
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
                    if (jsonObject.getInt("code") == 0){
                        JSONArray array = new JSONObject(jsonObject.getString("data")).getJSONArray("data");
                        httpCallBack.onSuccess(new Result.Success(array));
                    }else{
                        httpCallBack.onError(new Result.Error(null,"未找到相关班级"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void getTeachDetailsInfo(String jx0404id,RxHttpCallBack httpCallBack){
        JSONObject req = new JSONObject();
        try {
            req.put("cookie",LoginRepository.getInstance().getLoggedInUser().getCookie());
            req.put("jx0404id",jx0404id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), req.toString());
        HttpUtils.createHttp(HttpUtils.createRxRetrofit(HttpUtils.baseUrl).create(RxApis.class).teach_hmc_info(requestBody), new HttpCallBack() {
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
                    if (jsonObject.getInt("code") == 0){
                        JSONArray array = new JSONArray(jsonObject.getString("data"));
                        httpCallBack.onSuccess(new Result.Success(array));
                    }else{
                        httpCallBack.onError(new Result.Error(null,"未找到相关学生信息"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
