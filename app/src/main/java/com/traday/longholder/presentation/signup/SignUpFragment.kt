package com.traday.longholder.presentation.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentSignUpBinding
import com.traday.longholder.presentation.base.BaseMVVMFragment

class SignUpFragment : BaseMVVMFragment<SignUpViewModel, FragmentSignUpBinding>(
    R.layout.fragment_sign_up
) {

    override val binding: FragmentSignUpBinding by viewBinding(FragmentSignUpBinding::bind)

    override val viewModel: SignUpViewModel by viewModels()

    override fun initView(inflatedView: View, args: Bundle?) {
        binding.stSignUp.setLeftActionOnCLickListener { navController.popBackStack() }
    }

    override fun initViewModel() {}
}