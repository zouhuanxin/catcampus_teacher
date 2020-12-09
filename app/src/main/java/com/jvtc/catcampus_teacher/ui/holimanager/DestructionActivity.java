package com.jvtc.catcampus_teacher.ui.holimanager;

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

import org.json.JSONArray;

import java.util.List;

/**
 * 销假
 */
public class DestructionActivity extends AppCompatActivity implements View.OnClickListener {
    private DestructionModel destructionModel;
    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private RecyclerView destrutionRecy;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationManager.setStatusBarColor(this);
        setContentView(R.layout.activity_destruction);
        destructionModel = new ViewModelProvider(this).get(DestructionModel.class);
        initView();
        initData();
    }

    private void initView() {
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headText = (TextView) findViewById(R.id.head_text);
        destrutionRecy = (RecyclerView) findViewById(R.id.destruction_recy);
        progress = (ProgressBar) findViewById(R.id.progress);

        headLefticon.setImageResource(R.mipmap.arrowleft_back);
        headText.setText("销假管理");
        headLefticon.setOnClickListener(this);
    }

    private void initData() {
        destructionModel.getReViewResultMutableLiveData().observe(this, new Observer<DestructionModel.DestructionResult>() {
            @Override
            public void onChanged(DestructionModel.DestructionResult destructionResult) {
                if (destructionResult.error == null) {
                    initAdapter(destructionResult.list);
                    return;
                }
                Toast.makeText(DestructionActivity.this,destructionResult.error,Toast.LENGTH_SHORT).show();
            }
        });
        destructionModel.getOperationResultMutableLiveData().observe(this, new Observer<DestructionModel.OperationResult>() {
            @Override
            public void onChanged(DestructionModel.OperationResult operationResult) {
                if (operationResult.error == null){
                    destructionModel.queryData();
                    Toast.makeText(DestructionActivity.this,operationResult.success,Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(DestructionActivity.this,operationResult.error,Toast.LENGTH_SHORT).show();
            }
        });
        progress.setVisibility(View.VISIBLE);
        destructionModel.queryData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_lefticon:
                finish();
                break;
        }
    }

    private void initAdapter(List<DestructionModel.DestructionItem> list) {
        progress.setVisibility(View.GONE);
        if (list == null || list.size() == 0){
            Toast.makeText(DestructionActivity.this,"暂无销假信息",Toast.LENGTH_SHORT).show();
            return;
        }
        DestructionAdapter adapter = new DestructionAdapter(list, new DestructionAdapter.ItemClick() {
            @Override
            public void Click(int type, DestructionModel.DestructionItem item) {
                progress.setVisibility(View.VISIBLE);
                JSONArray array = new JSONArray();
                array.put(item.id);
                destructionModel.Operation(array);
            }
        });
        destrutionRecy.setLayoutManager(new LinearLayoutManager(this));
        destrutionRecy.setAdapter(adapter);
    }
}