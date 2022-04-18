package com.traday.longholder.presentation.welcome

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentWelcomeBinding
import com.traday.longholder.presentation.base.BaseMVVMFragment

class WelcomeFragment : BaseMVVMFragment<WelcomeViewModel, FragmentWelcomeBinding>(
    R.layout.fragment_welcome
) {

    override val binding: FragmentWelcomeBinding by viewBinding(FragmentWelcomeBinding::bind)

    override val viewModel: WelcomeViewModel by viewModels()

    override fun initView(inflatedView: View, args: Bundle?) {
    }

    override fun initViewModel() {
    }

}