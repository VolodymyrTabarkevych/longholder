package com.traday.longholder.presentation.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentOnboardingBinding
import com.traday.longholder.presentation.base.BaseMVVMFragment

class OnboardingFragment : BaseMVVMFragment<OnboardingViewModel, FragmentOnboardingBinding>(
    R.layout.fragment_onboarding
) {

    override val binding: FragmentOnboardingBinding by viewBinding(FragmentOnboardingBinding::bind)

    override val viewModel: OnboardingViewModel by viewModels()

    private val onboardingAdapter: OnboardingAdapter by lazy { OnboardingAdapter() }

    override fun initView(inflatedView: View, args: Bundle?) {
        initViewPager()
    }

    private fun initViewPager() {
        with(binding) {
            vpOnboarding.adapter = onboardingAdapter
            TabLayoutMediator(tlOnboarding, vpOnboarding) { tab, _ ->
                tab.view.isClickable = false
            }.attach()
        }
    }

    override fun initViewModel() {}
}