package com.jvtc.catcampus_teacher.http;

import org.json.JSONObject;

public interface HttpCallBack {
    void onCompleted();
    void onError(Throwable e);
    void onNext(JSONObject jsonObject);
}
