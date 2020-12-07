package com.jvtc.catcampus_teacher.ui.updatepass;

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

import com.gyf.immersionbar.ImmersionBar;
import com.jvtc.catcampus_teacher.R;
import com.jvtc.catcampus_teacher.data.LoginRepository;
import com.jvtc.catcampus_teacher.data.model.LoggedInUser;
import com.jvtc.catcampus_teacher.http.HttpCallBack;
import com.jvtc.catcampus_teacher.http.HttpUtils;
import com.jvtc.catcampus_teacher.http.RxApis;
import com.jvtc.catcampus_teacher.ui.login.LoginActivity;
import com.kproduce.roundcorners.RoundButton;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class XgUpdatepassActivity extends AppCompatActivity {

    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private EditText oldpassword;
    private EditText newpassword1;
    private EditText newpassword2;
    private RoundButton next;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).statusBarColor(R.color.white2).init();
        setContentView(R.layout.activity_xg_updatepass);
        initView();
    }

    private void initView() {
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headText = (TextView) findViewById(R.id.head_text);
        oldpassword = (EditText) findViewById(R.id.oldpassword);
        newpassword1 = (EditText) findViewById(R.id.newpassword1);
        newpassword2 = (EditText) findViewById(R.id.newpassword2);
        next = (RoundButton) findViewById(R.id.next);
        progress = (ProgressBar) findViewById(R.id.progress);

        headLefticon.setImageResource(R.mipmap.arrowleft_back);
        headText.setText("学工平台密码修改");
        headLefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdatePass();
            }
        });
    }

    private void UpdatePass(){
        if (TextUtils.isEmpty(oldpassword.getText())){
            Toast.makeText(XgUpdatepassActivity.this,"请输入旧密码",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(newpassword1.getText())){
            Toast.makeText(XgUpdatepassActivity.this,"请输入新密码",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(newpassword2.getText())){
            Toast.makeText(XgUpdatepassActivity.this,"请输入确认密码",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!(newpassword1.getText().toString().equals(newpassword2.getText().toString()))){
            Toast.makeText(XgUpdatepassActivity.this,"俩次密码不一致",Toast.LENGTH_SHORT).show();
            return;
        }
        progress.setVisibility(View.VISIBLE);
        JSONObject req = new JSONObject();
        try {
            req.put("OldPass", oldpassword.getText());
            req.put("NewPass", newpassword1.getText());
            req.put("NewPassAgin", newpassword2.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), req.toString());
        HttpUtils.createHttp(HttpUtils.createRxRetrofit(HttpUtils.ncgameUrl).create(RxApis.class).teacherChangePass(
                "Bearer "+LoginRepository.getInstance().getLoggedInUser().getCookie2(),requestBody), new HttpCallBack() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                progress.setVisibility(View.GONE);
                Toast.makeText(XgUpdatepassActivity.this,"请检查网络",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(JSONObject jsonObject) {
                try {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(XgUpdatepassActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                    if (jsonObject.getInt("code") == 0) {
                        LoggedInUser user = LoginRepository.getInstance().getLoggedInUser();
                        user.setCookie2(null);
                        user.setPassword2(null);
                        LoginRepository.getInstance().setLoggedInUser(user);
                        finish();
                    } else {
                        progress.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void restartApp() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}