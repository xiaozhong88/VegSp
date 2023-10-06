package com.atinytot.vegsp_v_1.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.viewpager2.widget.ViewPager2;

import com.atinytot.vegsp_v_1.R;
import com.atinytot.vegsp_v_1.adapter.FragmentAdapter;
import com.atinytot.vegsp_v_1.databinding.ActivityMainBinding;
import com.atinytot.vegsp_v_1.domain.ModeEvent;
import com.atinytot.vegsp_v_1.mould.newton.NewtonCradleLoading;
import com.atinytot.vegsp_v_1.ui.display.DisplayFragment;
import com.atinytot.vegsp_v_1.ui.ranging.RangingFragment;
import com.atinytot.vegsp_v_1.ui.setting.SettingActivity;
import com.atinytot.vegsp_v_1.ui.video.VideoFragment;
import com.flod.drawabletextview.DrawableTextView;
import com.flod.loadingbutton.LoadingButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    // 底部导航栏+滑动视图
//    private ViewPager2 myViewPager;
//    private BottomNavigationView navbar;
//    // 侧边导航栏
//    private DrawerLayout drawerLayout;
//    private NavigationView navigationView;
//    private Toolbar toolbar;
//    private TextView nickname;
//    private String nick_s;
//    // 关联fragment
//    private List<String> data;
//    private List<Fragment> fragmentList;
//    private FragmentAdapter fragmentAdapter;
//
//    // 记录当前的页面位置
//    public int position = 0;
//    private DisplayFragment displayFragment;
//    private VideoFragment switchFragment;
//    private RangingFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Log.e("MainActivity", "onCreate: " + savedInstanceState);
        super.onCreate(savedInstanceState);

//        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(ActivityMainBinding.inflate(getLayoutInflater()).getRoot());
//        FragmentManager fragmentManager = getFragmentManager();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            Log.e("MainActivity", "onCreate: " + fragmentManager.getPrimaryNavigationFragment());
//        }
        if (savedInstanceState != null) {
            Bundle save = savedInstanceState.getBundle("androidx.lifecycle.BundlableSavedStateRegistry.key");
            // 获取包含片段信息的 Bundle
            Bundle fragmentBundle = save.getBundle("android:support:fragments");
//            Log.e("MainActivity", "onCreate: " + save.toString());

            if (fragmentBundle != null) {
                // 获取特定片段的状态
                Bundle fragmentState = fragmentBundle.getBundle("state");

                if (fragmentState != null) {
                    // 获取片段标识符（tag）
                    String tag = fragmentState.getString("tag");

//                    Log.e("MainActivity", "onCreate: " + fragmentState.toString() + tag);
                }
            }
        }

//        drawerLayout = binding.app.findViewById(R.id.drawer_layout);
//        getNotchParams();

