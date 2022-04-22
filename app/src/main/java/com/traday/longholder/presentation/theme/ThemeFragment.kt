package com.traday.longholder.presentation.theme

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentThemeBinding
import com.traday.longholder.presentation.base.BaseMVVMFragment
import com.traday.longholder.presentation.base.WindowBackgroundMode

class ThemeFragment : BaseMVVMFragment<ThemeViewModel, FragmentThemeBinding>(
    layoutResId = R.layout.fragment_theme,
    windowBackgroundMode = WindowBackgroundMode.Secondary
) {

    override val binding: FragmentThemeBinding by viewBinding(FragmentThemeBinding::bind)

    override val viewModel: ThemeViewModel by viewModels()

    override fun initView(inflatedView: View, savedInstanceState: Bundle?) {
        initActionButtons()
    }

    private fun initActionButtons() {
        with(binding) {
            stTheme.setLeftActionOnCLickListener { navController.popBackStack() }
        }
    }

    override fun initViewModel() {}
}