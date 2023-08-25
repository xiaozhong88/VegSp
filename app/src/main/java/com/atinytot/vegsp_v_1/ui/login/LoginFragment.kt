package com.atinytot.vegsp_v_1.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.atinytot.vegsp_v_1.R
import com.atinytot.vegsp_v_1.base.BaseFragment
import com.atinytot.vegsp_v_1.databinding.FragmentLoginBinding
import com.atinytot.vegsp_v_1.room.entity.User
import com.flod.loadingbutton.LoadingButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(
    FragmentLoginBinding::inflate,
    LoginViewModel::class.java
) {

    private var list: List<User>? = null
    private lateinit var username: TextInputEditText
    private lateinit var password: TextInputEditText

    //    private lateinit var login: MaterialButton
    private lateinit var login: LoadingButton
    private lateinit var register: MaterialTextView
    private var user_s: String? = null
    private var pass_s: String? = null
    private var isClicked = false

    override fun initFragment(
        binding: FragmentLoginBinding,
        viewModel: LoginViewModel?,
        savedInstanceState: Bundle?
    ) {
//        binding.btn.setOnClickListener {
//            findNavController(requireParentFragment()).navigate(R.id.action_loginFragment_to_displayFragment)
//        }
        list = ArrayList<User>()
        username = binding.username
        password = binding.password
        login = binding.login
        register = binding.register

        username.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                user_s = charSequence.toString()
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                pass_s = charSequence.toString()
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        var isSuccess = false
        viewModel!!.apply {
            val lifecycleOwner = viewLifecycleOwner
            this.getAllUsersLive().observe(lifecycleOwner, Observer { users ->
                if (!users.isNullOrEmpty()) {
                    (list as ArrayList<User>).addAll(users)
                    Log.i(
                        "pass:",
                        if ((list as ArrayList<User>).isEmpty()) "空" else list.toString()
                    )
                }
            })

            this.getUserLive().observe(lifecycleOwner) { user ->
                isSuccess = user != null && user_s != null && pass_s != null
            }
        }

        isClicked = false
        login.setOnClickListener { view ->
            isClicked = !isClicked
            view.isSelected = isClicked
            login.start()
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.findUser(user_s, pass_s)
                withContext(Dispatchers.Main) {
                    delay(1200)
                    if (isSuccess) {
                        // 加载动画完成
                        login.complete(true)
                        Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(
                            R.id.action_loginFragment_to_mainNavigationFragment
                        )
                    } else {
                        login.complete(false)
                        Toast.makeText(context, "账号或密码错误", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            // 隐藏键盘
//                val imm =
//                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                imm.hideSoftInputFromWindow(login.windowToken, 0)
        }

        register.setOnClickListener { view: View? ->
//            Toast.makeText(context, "注册", Toast.LENGTH_SHORT).show()
            findNavController().navigate(
                R.id.action_loginFragment_to_registerFragment
            )
//            val intent = Intent(context, RegisterActivity::class.java)
//            startActivity(intent)
        }
    }
}