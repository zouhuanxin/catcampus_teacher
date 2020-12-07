package com.jvtc.catcampus_teacher.ui.notice;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jvtc.catcampus_teacher.R;
import com.jvtc.catcampus_teacher.http.HttpCallBack;
import com.jvtc.catcampus_teacher.http.HttpUtils;
import com.jvtc.catcampus_teacher.http.RxApis;
import com.jvtc.catcampus_teacher.util.NavigationManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NoticeActivity extends AppCompatActivity {
    private List<NoticeItem> noticeItems;
    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private RecyclerView noticeRecy;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationManager.setStatusBarColor(this);
        setContentView(R.layout.activity_notice);
        initView();
        QueryAllNotice();
    }

    private void initView() {
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headText = (TextView) findViewById(R.id.head_text);
        noticeRecy = (RecyclerView) findViewById(R.id.notice_recy);
        progress = (ProgressBar) findViewById(R.id.progress);

        headLefticon.setImageResource(R.mipmap.arrowleft_back);
        headText.setText("系统通知");
        headLefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void QueryAllNotice() {
        progress.setVisibility(View.VISIBLE);
        HttpUtils.createHttp(HttpUtils.createRxRetrofit(HttpUtils.baseUrl).create(RxApis.class).getAllSystemNotice(), new HttpCallBack() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(NoticeActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("code") == 200) {
                        JSONArray array = jsonObject.getJSONArray("data");
                        noticeItems = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            NoticeItem noticeItem = new NoticeItem();
                            noticeItem.id = array.getJSONObject(i).getInt("id");
                            noticeItem.title = array.getJSONObject(i).getString("title");
                            noticeItem.content = array.getJSONObject(i).getString("content");
                            noticeItem.url = array.getJSONObject(i).getString("url");
                            noticeItem.createdtime = array.getJSONObject(i).getString("createdtime");
                            noticeItems.add(noticeItem);
                        }
                        initAdapter();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initAdapter() {
        NoticeAdapter adapter = new NoticeAdapter(noticeItems, new NoticeAdapter.ItemClick() {
            @Override
            public void Click(NoticeItem item) {

            }
        });
        noticeRecy.setLayoutManager(new LinearLayoutManager(this));
        noticeRecy.setAdapter(adapter);
        progress.setVisibility(View.GONE);
    }

    class NoticeItem {
        public int id;
        public String title;
        public String content;
        public String url;
        public String createdtime;
    }
}