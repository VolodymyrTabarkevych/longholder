package com.traday.longholder.presentation.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentSignUpEmailBinding
import com.traday.longholder.presentation.base.BaseMVVMFragment

class SignUpEmailFragment : BaseMVVMFragment<SignUpEmailViewModel, FragmentSignUpEmailBinding>(
    R.layout.fragment_sign_up_email
) {

    override val binding: FragmentSignUpEmailBinding by viewBinding(FragmentSignUpEmailBinding::bind)

    override val viewModel: SignUpEmailViewModel by viewModels()

    override fun initView(inflatedView: View, args: Bundle?) {
        binding.stSignUp.setLeftActionOnCLickListener { navController.popBackStack() }
    }

    override fun initViewModel() {}
}