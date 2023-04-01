package com.atinytot.vegsp_v_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.atinytot.vegsp_v_1.adapter.FragmentAdapter;
import com.atinytot.vegsp_v_1.databinding.ActivityMainBinding;
import com.atinytot.vegsp_v_1.ui.display.DisplayFragment;
import com.atinytot.vegsp_v_1.ui.setting.SettingFragment;
import com.atinytot.vegsp_v_1.ui.video.VideoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.leaf.library.StatusBarUtil;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    // 底部导航栏+滑动视图
    private ViewPager2 myViewPager;
    private BottomNavigationView navbar;
    // 侧边导航栏
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    // 关联fragment
    private List<String> data;
    private List<Fragment> fragmentList;
    private FragmentAdapter fragmentAdapter;


    // 记录当前的页面位置
    public int position = 0;
    private DisplayFragment displayFragment;
    private VideoFragment switchFragment;
    private SettingFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        getNotchParams();

        // 初始化
        initView();
        // 添加fragment列表进入viewpager
        initTab();
//        StatusBarUtil.setGradientColor(this, toolbar);
        // TODO 重点 设置 bottomNavigationView 的item 的点击事件 设置viewPager2的联动
        navbar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // 关联fragment
                switch (item.getItemId()) {
                    case R.id.item_show:
                        toolbar.setTitle("环境");
                        myViewPager.setCurrentItem(0);
                        break;
                    case R.id.item_circuit:
                        toolbar.setTitle("监控");
                        myViewPager.setCurrentItem(1);
                        break;
                    case R.id.item_install:
                        toolbar.setTitle("用户数据");
                        myViewPager.setCurrentItem(2);
                        break;
                }

                return true;
            }
        });
        // TODO 重点 实现滑动的时候 联动 bottomNavigationView的selectedItem
        myViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        navbar.setSelectedItemId(R.id.item_show);
                        break;
                    case 1:
                        navbar.setSelectedItemId(R.id.item_circuit);
                        break;
                    case 2:
                        navbar.setSelectedItemId(R.id.item_install);
                        break;
                }
            }
        });
        // TODO 重点 实现侧边导航栏
        toolbar.setTitle("环境");
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, androidx.navigation.ui.R.string.nav_app_bar_open_drawer_description, androidx.navigation.ui.R.string.nav_app_bar_navigate_up_description);
        // 同步 替换原有按钮
        toggle.syncState();
        // 侧拉布局和按钮关联监听
        drawerLayout.addDrawerListener(toggle);
        // 开启手势滑动打开侧滑菜单栏，如果要关闭将后面的UNLOCKED替换成LOCKED_CLOSED就可以了
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // 记录当前位置
                position = myViewPager.getCurrentItem();
                Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();

                // fragment事务
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.detach(displayFragment).attach(displayFragment).
                        detach(switchFragment).attach(switchFragment).
                        detach(settingFragment).attach(settingFragment).commit();
                switch (item.getItemId()) {
                    case R.id.vegetable_plot_one:
                        myViewPager.setCurrentItem(position);
                        navbar.getMenu().getItem(position).setChecked(true);
                        break;
                    case R.id.vegetable_plot_two:
                        myViewPager.setCurrentItem(position);
                        navbar.getMenu().getItem(position).setChecked(true);
                        break;
                    case R.id.vegetable_plot_three:
                        myViewPager.setCurrentItem(position);
                        navbar.getMenu().getItem(position).setChecked(true);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @TargetApi(28)
    public void getNotchParams() {
        final View decorView = getWindow().getDecorView();
        decorView.post(new Runnable() {
            @Override
            public void run() {
                DisplayCutout displayCutout = decorView.getRootWindowInsets().getDisplayCutout();
                Log.e("TAG", "安全区域距离屏幕左边的距离 SafeInsetLeft:" + displayCutout.getSafeInsetLeft());
                Log.e("TAG", "安全区域距离屏幕右部的距离 SafeInsetRight:" + displayCutout.getSafeInsetRight());
                Log.e("TAG", "安全区域距离屏幕顶部的距离 SafeInsetTop:" + displayCutout.getSafeInsetTop());
                Log.e("TAG", "安全区域距离屏幕底部的距离 SafeInsetBottom:" + displayCutout.getSafeInsetBottom());
                List<Rect> rects = displayCutout.getBoundingRects();
                if (rects == null || rects.size() == 0) {
                    Log.e("TAG", "不是刘海屏");
                } else {
                    Log.e("TAG", "刘海屏数量:" + rects.size());
                    for (Rect rect : rects) {
                        Log.e("TAG", "刘海屏区域：" + rect);
                    }
                }
            }
        });
    }

    /**
     * 初始化UI控件
     */
    private void initView() {

        // 实例化数据储存列表
        data = new ArrayList<>();

        // 实例化fragment
        displayFragment = DisplayFragment.newInstance();
        switchFragment = VideoFragment.newInstance();
        settingFragment = SettingFragment.newInstance();

        // 底部导航栏，滑动视图
        myViewPager = binding.appBarMain.myViewPager;
        navbar = binding.appBarMain.navbar;

        // 侧边导航栏
        drawerLayout = binding.drawerLayout;
        navigationView = binding.navView;
        toolbar = binding.appBarMain.toolbar;
    }

    /**
     * 显示 显示区
     */
    private void initTab() {
        // 添加三个fragment
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(displayFragment);
        fragmentList.add(switchFragment);
        fragmentList.add(settingFragment);

        // fragment适配器
        fragmentAdapter = new FragmentAdapter(this, fragmentList);
        // 设置预加载页面数量：一个是代表预加载的数量：n，一个是缓存的最大数量：2*n+1
        myViewPager.setOffscreenPageLimit(3);
        // 设置适配器
        myViewPager.setAdapter(fragmentAdapter);
    }

    /**
     * 避免在按Back键时关闭程序
     */
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
}