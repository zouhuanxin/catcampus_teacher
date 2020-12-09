package com.jvtc.catcampus_teacher.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jvtc.catcampus_teacher.R;
import com.jvtc.catcampus_teacher.data.LoginRepository;
import com.jvtc.catcampus_teacher.data.model.LoggedInUser;
import com.jvtc.catcampus_teacher.ui.login.LoginActivity;
import com.jvtc.catcampus_teacher.util.NavigationManager;
import com.kproduce.roundcorners.RoundButton;

public class SettingActivity extends AppCompatActivity {

    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private Switch switch1;
    private Switch switch2;
    private RoundButton cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationManager.setStatusBarColor(this);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headText = (TextView) findViewById(R.id.head_text);
        switch1 = (Switch) findViewById(R.id.switch1);
        switch2 = (Switch) findViewById(R.id.switch2);
        cancel = (RoundButton) findViewById(R.id.cancel);

        headLefticon.setImageResource(R.mipmap.arrowleft_back);
        headText.setText("设置");
        headLefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        switch1.setChecked(LoginRepository.getInstance().getLoggedInUser().getAuto());
        switch2.setChecked(LoginRepository.getInstance().getLoggedInUser().getAuto2());
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LoggedInUser user = LoginRepository.getInstance().getLoggedInUser();
                user.setAuto(isChecked);
                LoginRepository.getInstance().setLoggedInUser(user);
            }
        });
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LoggedInUser user = LoginRepository.getInstance().getLoggedInUser();
                user.setAuto2(isChecked);
                LoginRepository.getInstance().setLoggedInUser(user);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginRepository.getInstance().logout();
                restartApp();
            }
        });
    }

    private void restartApp() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}