package com.traday.longholder.presentation.signup_name

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentSignUpNameBinding
import com.traday.longholder.extensions.navigateSafe
import com.traday.longholder.presentation.base.BaseMVVMFragment

class SignUpNameFragment : BaseMVVMFragment<SignUpNameViewModel, FragmentSignUpNameBinding>(
    R.layout.fragment_sign_up_name
) {

    override val binding: FragmentSignUpNameBinding by viewBinding(FragmentSignUpNameBinding::bind)

    override val viewModel: SignUpNameViewModel by viewModels()

    override fun initView(inflatedView: View, args: Bundle?) {
        with(binding) {
            stSignUpName.setLeftActionOnCLickListener { navController.popBackStack() }
            stSignUpName.setRightActionOnClickListener {
                navController.navigateSafe(
                    SignUpNameFragmentDirections.actionSignUpNameFragmentToSingUpPasswordFragment()
                )
            }
            pbSignUpNameNext.setOnClickListener {
                pbSignUpNameNext.setLoading(true)
                mainHandler.postDelayed({
                    pbSignUpNameNext.setLoading(false)
                    navController.navigateSafe(
                        SignUpNameFragmentDirections.actionSignUpNameFragmentToSingUpPasswordFragment()
                    )
                }, 500)
            }
        }
    }

    override fun initViewModel() {}
}