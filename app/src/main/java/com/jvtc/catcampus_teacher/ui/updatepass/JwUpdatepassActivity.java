package com.jvtc.catcampus_teacher.ui.updatepass;

import android.content.Intent;
import android.content.pm.PackageManager;
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
import com.jvtc.catcampus_teacher.data.Result;
import com.jvtc.catcampus_teacher.http.HttpCallBack;
import com.jvtc.catcampus_teacher.http.HttpUtils;
import com.jvtc.catcampus_teacher.http.RxApis;
import com.jvtc.catcampus_teacher.ui.login.LoginActivity;
import com.kproduce.roundcorners.RoundButton;
import com.youth.banner.util.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class JwUpdatepassActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_jw_updatepass);
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
        headText.setText("教务系统密码修改");
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
            Toast.makeText(JwUpdatepassActivity.this,"请输入旧密码",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(newpassword1.getText())){
            Toast.makeText(JwUpdatepassActivity.this,"请输入新密码",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(newpassword2.getText())){
            Toast.makeText(JwUpdatepassActivity.this,"请输入确认密码",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!(newpassword1.getText().toString().equals(newpassword2.getText().toString()))){
            Toast.makeText(JwUpdatepassActivity.this,"俩次密码不一致",Toast.LENGTH_SHORT).show();
            return;
        }
        progress.setVisibility(View.VISIBLE);
        JSONObject req = new JSONObject();
        try {
            req.put("oldpassword", oldpassword.getText());
            req.put("password1", newpassword1.getText());
            req.put("password2", newpassword2.getText());
            req.put("cookie", LoginRepository.getInstance().getLoggedInUser().getCookie());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), req.toString());
        HttpUtils.createHttp(HttpUtils.createRxRetrofit(HttpUtils.baseUrl).create(RxApis.class).teach_uploadpass_info(requestBody), new HttpCallBack() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(JwUpdatepassActivity.this,"请检查网络",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(JSONObject jsonObject) {
                try {
                    Toast.makeText(JwUpdatepassActivity.this,jsonObject.getString("data"),Toast.LENGTH_SHORT).show();
                    if (jsonObject.getInt("code") == 0 && jsonObject.getString("data").trim().equals("\"密码修改成功,请重新登录!\"")) {
                        LoginRepository.getInstance().logout();
                        restartApp();
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