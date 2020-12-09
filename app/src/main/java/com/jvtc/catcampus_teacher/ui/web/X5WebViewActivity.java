package com.jvtc.catcampus_teacher.ui.web;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.jvtc.catcampus_teacher.R;
import com.jvtc.catcampus_teacher.util.NavigationManager;

public class X5WebViewActivity extends AppCompatActivity {

    private WebView forumContext;
    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationManager.setStatusBarColor(this);
        setContentView(R.layout.activity_x5webview);
        initView();
    }

    private void initView() {
        forumContext = (WebView) findViewById(R.id.forum_context);
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headText = (TextView) findViewById(R.id.head_text);
        headLefticon.setImageResource(R.mipmap.arrowleft_back);

        if (getIntent().getStringExtra("url") == null && getIntent().getStringExtra("title") == null) {
            Toast.makeText(X5WebViewActivity.this, "网络请求超时", Toast.LENGTH_SHORT).show();
            return;
        }
        WebSettings webSettings = forumContext.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLoadsImagesAutomatically(true);
        headLefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        headText.setText(getIntent().getStringExtra("title"));
        forumContext.loadUrl(getIntent().getStringExtra("url"));
    }
}
