package com.jvtc.catcampus_teacher.ui.roster;

import android.content.Intent;
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
import com.jvtc.catcampus_teacher.data.model.RosterInItem;
import com.jvtc.catcampus_teacher.util.NavigationManager;

import java.util.List;

public class RosterActivity extends AppCompatActivity {
    private RosterViewModel rosterViewModel;
    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private RecyclerView rosterRecy;
    private RosterItemAdapter rosterItemAdapter;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationManager.setStatusBarColor(this);
        setContentView(R.layout.activity_roster);
        rosterViewModel = new ViewModelProvider(this).get(RosterViewModel.class);
        initView();
        initData();
    }

    private void initView() {
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headText = (TextView) findViewById(R.id.head_text);
        rosterRecy = (RecyclerView) findViewById(R.id.roster_recy);
        progress = (ProgressBar) findViewById(R.id.progress);

        headLefticon.setImageResource(R.mipmap.arrowleft_back);
        headText.setText("花名册");
        headLefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        rosterViewModel.getRosterInItemMutableLiveData().observe(this, new Observer<RosterViewModel.RosterResult>() {
            @Override
            public void onChanged(RosterViewModel.RosterResult rosterResult) {
                if (rosterResult.error == null) {
                    initAdapter(rosterResult.list);
                }
            }
        });
        rosterViewModel.initRosterData();
    }

    private void initAdapter(List<RosterInItem> list) {
        rosterItemAdapter = new RosterItemAdapter(list, new RosterItemAdapter.ItemClick() {
            @Override
            public void click(RosterInItem rosterInItem) {
                Intent intent = new Intent(RosterActivity.this, RosterDetailsActivity.class);
                /**
                 * 处理参数
                 *
                 */
                String[] arr1 = rosterInItem.getHmc().split("hmc:");
                if (arr1.length > 0) {
                    String[] arr2 = arr1[0].split(",");
                    if (arr2.length > 0) {
                        String jx0404id = arr2[0].replace("frdy_laosha('", "").replace("'", "");
                        intent.putExtra("jx0404id", jx0404id);
                        intent.putExtra("kc", rosterInItem.getKc());
                        startActivity(intent);
                    }
                }
            }
        });
        rosterRecy.setLayoutManager(new LinearLayoutManager(this));
        rosterRecy.setAdapter(rosterItemAdapter);
        progress.setVisibility(View.GONE);
    }
}