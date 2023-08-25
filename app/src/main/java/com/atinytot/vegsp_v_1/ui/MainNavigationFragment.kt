package com.atinytot.vegsp_v_1.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.atinytot.vegsp_v_1.R
import com.atinytot.vegsp_v_1.adapter.FragmentAdapter
import com.atinytot.vegsp_v_1.base.BaseFragment
import com.atinytot.vegsp_v_1.databinding.FragmentMainNavigationBinding
import com.atinytot.vegsp_v_1.mould.CenterTitleToolbar
import com.atinytot.vegsp_v_1.mould.newton.NewtonCradleLoading
import com.atinytot.vegsp_v_1.ui.display.DisplayFragment
import com.atinytot.vegsp_v_1.ui.ranging.RangingFragment
import com.atinytot.vegsp_v_1.ui.setting.SettingActivity
import com.atinytot.vegsp_v_1.ui.video.VideoFragment
import com.flod.loadingbutton.LoadingButton
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.*


class MainNavigationFragment : BaseFragment<FragmentMainNavigationBinding, ViewModel>(
    FragmentMainNavigationBinding::inflate,
    null
) {

    private lateinit var binding: FragmentMainNavigationBinding
    private lateinit var mViewPage: ViewPager2
    private lateinit var navbar: BottomNavigationView

    // 侧边导航栏
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: CenterTitleToolbar
    private lateinit var nickname: TextView
    private lateinit var nick_s: String
    private lateinit var btn: LoadingButton

    // 记录当前的页面位置
    var position = 0
    val tb = listOf<String>("环境", "监控", "作物状况")

    //    private lateinit var spinKitView: TilesFrameLayout
    private lateinit var newtonCradleLoading: NewtonCradleLoading

    override fun initFragment(
        binding: FragmentMainNavigationBinding,
        viewModel: ViewModel?,
        savedInstanceState: Bundle?
    ) {
        this.binding = binding

        // TODO 初始化
        initView()
        // TODO 过渡动画
        newtonCradleLoading.visibility = View.VISIBLE
        newtonCradleLoading.setLoadingColor(R.color.teal_200)
        newtonCradleLoading.start()
        drawerLayout.visibility = View.GONE
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                delay(1200)
                // TODO 添加fragment列表进入viewpager
                initTab()

                newtonCradleLoading.visibility = View.GONE
                newtonCradleLoading.stop()
                drawerLayout.visibility = View.VISIBLE
            }
        }
        // TODO 重点 实现滑动的时候 联动 bottomNavigationView的selectedItem
        mViewPage.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val itemId = when (position) {
                        0 -> {
                            toolbar.title = tb[position]
                            R.id.displayFragment
                        }
                        1 -> {
                            toolbar.title = tb[position]
                            R.id.videoFragment
                        }
                        2 -> {
                            toolbar.title = tb[position]
                            R.id.rangingFragment
                        }
                        else -> return
                    }
                    if (navbar.selectedItemId != itemId) {
                        navbar.selectedItemId = itemId
                    }
                }
            }
        )
        // TODO 重点 设置 bottomNavigationView 的item 的点击事件 设置viewPager2的联动
        navbar.setOnItemSelectedListener { item ->
            val position = when (item.itemId) {
                R.id.displayFragment -> 0
                R.id.videoFragment -> 1
                R.id.rangingFragment -> 2
                else -> return@setOnItemSelectedListener false
            }
            if (mViewPage.currentItem != position) {
                mViewPage.currentItem = position
                toolbar.title = tb[position]
            }
            true
        }

        // TODO 重点 实现侧边导航栏
        toolbar.title = "环境";
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        val toggle: ActionBarDrawerToggle = ActionBarDrawerToggle(
            requireActivity(),
            drawerLayout,
            toolbar,
            androidx.navigation.ui.R.string.nav_app_bar_open_drawer_description,
            androidx.navigation.ui.R.string.nav_app_bar_navigate_up_description
        );
        // 同步 替换原有按钮
        toggle.syncState();
        // 侧拉布局和按钮关联监听
        drawerLayout.addDrawerListener(toggle);
        // 开启手势滑动打开侧滑菜单栏，如果要关闭将后面的UNLOCKED替换成LOCKED_CLOSED就可以了
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener { item -> // 记录当前位置
            position = mViewPage.currentItem
            Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show()

            // fragment事务
//            val ft: FragmentTransaction =
//                childFragmentManager.beginTransaction()
            when (item.itemId) {
                R.id.set_up -> {
                    val intent = Intent(context, SettingActivity::class.java)
                    startActivity(intent)
                    mViewPage.currentItem = position
                    navbar.menu.getItem(position).isChecked = true
                }
                R.id.vegetable_plot_one -> {
                    mViewPage.currentItem = position
                    navbar.menu.getItem(position).isChecked = true
                }
                R.id.vegetable_plot_two -> {
                    mViewPage.currentItem = position
                    navbar.menu.getItem(position).isChecked = true
                }
                R.id.vegetable_plot_three -> {
                    mViewPage.currentItem = position
                    navbar.menu.getItem(position).isChecked = true
                }
                else -> {}
            }
            true
        }
    }

    /**
     * 初始化UI控件
     */
    private fun initView() {

        // 实例化数据储存列表
//        data = java.util.ArrayList<String>()

        // 底部导航栏，滑动视图
        mViewPage = binding.appBarMain.myViewPager
        navbar = binding.appBarMain.navbar

        // 侧边导航栏
        drawerLayout = binding.drawerLayout
        navigationView = binding.navView
        toolbar = binding.appBarMain.toolbar
        nickname = navigationView.getHeaderView(0).findViewById(R.id.nickname)

//        spinKitView = binding.appBarMain.tilesFrameLayout
        newtonCradleLoading = binding.newtonCradleLoading
    }

    /**
     * 显示 显示区
     */
    private fun initTab() {
        // 添加三个fragment
        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(DisplayFragment.newInstance())
        fragmentList.add(VideoFragment.newInstance())
        fragmentList.add(RangingFragment.newInstance())

        // fragment适配器
        val fragmentAdapter = FragmentAdapter(requireActivity(), fragmentList)
        // 设置预加载页面数量：一个是代表预加载的数量：n，一个是缓存的最大数量：2*n+1
        mViewPage.setOffscreenPageLimit(3)
        // 设置适配器
        mViewPage.adapter = fragmentAdapter

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}