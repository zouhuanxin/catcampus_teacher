package com.jvtc.catcampus_teacher.ui.holimanager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jvtc.catcampus_teacher.R;
import com.jvtc.catcampus_teacher.util.NavigationManager;
import com.kproduce.roundcorners.RoundButton;

import java.util.List;

/**
 * 请假审核
 */
public class ReViewActivity extends AppCompatActivity implements View.OnClickListener {
    private ReViewModel reViewModel;
    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private RecyclerView reviewRecy;
    private ProgressBar progress;
    private RoundButton b1;
    private RoundButton b2;
    private RoundButton b3;
    private RoundButton b4;
    private RoundButton b5;
    private RoundButton b6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationManager.setStatusBarColor(this);
        setContentView(R.layout.activity_review);
        reViewModel = new ViewModelProvider(this).get(ReViewModel.class);
        initView();
        initData();
    }

    private void initView() {
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headText = (TextView) findViewById(R.id.head_text);
        b1 = (RoundButton) findViewById(R.id.b1);
        b2 = (RoundButton) findViewById(R.id.b2);
        b3 = (RoundButton) findViewById(R.id.b3);
        b4 = (RoundButton) findViewById(R.id.b4);
        b5 = (RoundButton) findViewById(R.id.b5);
        b6 = (RoundButton) findViewById(R.id.b6);
        reviewRecy = (RecyclerView) findViewById(R.id.review_recy);
        progress = (ProgressBar) findViewById(R.id.progress);

        headLefticon.setImageResource(R.mipmap.arrowleft_back);
        headText.setText("请假审核");
        headLefticon.setOnClickListener(this);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
    }

    private void initData() {
        reViewModel.getReViewResultMutableLiveData().observe(this, new Observer<ReViewModel.ReViewResult>() {
            @Override
            public void onChanged(ReViewModel.ReViewResult reViewResult) {
                if (reViewResult.error == null){
                    initAdapter(reViewResult.list);
                    return;
                }
                Toast.makeText(ReViewActivity.this,reViewResult.error,Toast.LENGTH_SHORT).show();
            }
        });
        reViewModel.getOperationResultMutableLiveData().observe(this, new Observer<ReViewModel.OperationResult>() {
            @Override
            public void onChanged(ReViewModel.OperationResult operationResult) {
                if (operationResult.error == null){
                    reViewModel.getSelectMutableLiveData().setValue(reViewModel.getSelectMutableLiveData().getValue());
                    Toast.makeText(ReViewActivity.this,operationResult.success,Toast.LENGTH_SHORT).show();
                    return;
                }
                progress.setVisibility(View.GONE);
                Toast.makeText(ReViewActivity.this,operationResult.error,Toast.LENGTH_SHORT).show();
            }
        });
        reViewModel.getSelectMutableLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                progress.setVisibility(View.VISIBLE);
                reViewModel.queryData(integer);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_lefticon:
                finish();
                break;
            case R.id.b1:
                reViewModel.getSelectMutableLiveData().setValue(1);
                UpdateTopButtonStatus(0);
                break;
            case R.id.b2:
                reViewModel.getSelectMutableLiveData().setValue(2);
                UpdateTopButtonStatus(1);
                break;
            case R.id.b3:
                reViewModel.getSelectMutableLiveData().setValue(4);
                UpdateTopButtonStatus(2);
                break;
            case R.id.b4:
                reViewModel.getSelectMutableLiveData().setValue(5);
                UpdateTopButtonStatus(3);
                break;
            case R.id.b5:
                reViewModel.getSelectMutableLiveData().setValue(7);
                UpdateTopButtonStatus(4);
                break;
            case R.id.b6:
                reViewModel.getSelectMutableLiveData().setValue(9);
                UpdateTopButtonStatus(5);
                break;
        }
    }

    private void UpdateTopButtonStatus(int index) {
        progress.setVisibility(View.VISIBLE);
        b1.setBackgroundColor(Color.parseColor(index == 0 ? "#f6d365" : "#cccccc"));
        b2.setBackgroundColor(Color.parseColor(index == 1 ? "#f6d365" : "#cccccc"));
        b3.setBackgroundColor(Color.parseColor(index == 2 ? "#f6d365" : "#cccccc"));
        b4.setBackgroundColor(Color.parseColor(index == 3 ? "#f6d365" : "#cccccc"));
        b5.setBackgroundColor(Color.parseColor(index == 4 ? "#f6d365" : "#cccccc"));
        b6.setBackgroundColor(Color.parseColor(index == 5 ? "#f6d365" : "#cccccc"));
    }

    private void initAdapter(List<ReViewModel.ReViewItem> list) {
        progress.setVisibility(View.GONE);
        ReViewAdapter adapter = new ReViewAdapter(list, new ReViewAdapter.ItemClick() {
            @Override
            public void Click(int type, ReViewModel.ReViewItem item) {
                progress.setVisibility(View.VISIBLE);
                reViewModel.Operation(type,item.id);
            }
        });
        reviewRecy.setLayoutManager(new LinearLayoutManager(this));
        reviewRecy.setAdapter(adapter);
    }
}