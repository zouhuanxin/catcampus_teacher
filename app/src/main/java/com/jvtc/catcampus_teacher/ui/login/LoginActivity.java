package com.jvtc.catcampus_teacher.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.jvtc.catcampus_teacher.MainActivity;
import com.jvtc.catcampus_teacher.R;
import com.jvtc.catcampus_teacher.data.LoginRepository;
import com.jvtc.catcampus_teacher.ui.login.LoginViewModel;
import com.jvtc.catcampus_teacher.ui.login.LoginViewModelFactory;
import com.jvtc.catcampus_teacher.ui.web.X5WebViewActivity;
import com.jvtc.catcampus_teacher.util.NavigationManager;
import com.jvtc.catcampus_teacher.util.PermissionUtils;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    private EditText accountEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ProgressBar loadingProgressBar;
    private TextView agreement;
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).init();
        new PermissionUtils().verifyStoragePermissions(this, null);
        setContentView(R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory()).get(LoginViewModel.class);
        initView();
        initData();
    }


    private void initView() {
        accountEditText = findViewById(R.id.account);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        loadingProgressBar = findViewById(R.id.loading);
        agreement = findViewById(R.id.agreement);
    }

    private void initData() {
        agreement.setText(Html.fromHtml("登录即代表您已阅读并同意<font color='#4678ff'>《九小师隐私协议》</font>"));

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                if (loginFormState.getUsernameError() != null) {
                    accountEditText.setError(loginFormState.getUsernameError());
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(loginFormState.getPasswordError());
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loginButton.setEnabled(true);
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                    return;
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);
                finish();
            }
        });

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(accountEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, X5WebViewActivity.class);
                intent.putExtra("title", "《九小师隐私协议》");
                intent.putExtra("url", "https://v.ncgame.cc/service.html");
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(accountEditText.getText())) {
                    showLoginFailed("请输入账号");
                    return;
                }
                if (TextUtils.isEmpty(passwordEditText.getText())) {
                    showLoginFailed("请输入密码");
                    return;
                }
                Boolean status = loginViewModel.login(accountEditText.getText().toString(), passwordEditText.getText().toString());
                if (status) {
                    loginButton.setEnabled(false);
                    loadingProgressBar.setVisibility(View.VISIBLE);
                }
            }
        });
        //已存在登录信息时候，自动填写账号密码信息
        if (LoginRepository.getInstance().getLoggedInUser() != null
                && !TextUtils.isEmpty(LoginRepository.getInstance().getLoggedInUser().getCookie())) {
            accountEditText.setText(LoginRepository.getInstance().getLoggedInUser().getAccount());
            passwordEditText.setText(LoginRepository.getInstance().getLoggedInUser().getPassword());
        }
        //是否自动登录教务系统
        if (LoginRepository.getInstance().getLoggedInUser() != null
                && LoginRepository.getInstance().getLoggedInUser().getCookie() != null
                && !TextUtils.isEmpty(LoginRepository.getInstance().getLoggedInUser().getCookie())
                && LoginRepository.getInstance().getLoggedInUser().getAuto() != null
                && LoginRepository.getInstance().getLoggedInUser().getAuto()) {
            Boolean status = loginViewModel.login(
                    LoginRepository.getInstance().getLoggedInUser().getAccount(),
                    LoginRepository.getInstance().getLoggedInUser().getPassword());
            if (status) {
                loginButton.setEnabled(false);
                loadingProgressBar.setVisibility(View.VISIBLE);
            }
            return;
        }
    }

    private void updateUiWithUser(LoggedInUserView model) {
        Toast.makeText(getApplicationContext(), model.getDisplayName(), Toast.LENGTH_SHORT).show();
        intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void showLoginFailed(String errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}