package com.jvtc.catcampus_teacher.data;

import com.jvtc.catcampus_teacher.app.MyApplication;
import com.jvtc.catcampus_teacher.data.model.LoggedInUser;
import com.jvtc.catcampus_teacher.http.HttpCallBack;
import com.jvtc.catcampus_teacher.http.HttpUtils;
import com.jvtc.catcampus_teacher.http.RxApis;
import com.jvtc.catcampus_teacher.http.RxHttpCallBack;
import com.jvtc.catcampus_teacher.util.AppRepository;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoggedInUser user = null;

    public static LoginRepository getInstance() {
        if (instance == null) {
            instance = new LoginRepository();
        }
        return instance;
    }

    public LoggedInUser getLoggedInUser(){
        return AppRepository.getUser(MyApplication.getMyApplication());
    }

    public void logout() {
        user = null;
        AppRepository.clearUser(MyApplication.getMyApplication());
    }

    public void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        AppRepository.setUser(MyApplication.getMyApplication(),user);
    }

    public void login(String account, String password, RxHttpCallBack httpCallBack) {
        JSONObject req = new JSONObject();
        try {
            //bcl515516
            req.put("jwusername",account);
            req.put("jwpassword",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), req.toString());
        HttpUtils.createHttp(HttpUtils.createRxRetrofit(HttpUtils.baseUrl).create(RxApis.class).login(requestBody),
                new HttpCallBack() {
                    @Override
                    public void onCompleted() {
                        httpCallBack.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        httpCallBack.onError(new Result.Error(e,"登录失败,请检查网络"));
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getInt("code") == 0){
                                user = new LoggedInUser();
                                user.setAccount(account);
                                user.setPassword(password);
                                user.setCookie(jsonObject.getString("cookie"));
                                //第一次登录的时候没有这个信息不进行保存
                                if (LoginRepository.getInstance().getLoggedInUser() != null){
                                    user.setPassword2(LoginRepository.getInstance().getLoggedInUser().getPassword2());
                                    user.setCookie2(LoginRepository.getInstance().getLoggedInUser().getCookie2());
                                }
                                setLoggedInUser(user);
                                httpCallBack.onSuccess(new Result.Success<>(user));
                            } else {
                                httpCallBack.onError(new Result.Error(null,"登录失败,请检查账号密码"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

}