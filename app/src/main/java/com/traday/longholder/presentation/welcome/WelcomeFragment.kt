package com.traday.longholder.presentation.welcome

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentWelcomeBinding
import com.traday.longholder.extensions.navigateSafe
import com.traday.longholder.presentation.base.BaseFragment

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(
    R.layout.fragment_welcome
) {

    override val binding: FragmentWelcomeBinding by viewBinding(FragmentWelcomeBinding::bind)

    private val viewModel: WelcomeViewModel by viewModels()

    override fun initView(inflatedView: View, savedInstanceState: Bundle?) {
        with(binding) {
            pbWelcomeRegistration.setOnClickListener {
                navController.navigateSafe(WelcomeFragmentDirections.actionWelcomeFragmentToSignUpFragment())
            }
            pbWelcomeLogin.setOnClickListener {
                navController.navigateSafe(WelcomeFragmentDirections.actionWelcomeFragmentToLoginFragment())
            }
        }
    }

    override fun initViewModel() {
        viewModel.checkIfLogoutNeeded()
    }
}