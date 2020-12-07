package com.jvtc.catcampus_teacher.ui.roster;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.jvtc.catcampus_teacher.R;
import com.jvtc.catcampus_teacher.data.model.RosterDetailsInItem;
import com.jvtc.catcampus_teacher.util.NavigationManager;

import java.util.List;

public class RosterDetailsActivity extends AppCompatActivity {
    private RosterViewModel rosterViewModel;
    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private RecyclerView rosterDetailsRecy;
    private String jx0404id;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationManager.setStatusBarColor(this);
        setContentView(R.layout.activity_roster_details);
        rosterViewModel = new ViewModelProvider(this).get(RosterViewModel.class);
        initView();
        initData();
    }

    private void initView() {
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headText = (TextView) findViewById(R.id.head_text);
        rosterDetailsRecy = (RecyclerView) findViewById(R.id.roster_details_recy);
        progress = (ProgressBar) findViewById(R.id.progress);

        headLefticon.setImageResource(R.mipmap.arrowleft_back);
        headText.setText(getIntent().getStringExtra("kc"));
        headLefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        jx0404id = getIntent().getStringExtra("jx0404id");
        rosterViewModel.getRosterDetailsResultMutableLiveData().observe(this, new Observer<RosterViewModel.RosterDetailsResult>() {
            @Override
            public void onChanged(RosterViewModel.RosterDetailsResult rosterDetailsResult) {
                if (rosterDetailsResult.error == null) {
                    initAdapter(rosterDetailsResult.list);
                }
            }
        });
        rosterViewModel.initRosterDetailsData(jx0404id);
    }

    private void initAdapter(List<RosterDetailsInItem> list) {
        RosterDetailsItemAdapter rosterDetailsItemAdapter = new RosterDetailsItemAdapter(list);
        rosterDetailsRecy.setLayoutManager(new LinearLayoutManager(this));
        rosterDetailsRecy.setAdapter(rosterDetailsItemAdapter);
        progress.setVisibility(View.GONE);
    }
}