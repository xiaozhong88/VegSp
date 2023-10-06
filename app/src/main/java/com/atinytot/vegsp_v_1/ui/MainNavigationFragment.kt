package com.atinytot.vegsp_v_1.ui

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionManager
import androidx.viewpager2.widget.ViewPager2
import com.atinytot.vegsp_v_1.R
import com.atinytot.vegsp_v_1.adapter.FragmentAdapter
import com.atinytot.vegsp_v_1.base.BaseFragment
import com.atinytot.vegsp_v_1.databinding.FragmentMainNavigationBinding
import com.atinytot.vegsp_v_1.domain.ModeEvent
import com.atinytot.vegsp_v_1.mould.CenterTitleToolbar
import com.atinytot.vegsp_v_1.mould.newton.NewtonCradleLoading
import com.atinytot.vegsp_v_1.ui.display.DisplayFragment
import com.atinytot.vegsp_v_1.ui.ranging.RangingFragment
import com.atinytot.vegsp_v_1.ui.setting.SettingActivity
import com.atinytot.vegsp_v_1.ui.video.VideoFragment
import com.atinytot.vegsp_v_1.utils.ThemeSettings
import com.flod.loadingbutton.LoadingButton
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.vimalcvs.switchdn.DayNightSwitch
import com.vimalcvs.switchdn.DayNightSwitchAnimListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainNavigationFragment : BaseFragment<FragmentMainNavigationBinding, ViewModel>(
    FragmentMainNavigationBinding::inflate,
    null
) {

    private val TAG = "MainNavigationFragment"
    private lateinit var binding: FragmentMainNavigationBinding

    private lateinit var mViewPage: ViewPager2
    private lateinit var navbar: BottomNavigationView
    private lateinit var fragmentAdapter: FragmentAdapter

    // 功能区
    private var displayFragment: DisplayFragment? = null
    private var videoFragment: VideoFragment? = null
    private var rangingFragment: RangingFragment? = null

    // 侧边导航栏
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: CenterTitleToolbar
    private lateinit var nickname: TextView
    private lateinit var nick_s: String
    private lateinit var btn: LoadingButton

    // 白天/黑夜模式切换
    private lateinit var dayNightSwitch: DayNightSwitch
    private lateinit var nightMode: TextView
    private lateinit var daySky: View
    private lateinit var nightSky: View
    private var currentFragmentId = R.id.displayFragment

    // 记录当前的页面位置
    var position = 0
    val tb = listOf<String>("环境", "监控", "作物状况")

    //    private lateinit var spinKitView: TilesFrameLayout
    private lateinit var newtonCradleLoading: NewtonCradleLoading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "onCreate: " + savedInstanceState.toString())
    }

    override fun initFragment(
        binding: FragmentMainNavigationBinding,
        viewModel: ViewModel?,
        savedInstanceState: Bundle?
    ) {
//        if (savedInstanceState != null) {
//            // 如果切换了深色/浅色模式 将之前存放的 fragment 状态赋予新建的 Fragment
//            displayFragment = childFragmentManager.getFragment(savedInstanceState, "DisplayFragment")
//        }
        this.binding = binding

        // TODO 初始化
        initView()
        // TODO 过渡动画
        if (!requireContext().getSharedPreferences("main_mode_config", MODE_PRIVATE)
                .getBoolean("day_and_night", false) and (savedInstanceState == null)
        ) {
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
        } else {
            val fadeOutAnimation = AlphaAnimation(1.0f, 0.0f)
            fadeOutAnimation.duration = 1000
            Log.e(TAG, "initFragment: " + ThemeSettings.getInstance(requireContext()).nightMode)
            if (!ThemeSettings.getInstance(requireContext()).nightMode) {
                daySky.visibility = View.VISIBLE
                nightSky.visibility = View.VISIBLE
                drawerLayout.visibility = View.GONE

                nightSky.animation = fadeOutAnimation
                nightSky.startAnimation(fadeOutAnimation)

                // 设置动画监听器，以便在动画结束后隐藏视图
                fadeOutAnimation.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(p0: Animation?) {

                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        nightSky.visibility = View.GONE
                    }

                    override fun onAnimationRepeat(p0: Animation?) {

                    }
                })

                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {

                        delay(1000)
                        // TODO 添加fragment列表进入viewpager
                        initTab()

//                        delay(600)
                        daySky.visibility = View.GONE
                        drawerLayout.visibility = View.VISIBLE
                    }
                }
            } else {

                nightSky.elevation = 1f
                daySky.elevation = 2f

                nightSky.visibility = View.VISIBLE
                daySky.visibility = View.VISIBLE
                drawerLayout.visibility = View.GONE

                daySky.animation = fadeOutAnimation
                daySky.startAnimation(fadeOutAnimation)

                // 设置动画监听器，以便在动画结束后隐藏视图
                fadeOutAnimation.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(p0: Animation?) {

                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        daySky.visibility = View.GONE
                    }

                    override fun onAnimationRepeat(p0: Animation?) {

                    }
                })

                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {

                        delay(1000)
                        // TODO 添加fragment列表进入viewpager
                        initTab()

//                        delay(600)
                        nightSky.visibility = View.GONE
                        drawerLayout.visibility = View.VISIBLE
                    }
                }
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
        )
        // 同步 替换原有按钮
        toggle.syncState()
        // 侧拉布局和按钮关联监听
        drawerLayout.addDrawerListener(toggle)
        // 开启手势滑动打开侧滑菜单栏，如果要关闭将后面的UNLOCKED替换成LOCKED_CLOSED就可以了
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

        navigationView.bringToFront()
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

        findNavController().addOnDestinationChangedListener { _, destination, _ ->
            currentFragmentId = destination.id
        }
        // 获取当前模式状态
        val isNightMode =
            (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK).run {
                if (this == 16) false
                else this == 32
            }
        if (!isNightMode) {
            nightMode.text = "白天"
        } else {
            nightMode.text = "黑夜"
        }
        dayNightSwitch.setDuration(450)
        dayNightSwitch.setIsNight(isNightMode)
        dayNightSwitch.setListener { isNight ->
            ThemeSettings.getInstance(requireContext()).nightMode = isNight
            ThemeSettings.getInstance(requireContext()).refreshTheme()

//            val nightModeEditor: SharedPreferences.Editor =
//                requireContext().getSharedPreferences("night_mode", MODE_PRIVATE).edit()
//            nightModeEditor.putBoolean("isNight", isNight)
//            nightModeEditor.apply()

            val mainEditor: SharedPreferences.Editor =
                requireContext().getSharedPreferences("main_mode_config", MODE_PRIVATE).edit()
            mainEditor.putBoolean("day_and_night", true)
            mainEditor.apply()
        }
