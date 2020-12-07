package com.jvtc.catcampus_teacher.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jvtc.catcampus_teacher.R;
import com.jvtc.catcampus_teacher.data.LoginRepository;
import com.jvtc.catcampus_teacher.data.model.LoggedInUser;
import com.jvtc.catcampus_teacher.http.HttpCallBack;
import com.jvtc.catcampus_teacher.http.HttpUtils;
import com.jvtc.catcampus_teacher.http.RxApis;
import com.jvtc.catcampus_teacher.ui.holimanager.DestructionActivity;
import com.jvtc.catcampus_teacher.ui.holimanager.ReViewActivity;
import com.jvtc.catcampus_teacher.ui.studentupdatepass.StudentUpdatePassActivity;
import com.jvtc.catcampus_teacher.ui.updatepass.XgUpdatepassActivity;
import com.jvtc.catcampus_teacher.util.NavigationManager;
import com.kproduce.roundcorners.RoundButton;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginActivity2 extends AppCompatActivity {

    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private EditText password;
    private RoundButton login;
    private ProgressBar progress;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationManager.setStatusBarColor(this);
        setContentView(R.layout.activity_login2);
        initView();
    }

    private void initView() {
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headText = (TextView) findViewById(R.id.head_text);
        password = (EditText) findViewById(R.id.password);
        login = (RoundButton) findViewById(R.id.login);
        progress = (ProgressBar) findViewById(R.id.progress);

        headLefticon.setImageResource(R.mipmap.arrowleft_back);
        headText.setText("学工平台登录");
        headLefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(password.getText())) {
                    Toast.makeText(LoginActivity2.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                login(password.getText().toString());
            }
        });

        CheckXg();
    }

    private void CheckXg() {
        if (!TextUtils.isEmpty(LoginRepository.getInstance().getLoggedInUser().getCookie2())) {
            //自动登录
            login(LoginRepository.getInstance().getLoggedInUser().getPassword2());
        }
    }

    private void login(String pass) {
        progress.setVisibility(View.VISIBLE);
        JSONObject req = new JSONObject();
        try {
            req.put("loginName", LoginRepository.getInstance().getLoggedInUser().getAccount());
            req.put("loginPwd", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), req.toString());
        HttpUtils.createHttp(HttpUtils.createRxRetrofit(HttpUtils.ncgameUrl).create(RxApis.class).xglogin(requestBody), new HttpCallBack() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                progress.setVisibility(View.GONE);
                Toast.makeText(LoginActivity2.this, "请检查网络", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(JSONObject jsonObject) {
                try {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity2.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    if (jsonObject.getInt("code") == 0) {
                        LoggedInUser user = LoginRepository.getInstance().getLoggedInUser();
                        user.setCookie2(jsonObject.getString("token"));
                        user.setPassword2(pass);
                        LoginRepository.getInstance().setLoggedInUser(user);
                        if (TextUtils.isEmpty(getIntent().getStringExtra("target"))) {
                            return;
                        }
                        switch (getIntent().getStringExtra("target")) {
                            case "学生密码修改":
                                intent = new Intent(LoginActivity2.this, StudentUpdatePassActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case "日常请假审核":
                                intent = new Intent(LoginActivity2.this, ReViewActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case "日常销假管理":
                                intent = new Intent(LoginActivity2.this, DestructionActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case "学工密码修改":
                                intent = new Intent(LoginActivity2.this, XgUpdatepassActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}