package com.jvtc.catcampus_teacher.data;

import com.jvtc.catcampus_teacher.http.HttpCallBack;
import com.jvtc.catcampus_teacher.http.HttpUtils;
import com.jvtc.catcampus_teacher.http.RxApis;
import com.jvtc.catcampus_teacher.http.RxHttpCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CourseRepositroy {
    private static volatile CourseRepositroy instance;

    public static CourseRepositroy getInstance() {
        if (instance == null) {
            instance = new CourseRepositroy();
        }
        return instance;
    }

    /**
     * 获取课表信息
     */
    public void getCourses(String date, RxHttpCallBack httpCallBack) {
        JSONObject req = new JSONObject();
        try {
            req.put("rq", date);
            req.put("cookie", LoginRepository.getInstance().getLoggedInUser().getCookie());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), req.toString());
        HttpUtils.createHttp(HttpUtils.createRxRetrofit(HttpUtils.baseUrl).create(RxApis.class).getCourses(requestBody),
                new HttpCallBack() {
                    @Override
                    public void onCompleted() {
                        httpCallBack.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        httpCallBack.onError(new Result.Error(e, "请检查网络"));
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("code").equals("0")) {
                                String data = jsonObject.getString("data");
                                httpCallBack.onSuccess(new Result.Success(new JSONArray(data)));
                            } else {
                                httpCallBack.onError(new Result.Error(null, "暂无课程信息"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    /**
     * 获取课表周次
     */
    public void getWeek(String date,RxHttpCallBack httpCallBack){
        JSONObject req = new JSONObject();
        try {
            req.put("rq", date);
            req.put("cookie", LoginRepository.getInstance().getLoggedInUser().getCookie());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), req.toString());
        HttpUtils.createHttp(HttpUtils.createRxRetrofit(HttpUtils.baseUrl).create(RxApis.class).getWeek(requestBody),
                new HttpCallBack() {
                    @Override
                    public void onCompleted() {
                        httpCallBack.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        httpCallBack.onError(new Result.Error(e, "请检查网络"));
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("code").equals("0")) {
                                String data = jsonObject.getString("data");
                                httpCallBack.onSuccess(new Result.Success(data));
                            } else {
                                httpCallBack.onError(new Result.Error(null, "暂无周次信息"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    public void getWeek(RxHttpCallBack httpCallBack){
        HttpUtils.createHttp(HttpUtils.createRxRetrofit(HttpUtils.notbucaiUrl).create(RxApis.class).getWeek(), new HttpCallBack() {
            @Override
            public void onCompleted() {
                httpCallBack.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                httpCallBack.onError(new Result.Error(e, "请检查网络"));
            }

            @Override
            public void onNext(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("code") == 200) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        httpCallBack.onSuccess(new Result.Success(data));
                    } else {
                        httpCallBack.onError(new Result.Error(null, "请求课程数据失败"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
