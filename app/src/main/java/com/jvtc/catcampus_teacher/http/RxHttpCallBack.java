package com.jvtc.catcampus_teacher.http;

import com.jvtc.catcampus_teacher.data.Result;

import org.json.JSONObject;

public interface RxHttpCallBack {
    void onCompleted();
    void onError(Result.Error data);
    void onSuccess(Result.Success data);
}
