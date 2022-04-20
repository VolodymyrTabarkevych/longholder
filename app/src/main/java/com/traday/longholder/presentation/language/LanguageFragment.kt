package com.traday.longholder.presentation.language

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentLanguageBinding
import com.traday.longholder.presentation.base.BaseMVVMFragment
import com.traday.longholder.presentation.base.StatusBarMode

class LanguageFragment : BaseMVVMFragment<LanguageViewModel, FragmentLanguageBinding>(
    layoutResId = R.layout.fragment_language,
    statusBarMode = StatusBarMode.Secondary
) {

    override val binding: FragmentLanguageBinding by viewBinding(FragmentLanguageBinding::bind)

    override val viewModel: LanguageViewModel by viewModels()

    override fun initView(inflatedView: View, args: Bundle?) {
        initActionButtons()
    }

    private fun initActionButtons() {
        binding.stLanguage.setLeftActionOnCLickListener { navController.popBackStack() }
    }

    override fun initViewModel() {}
}