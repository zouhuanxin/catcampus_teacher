package com.jvtc.catcampus_teacher.ui.ClassQuery;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.gyf.immersionbar.ImmersionBar;
import com.jvtc.catcampus_teacher.R;
import com.jvtc.catcampus_teacher.data.model.ClassQueryInItem;
import com.jvtc.catcampus_teacher.util.NavigationManager;
import com.kproduce.roundcorners.RoundButton;
import com.kproduce.roundcorners.RoundLinearLayout;

import java.util.List;

public class ClassQueryActivity extends AppCompatActivity {
    private ClassQueryViewModel classQueryViewModel;
    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private ExpandableListView classqueryExpandableview;
    private Spinner xnxqh;
    private Spinner skyx;
    private Spinner xqid;
    private Spinner jzwid;
    private EditText skjs;
    private RoundButton next;
    private RoundLinearLayout search;
    private ProgressBar progress;
    private ClassQueryAdapter classQueryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationManager.setStatusBarColor(this);
        setContentView(R.layout.activity_class_query);
        classQueryViewModel = new ViewModelProvider(this).get(ClassQueryViewModel.class);
        initView();
        initData();
    }

    private void initView() {
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headText = (TextView) findViewById(R.id.head_text);
        xnxqh = (Spinner) findViewById(R.id.xnxqh);
        skyx = (Spinner) findViewById(R.id.skyx);
        xqid = (Spinner) findViewById(R.id.xqid);
        jzwid = (Spinner) findViewById(R.id.jzwid);
        skjs = (EditText) findViewById(R.id.skjs);
        next = (RoundButton) findViewById(R.id.next);
        //搜索布局
        search = (RoundLinearLayout) findViewById(R.id.search);
        //加载布局
        progress = (ProgressBar) findViewById(R.id.progress);
        //查询数据显示数据
        classqueryExpandableview = (ExpandableListView) findViewById(R.id.classquery_expandableview);

        headLefticon.setImageResource(R.mipmap.arrowleft_back);
        headLefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (headText.getText().equals("教室课表查询")) {
                    finish();
                } else {
                    classQueryViewModel.getTitlelivedata().setValue("教室课表查询");
                    controllerView(View.VISIBLE, View.GONE, View.GONE);
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(skjs.getText())) {
                    Toast.makeText(ClassQueryActivity.this, "请输入教室号", Toast.LENGTH_SHORT).show();
                    return;
                }
                controllerView(View.GONE, View.VISIBLE, View.GONE);
                classQueryViewModel.QueryData(
                        classQueryViewModel.getXnxqhlivedata().getValue().get(xnxqh.getSelectedItemPosition())
                        , classQueryViewModel.getSkyxmap().get(classQueryViewModel.getSkyxlivedata().getValue().get(skyx.getSelectedItemPosition())).toString()
                        , classQueryViewModel.getXqidmap().get(classQueryViewModel.getXqidlivedata().getValue().get(xqid.getSelectedItemPosition())).toString()
                        , classQueryViewModel.getJzwidmap().get(classQueryViewModel.getJzwidlivedata().getValue().get(jzwid.getSelectedItemPosition())).toString()
                        , skjs.getText().toString()
                );
            }
        });
    }

    private void initData() {
        classQueryViewModel.getXnxqhlivedata().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                initXnxqh(strings);
            }
        });
        classQueryViewModel.getSkyxlivedata().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                initSkyx(strings);
            }
        });
        classQueryViewModel.getXqidlivedata().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                initXqid(strings);
            }
        });
        classQueryViewModel.getJzwidlivedata().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                initJzwid(strings);
            }
        });
        classQueryViewModel.getExpandlivedata().observe(this, new Observer<ClassQueryViewModel.ClassQueryRestult>() {
            @Override
            public void onChanged(ClassQueryViewModel.ClassQueryRestult classQueryRestult) {
                if (classQueryRestult.error != null) {
                    Toast.makeText(ClassQueryActivity.this, classQueryRestult.error, Toast.LENGTH_SHORT).show();
                    return;
                }
                initAdater(classQueryRestult.classQueryInItemList);
            }
        });
        classQueryViewModel.getTitlelivedata().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                headText.setText(s);
            }
        });
    }

    private void initXnxqh(List<String> list) {
        String[] mItems = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            mItems[i] = list.get(i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        xnxqh.setAdapter(adapter);
    }

    private void initSkyx(List<String> list) {
        String[] mItems = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            mItems[i] = list.get(i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        skyx.setAdapter(adapter);
    }

    private void initJzwid(List<String> list) {
        String[] mItems = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            mItems[i] = list.get(i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jzwid.setAdapter(adapter);
    }

    private void initXqid(List<String> list) {
        String[] mItems = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            mItems[i] = list.get(i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        xqid.setAdapter(adapter);
    }

    private void initAdater(List<ClassQueryInItem> list) {
        classQueryAdapter = new ClassQueryAdapter(list);
        classqueryExpandableview.setAdapter(classQueryAdapter);
        controllerView(View.GONE, View.GONE, View.VISIBLE);
    }

    private void controllerView(int a, int b, int c) {
        search.setVisibility(a);
        progress.setVisibility(b);
        classqueryExpandableview.setVisibility(c);
    }
}