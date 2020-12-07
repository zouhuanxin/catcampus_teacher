package com.jvtc.catcampus_teacher.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.text.TextUtils;
import android.util.Patterns;

import com.jvtc.catcampus_teacher.data.LoginRepository;
import com.jvtc.catcampus_teacher.data.Result;
import com.jvtc.catcampus_teacher.data.model.LoggedInUser;
import com.jvtc.catcampus_teacher.R;
import com.jvtc.catcampus_teacher.http.HttpUtils;
import com.jvtc.catcampus_teacher.http.RxApis;
import com.jvtc.catcampus_teacher.http.RxHttpCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;
    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public boolean login(String account, String password) {
        loginRepository.login(account, password, new RxHttpCallBack() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Result.Error data) {
                loginResult.setValue(new LoginResult("login_failed"));
            }

            @Override
            public void onSuccess(Result.Success data) {
                LoggedInUser loggedInUser = (LoggedInUser) data.getData();
                loginResult.setValue(new LoginResult(new LoggedInUserView("登录成功")));
            }
        });
        return true;
    }

}