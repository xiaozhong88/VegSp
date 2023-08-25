package com.atinytot.vegsp_v_1.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.atinytot.vegsp_v_1.factory.MyViewModelFactory
import com.atinytot.vegsp_v_1.model.PublicViewModel

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bufferRootView?.let {
            return bufferRootView
        }
        binding = inflate(inflater, container, false)
        initFragment(binding!!, viewModel, savedInstanceState)
        bufferRootView = binding!!.root
        return binding!!.root
    }

    // Fragment内容初始化
    abstract fun initFragment(
        binding: VB,
        viewModel: VM?,
        savedInstanceState: Bundle?
    )

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}