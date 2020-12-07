package com.jvtc.catcampus_teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.gyf.immersionbar.ImmersionBar;
import com.jvtc.catcampus_teacher.ui.course.CourseFragment;
import com.jvtc.catcampus_teacher.ui.home.HomeFragment;
import com.jvtc.catcampus_teacher.ui.hot.HotFragment;
import com.jvtc.catcampus_teacher.ui.notice.NoticeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 调课
 * 1。班级花名册
 * 2。教师课表查询
 * 3。教室课表查询
 * 4。教务系统密码修改
 * 5。学工平台个人信息
 * 6。学生密码修改
 * 7。日常请假审核
 * 8。日常销假管理
 * 9。学工密码修改
 * 10。当前课表查询
 */
public class MainActivity extends AppCompatActivity {
    private List<Fragment> fragments;
    private ViewPager2 viewPager2;
    private BottomNavigationView navigationView;
    private DrawerLayout drawer;
    private NavigationView navView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).init();
        setContentView(R.layout.activity_main);
        initView();

        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new CourseFragment());
        fragments.add(new HotFragment());
        viewPager2.setAdapter(new MyFragmentStateAdapter(this, fragments));

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        viewPager2.setCurrentItem(0);
                        navigationView.getMenu().getItem(0).setChecked(true);
                        break;
                    case R.id.course:
                        viewPager2.setCurrentItem(1);
                        navigationView.getMenu().getItem(1).setChecked(true);
                        break;
                    case R.id.hot:
                        viewPager2.setCurrentItem(2);
                        navigationView.getMenu().getItem(2).setChecked(true);
                        break;
                }
                item.setChecked(false);
                return false;
            }
        });
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.msg:

                        break;
                    case R.id.notice:
                        intent = new Intent(MainActivity.this, NoticeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.setting:

                        break;
                }
                return true;
            }

        });
    }

    private void initView() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        viewPager2 = (ViewPager2) findViewById(R.id.nav_host_viewpager);
        navigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        navView = (NavigationView) findViewById(R.id.nav_view);
    }

    class MyFragmentStateAdapter extends FragmentStateAdapter {
        private FragmentActivity fragmentActivity;
        private List<Fragment> list;

        public MyFragmentStateAdapter(FragmentActivity fragmentActivity, List<Fragment> list) {
            super(fragmentActivity);
            this.fragmentActivity = fragmentActivity;
            this.list = list;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return list.get(position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    public DrawerLayout getDrawer() {
        return drawer;
    }

}