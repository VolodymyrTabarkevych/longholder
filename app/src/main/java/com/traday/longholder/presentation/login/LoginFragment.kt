package com.traday.longholder.presentation.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentLoginBinding
import com.traday.longholder.extensions.navigateSafe
import com.traday.longholder.presentation.base.BaseMVVMFragment

class LoginFragment : BaseMVVMFragment<LoginViewModel, FragmentLoginBinding>(
    R.layout.fragment_login
) {

    override val binding: FragmentLoginBinding by viewBinding(FragmentLoginBinding::bind)

    override val viewModel: LoginViewModel by viewModels()

    override fun initView(inflatedView: View, savedInstanceState: Bundle?) {
        with(binding) {
            stLogin.setLeftActionOnCLickListener { navController.popBackStack() }
            pbLogin.setOnClickListener { navController.navigateSafe(LoginFragmentDirections.actionGlobalOnboardingFragment()) }
        }
    }

    override fun initViewModel() {}
}