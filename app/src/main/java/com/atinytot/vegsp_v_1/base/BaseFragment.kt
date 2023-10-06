package com.atinytot.vegsp_v_1.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.atinytot.vegsp_v_1.models.FragmentStateViewModel
import com.atinytot.vegsp_v_1.models.PublicViewModel

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB,
    private val viewModelClass: Class<VM>?,
    private val publicViewModelTag: Boolean = false
) : Fragment() {
    private var bufferRootView: View? = null
    private var binding: VB? = null

    private val viewModel by lazy {
        val viewModelProvider = ViewModelProvider(
            this
        )
        viewModelClass?.let {
            viewModelProvider[it]
        }
    }

    val publicViewModel: PublicViewModel? by lazy {
        if (publicViewModelTag) {
            // 共享的
            ViewModelProvider(requireActivity())[PublicViewModel::class.java]
        } else {
            null
        }
    }

    private val fragmentStateViewModel: FragmentStateViewModel by lazy {
        ViewModelProvider(requireActivity())[FragmentStateViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        // 获取当前 Fragment 的唯一标签
//        val fragmentTag = getFragmentTag()
//        // 检查是否存在具有相同标签的现有 Fragment 实例
//        val existingFragment = parentFragmentManager.findFragmentByTag(fragmentTag)
//        if (existingFragment != null) {
//            // 如果存在现有 Fragment 实例，直接返回其视图
//            return existingFragment.view
//        }
        bufferRootView?.let {
            return bufferRootView
        }
        binding = inflate(inflater, container, false)
        // 通过 ViewBinding 创建 Fragment 的视图
        initFragment(binding!!, viewModel, savedInstanceState)
        bufferRootView = binding!!.root
        return bufferRootView
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        // 获取当前 Fragment 的标签
        val fragmentTag = getFragmentTag()

        // 从 FragmentStateViewModel 中获取保存的状态
        val fragmentState = fragmentStateViewModel.getFragmentState(fragmentTag = fragmentTag)
        fragmentState?.let {
            // 如果存在保存的状态，恢复到 Fragment 中
            restoreFragmentState(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // 获取当前 Fragment 的标签
        val fragmentTag = getFragmentTag()

        // 保存 Fragment 的状态到 FragmentStateViewModel 中
        val fragmentState = saveFragmentState()
        fragmentStateViewModel.saveFragmentState(fragmentTag = fragmentTag, fragmentState)
        Log.e("BaseFragment", "onSaveInstanceState: " + fragmentStateViewModel.getFragmentState(fragmentTag))
    }

    // 获取当前 Fragment 的标签
    private fun getFragmentTag(): String {
        return this::class.java.simpleName
    }

    // Fragment内容初始化
    abstract fun initFragment(
        binding: VB,
        viewModel: VM?,
        savedInstanceState: Bundle?
    )

    abstract fun restoreFragmentState(state: Bundle)

    open fun saveFragmentState(): Bundle {
        return Bundle().apply {
            putString("state", "su")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bufferRootView = null
        binding = null
    }
}