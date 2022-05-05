package com.traday.longholder.presentation.calculator

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentCalculatorBinding
import com.traday.longholder.presentation.base.BaseMVVMFragment
import com.traday.longholder.presentation.base.TabBarMode

class CalculatorFragment : BaseMVVMFragment<CalculatorViewModel, FragmentCalculatorBinding>(
    layoutResId = R.layout.fragment_calculator,
    tabBarMode = TabBarMode.INVISIBLE
) {

    override val binding: FragmentCalculatorBinding by viewBinding(FragmentCalculatorBinding::bind)

    override val viewModel: CalculatorViewModel by viewModels()

    override fun initView(inflatedView: View, savedInstanceState: Bundle?) {
        initActionButtons()
    }

    private fun initActionButtons() {
        with(binding) {
            stCalculator.setLeftActionOnCLickListener { navController.popBackStack() }
        }
    }

    override fun initViewModel() {}
}