package com.jvtc.catcampus_teacher.ui.studentupdatepass;

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
import com.jvtc.catcampus_teacher.ui.login.LoginActivity2;
import com.jvtc.catcampus_teacher.util.NavigationManager;
import com.kproduce.roundcorners.RoundButton;

import org.json.JSONException;
import org.json.JSONObject;

public class StudentUpdatePassActivity extends AppCompatActivity {

    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private EditText studentId;
    private RoundButton next;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationManager.setStatusBarColor(this);
        setContentView(R.layout.activity_student_update_pass);
        initView();
    }

    private void initView() {
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headText = (TextView) findViewById(R.id.head_text);
        studentId = (EditText) findViewById(R.id.studentid);
        next = (RoundButton) findViewById(R.id.next);
        progress = (ProgressBar) findViewById(R.id.progress);

        headLefticon.setImageResource(R.mipmap.arrowleft_back);
        headText.setText("学生密码修改");
        headLefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(studentId.getText())) {
                    Toast.makeText(StudentUpdatePassActivity.this, "请输入学生学号", Toast.LENGTH_SHORT).show();
                    return;
                }
                UpdatePass(studentId.getText().toString());
            }
        });
    }

    private void UpdatePass(String studentId){
        progress.setVisibility(View.VISIBLE);
        HttpUtils.createHttp(HttpUtils.createRxRetrofit(HttpUtils.ncgameUrl).create(RxApis.class)
                .teacherReSetpass("Bearer "+LoginRepository.getInstance().getLoggedInUser().getCookie2(),studentId), new HttpCallBack() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                progress.setVisibility(View.GONE);
                Toast.makeText(StudentUpdatePassActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(JSONObject jsonObject) {
                try {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(StudentUpdatePassActivity.this, jsonObject.getJSONObject("data").getString("msg"), Toast.LENGTH_SHORT).show();
                    if (jsonObject.getInt("code") == 0) {
                        if (jsonObject.getJSONObject("data").getBoolean("type")){
                            finish();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}