//        // TODO 初始化
//        initView();
//        // TODO 添加fragment列表进入viewpager
//        initTab();
////        StatusBarUtil.setGradientColor(this, toolbar);
//        // TODO 重点 设置 bottomNavigationView 的item 的点击事件 设置viewPager2的联动
//        navbar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @SuppressLint("NonConstantResourceId")
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                // 关联fragment
//                switch (item.getItemId()) {
//                    case R.id.item_display:
//                        toolbar.setTitle("环境");
//                        myViewPager.setCurrentItem(0);
//                        break;
//                    case R.id.item_video:
//                        toolbar.setTitle("监控");
//                        myViewPager.setCurrentItem(1);
//                        break;
//                    case R.id.item_ranging:
//                        toolbar.setTitle("测距");
//                        myViewPager.setCurrentItem(2);
//                        break;
//                }
//
//                return true;
//            }
//        });
//        // TODO 重点 实现滑动的时候 联动 bottomNavigationView的selectedItem
//        myViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                switch (position) {
//                    case 0:
//                        navbar.setSelectedItemId(R.id.item_display);
//                        break;
//                    case 1:
//                        navbar.setSelectedItemId(R.id.item_video);
//                        break;
//                    case 2:
//                        navbar.setSelectedItemId(R.id.item_ranging);
//                        break;
//                }
//            }
//        });
//        // TODO 重点 实现侧边导航栏
//        toolbar.setTitle("环境");
//        setSupportActionBar(toolbar);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, androidx.navigation.ui.R.string.nav_app_bar_open_drawer_description, androidx.navigation.ui.R.string.nav_app_bar_navigate_up_description);
//        // 同步 替换原有按钮
//        toggle.syncState();
//        // 侧拉布局和按钮关联监听
//        drawerLayout.addDrawerListener(toggle);
//        // 开启手势滑动打开侧滑菜单栏，如果要关闭将后面的UNLOCKED替换成LOCKED_CLOSED就可以了
//        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//
//        navigationView.bringToFront();
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @SuppressLint("NonConstantResourceId")
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                // 记录当前位置
//                position = myViewPager.getCurrentItem();
//                Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
//
//                // fragment事务
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
////                ft.detach(displayFragment).attach(displayFragment).
////                        detach(switchFragment).attach(switchFragment).
////                        detach(settingFragment).attach(settingFragment).commit();
//                switch (item.getItemId()) {
//                    case R.id.set_up:
//                        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
//                        startActivity(intent);
//                    case R.id.vegetable_plot_one:
//                        myViewPager.setCurrentItem(position);
//                        navbar.getMenu().getItem(position).setChecked(true);
//                        break;
//                    case R.id.vegetable_plot_two:
//                        myViewPager.setCurrentItem(position);
//                        navbar.getMenu().getItem(position).setChecked(true);
//                        break;
//                    case R.id.vegetable_plot_three:
//                        myViewPager.setCurrentItem(position);
//                        navbar.getMenu().getItem(position).setChecked(true);
//                        break;
//                    default:
//                        break;
//                }
//                return true;
//            }
//        });
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 会保留 newConfig.uiMode 的低两位（也就是白天黑夜模式位），UI_MODE_NIGHT_MASK 是一个位掩码常量
        int newMode = newConfig.uiMode & Configuration.UI_MODE_NIGHT_MASK;

//        switch (newMode) {
//            case Configuration.UI_MODE_NIGHT_UNDEFINED:
//                // 未定义模式，可以根据需要进行处理
//                break;
//            case Configuration.UI_MODE_NIGHT_NO:
//                // 白天模式
////                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                Log.e("updateTheme", "updateTheme: " + "白天模式");
//                break;
//            case Configuration.UI_MODE_NIGHT_YES:
//                // 黑夜模式
////                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                Log.e("updateTheme", "updateTheme: " + "黑夜模式");
//                break;
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
//        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onModeEvent(@NonNull ModeEvent modeEvent) {
//        if (modeEvent.getMode() == Configuration.UI_MODE_NIGHT_NO) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        } else if (modeEvent.getMode() == Configuration.UI_MODE_NIGHT_YES) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        } else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
//        }
//    }

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
//        data = new ArrayList<>();
//
//        // 实例化fragment
//        displayFragment = DisplayFragment.newInstance();
//        switchFragment = VideoFragment.newInstance();
//        settingFragment = RangingFragment.newInstance();

        // 底部导航栏，滑动视图
//        myViewPager = binding.appBarMain.myViewPager;
//        navbar = binding.appBarMain.navbar;
//
//        // 侧边导航栏
//        drawerLayout = binding.drawerLayout;
//        navigationView = binding.navView;
//        toolbar = binding.appBarMain.toolbar;
//        nickname = navigationView.getHeaderView(0).findViewById(R.id.nickname);
    }

    /**
     * 显示 显示区
     */
    private void initTab() {
        // 添加三个fragment
//        fragmentList = new ArrayList<Fragment>();
//        fragmentList.add(displayFragment);
//        fragmentList.add(switchFragment);
//        fragmentList.add(settingFragment);
//
//        // fragment适配器
//        fragmentAdapter = new FragmentAdapter(this, fragmentList);
//        // 设置预加载页面数量：一个是代表预加载的数量：n，一个是缓存的最大数量：2*n+1
//        myViewPager.setOffscreenPageLimit(3);
//        // 设置适配器
//        myViewPager.setAdapter(fragmentAdapter);
    }

    /**
     * 避免在按Back键时关闭程序
     */
//    @Override
//    public void onBackPressed() {
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

    /**
     * TODO 获取昵称
     * 使用sticky保证数据的接收
     *
     * @param msg 昵称
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(String msg) {
//        nickname.setText(msg);
    }

}