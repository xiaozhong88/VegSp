package com.atinytot.vegsp_v_1.ui.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.atinytot.vegsp_v_1.R
import com.atinytot.vegsp_v_1.base.BaseFragment
import com.atinytot.vegsp_v_1.databinding.FragmentRegisterBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>(
    FragmentRegisterBinding::inflate,
    RegisterViewModel::class.java
) {

    private var nickname: TextInputEditText? = null
    private var username: TextInputEditText? = null
    private var password: TextInputEditText? = null
    private var password_again: TextInputEditText? = null
    private var register: MaterialButton? = null
    private var nick_s: String? = null
    private var user_s: String? = null
    private var pass_s: String? = null
    private var passa_s: String? = null

    override fun initFragment(
        binding: FragmentRegisterBinding,
        viewModel: RegisterViewModel?,
        savedInstanceState: Bundle?
    ) {
        nickname = binding.registerNickname
        username = binding.registerUsername
        password = binding.registerPassword
        password_again = binding.registerPasswordAgain
        register = binding.register

        viewModel?.apply {
            this.data.observe(viewLifecycleOwner) { user ->
                if (user != null) {
                    Toast.makeText(requireContext(), "用户已存在", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "注册成功", Toast.LENGTH_SHORT).show();
                }
            }
        }

        nickname!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                nick_s = charSequence.toString()
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        username!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                user_s = charSequence.toString()
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        password!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                pass_s = charSequence.toString()
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        password_again!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                passa_s = charSequence.toString()
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        register!!.setOnClickListener { view: View? ->
            if (pass_s != passa_s) {
                Toast.makeText(requireContext(), "两次密码不一致", Toast.LENGTH_SHORT).show()
            } else {
                viewModel?.registerUser(user_s, pass_s)
                findNavController().navigate(
                    R.id.action_registerFragment_to_loginFragment,
//                    null,
//                    NavOptions.Builder().setPopUpTo(R.id.registerFragment, true).build()
                )
            }
        }
    }
}