//        dayNightSwitch.setAnimListener(object : DayNightSwitchAnimListener {
//            override fun onAnimStart() {
//
//            }
//
//            override fun onAnimEnd() {
//
//            }
//
//            override fun onAnimValueChanged(value: Float) {
//
//            }
//
//        })
    }

    override fun restoreFragmentState(state: Bundle) {
//        val fragmentManager = childFragmentManager
//        val fragmentTag = state.getString(this::class.java.simpleName)
//        val fragment = fragmentManager.findFragmentByTag(fragmentTag)
//        if (fragment != null) {
//            // 恢复保存的 Fragment 实例
//            val restoredFragment = fragment as MainNavigationFragment
//            // 进行相应的处理，例如将恢复的 Fragment 实例添加到视图中
//            fragmentManager.beginTransaction().apply {
//                hideFragment(this)
//                this.show(displayFragment!!)
//                this.commit()
//            }
//        }

        Log.e(TAG, "restoreFragmentState: " + state)
    }

//    override fun saveFragmentState(): Bundle {
//
//        val bundle = Bundle()
////        val fragmentManager = childFragmentManager
////        val currentFragment = fragmentManager.findFragmentById(R.id.view)
////        fragmentManager.beginTransaction().apply {
////            if (currentFragment )
////        }
////        bundle.putParcelable("this")
//
//
//        return bundle
//    }


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

        // 白天/黑夜模式 切换
        dayNightSwitch = binding.switchItem
        nightMode = binding.nightMode
        daySky = binding.dayBg
        nightSky = binding.nightBg

//        spinKitView = binding.appBarMain.tilesFrameLayout
        newtonCradleLoading = binding.newtonCradleLoading

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        displayFragment = DisplayFragment.newInstance()
        videoFragment = VideoFragment.newInstance()
        rangingFragment = RangingFragment.newInstance()

        // 添加三个fragment
        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(displayFragment!!)
        fragmentList.add(videoFragment!!)
        fragmentList.add(rangingFragment!!)

        // fragment适配器
        fragmentAdapter = FragmentAdapter(requireActivity(), fragmentList)
    }

    /**
     * 显示 显示区
     */
    private fun initTab() {

        // 设置预加载页面数量：一个是代表预加载的数量：n，一个是缓存的最大数量：2*n+1
        mViewPage.setOffscreenPageLimit(3)
        // 设置适配器
        mViewPage.adapter = fragmentAdapter
        // 禁用保存和恢复视图状态的功能
//        mViewPage.isSaveEnabled = false
    }

    private fun hideFragment(transaction: FragmentTransaction) {
        if (displayFragment != null) {
            transaction.hide(displayFragment!!)
        }
        if (videoFragment != null) {
            transaction.hide(videoFragment!!)
        }
        if (rangingFragment != null) {
            transaction.hide(rangingFragment!!)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.e(TAG, "onPause: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy: ")

//        val nightEditor = requireContext().getSharedPreferences("night_mode", MODE_PRIVATE).edit()
//        nightEditor.remove("isNight")
//        nightEditor.apply()

        ThemeSettings.getInstance(requireContext()).save(requireContext())
        val mainModeEditor =
            requireContext().getSharedPreferences("main_mode_config", MODE_PRIVATE).edit()
        mainModeEditor.remove("day_and_night")
        mainModeEditor.apply()
    }
}