package com.jvtc.catcampus_teacher.ui.hot;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jvtc.catcampus_teacher.R;
import com.jvtc.catcampus_teacher.ui.web.X5WebViewActivity;

public class HotFragment extends Fragment {
    private HotViewModel hotViewModel;
    private WebView forumContext;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        hotViewModel = new ViewModelProvider(this).get(HotViewModel.class);
        View root = inflater.inflate(R.layout.fragment_hot, container, false);
        initView(root);
        return root;
    }

    private void initView(View root) {
        forumContext = (WebView) root.findViewById(R.id.forum_context);

        WebSettings webSettings = forumContext.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLoadsImagesAutomatically(true);
        forumContext.loadUrl("https://jvtc.notbucai.com/jiu/");
    }
}