package com.traday.longholder.presentation.analytics

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentAnalyticsBinding
import com.traday.longholder.presentation.base.BaseMVVMFragment
import com.traday.longholder.presentation.base.StatusBarMode
import com.traday.longholder.presentation.base.TabBarMode

class AnalyticsFragment : BaseMVVMFragment<AnalyticsViewModel, FragmentAnalyticsBinding>(
    layoutResId = R.layout.fragment_analytics,
    statusBarMode = StatusBarMode.Secondary,
    tabBarMode = TabBarMode.VISIBLE
) {

    override val binding: FragmentAnalyticsBinding by viewBinding(FragmentAnalyticsBinding::bind)

    override val viewModel: AnalyticsViewModel by viewModels()

    override fun initView(inflatedView: View, savedInstanceState: Bundle?) {}

    override fun initViewModel() {}
}