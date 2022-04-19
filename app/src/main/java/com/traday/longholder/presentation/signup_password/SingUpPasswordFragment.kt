package com.traday.longholder.presentation.signup_password

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentSignUpPasswordBinding
import com.traday.longholder.presentation.base.BaseMVVMFragment

class SingUpPasswordFragment :
    BaseMVVMFragment<SingUpPasswordViewModel, FragmentSignUpPasswordBinding>(
        R.layout.fragment_sign_up_password
    ) {

    override val binding: FragmentSignUpPasswordBinding by viewBinding(FragmentSignUpPasswordBinding::bind)

    override val viewModel: SingUpPasswordViewModel by viewModels()

    override fun initView(inflatedView: View, args: Bundle?) {
        with(binding) {
            stSignUpPassword.setLeftActionOnCLickListener { navController.popBackStack() }
        }
    }

    override fun initViewModel() {}
}