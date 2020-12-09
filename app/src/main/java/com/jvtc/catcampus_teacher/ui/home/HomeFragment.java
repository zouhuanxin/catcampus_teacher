package com.jvtc.catcampus_teacher.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jvtc.catcampus_teacher.MainActivity;
import com.jvtc.catcampus_teacher.R;
import com.jvtc.catcampus_teacher.data.model.HomeItem;
import com.jvtc.catcampus_teacher.ui.ClassQuery.ClassQueryActivity;
import com.jvtc.catcampus_teacher.ui.login.LoginActivity2;
import com.jvtc.catcampus_teacher.ui.roster.RosterActivity;
import com.jvtc.catcampus_teacher.ui.updatepass.JwUpdatepassActivity;
import com.jvtc.catcampus_teacher.util.RoundedCornersTransform;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.transformer.ScaleInTransformer;

import java.util.List;

public class HomeFragment extends Fragment {
    private View root;
    private HomeViewModel homeViewModel;
    private Banner homeBanner;
    private TextView homeTotayaccess;
    private TextView homeSumuser;
    private TextView homeTotayactive;
    private RecyclerView homeRecy;
    private HomeItemAdapter homeItemAdapter;
    private Intent intent;
    private ImageView person;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        initData();
        return root;
    }

    private void initData() {
        homeViewModel.getHomeItems().observe(getViewLifecycleOwner(), new Observer<List<HomeItem>>() {
            @Override
            public void onChanged(List<HomeItem> homeItems) {
                homeItemAdapter = new HomeItemAdapter(homeItems, itemClick);
                GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
                homeRecy.setLayoutManager(manager);
                homeRecy.setAdapter(homeItemAdapter);
            }
        });
        homeViewModel.getTodaydata().observe(getViewLifecycleOwner(), new Observer<HomeViewModel.TodayData>() {
            @Override
            public void onChanged(HomeViewModel.TodayData todayData) {
                homeTotayaccess.setText(String.valueOf(todayData.toDayApiNum));
                homeSumuser.setText(String.valueOf(todayData.toAllUserNum));
                homeTotayactive.setText(String.valueOf(todayData.toDayNewUserNum));
            }
        });
        homeViewModel.getHeadLivewData().observe(getViewLifecycleOwner(), new Observer<List<HomeViewModel.HeadItem>>() {
            @Override
            public void onChanged(List<HomeViewModel.HeadItem> headItems) {
                homeBanner.setAdapter(new BannerImageAdapter<HomeViewModel.HeadItem>(headItems) {
                    @Override
                    public void onBindView(BannerImageHolder holder, HomeViewModel.HeadItem data, int position, int size) {
                        RoundedCornersTransform transform = new RoundedCornersTransform(holder.itemView.getContext(), 50);
                        transform.setNeedCorner(true, true, true, true);
                        RequestOptions options = new RequestOptions().placeholder(R.mipmap.logo_jiu_black_mini).transform(transform);
                        Glide.with(holder.itemView)
                                .load(data.imageurl)
                                .apply(options)
                                .into(holder.imageView);
                    }
                });
                homeBanner.setPageTransformer(new ScaleInTransformer());
                homeBanner.start();
            }
        });
        homeViewModel.initTodayData();
        homeViewModel.initHead();
    }

    private HomeItemAdapter.ItemClick itemClick = new HomeItemAdapter.ItemClick() {
        @Override
        public void click(HomeItem homeItem) {
            switch (homeItem.getName()) {
                case "班级花名册":
                    //班级花名册
                    intent = new Intent(getActivity(), RosterActivity.class);
                    startActivity(intent);
                    break;
                case "教室课表查询":
                    //教室课表查询
                    intent = new Intent(getActivity(), ClassQueryActivity.class);
                    startActivity(intent);
                    break;
                case "教务系统密码修改":
                    intent = new Intent(getActivity(), JwUpdatepassActivity.class);
                    startActivity(intent);
                    break;
                case "学生密码修改":
                    JumpTarget("学生密码修改");
                    break;
                case "日常请假审核":
                    JumpTarget("日常请假审核");
                    break;
                case "日常销假管理":
                    JumpTarget("日常销假管理");
                    break;
                case "学工密码修改":
                    JumpTarget("学工密码修改");
                    break;
            }
        }
    };

    private void JumpTarget(String value) {
        intent = new Intent(getActivity(), LoginActivity2.class);
        intent.putExtra("target", value);
        startActivity(intent);
    }

    private void initView() {
        homeBanner = (Banner) root.findViewById(R.id.home_banner);
        homeTotayaccess = (TextView) root.findViewById(R.id.home_totayaccess);
        homeSumuser = (TextView) root.findViewById(R.id.home_sumuser);
        homeTotayactive = (TextView) root.findViewById(R.id.home_totayactive);
        homeRecy = (RecyclerView) root.findViewById(R.id.home_recy);
        person = (ImageView) root.findViewById(R.id.person);

        person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).getDrawer().openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        homeBanner.destroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        homeBanner.stop();
    }
}