package com.traday.longholder.presentation.onboarding

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.traday.longholder.NavMainDirections
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentOnboardingBinding
import com.traday.longholder.extensions.navigateSafe
import com.traday.longholder.presentation.base.BaseMVVMFragment

class OnboardingFragment : BaseMVVMFragment<OnboardingViewModel, FragmentOnboardingBinding>(
    R.layout.fragment_onboarding
) {

    override val binding: FragmentOnboardingBinding by viewBinding(FragmentOnboardingBinding::bind)

    override val viewModel: OnboardingViewModel by viewModels()

    private val onboardingAdapter: OnboardingAdapter by lazy { OnboardingAdapter() }

    override fun initView(inflatedView: View, args: Bundle?) {
        initActionButtons()
        initViewPager()
    }

    private fun initActionButtons() {
        with(binding) {
            pbOnboardingNext.setOnClickListener {
                val currentPage = vpOnboarding.currentItem
                val lastPage = onboardingAdapter.itemCount - 1
                val nextPage = if (currentPage < lastPage) currentPage + 1 else lastPage
                vpOnboarding.setCurrentItem(nextPage, true)
            }
            pbOnboardingSkip.setOnClickListener {
                navController.navigateSafe(NavMainDirections.actionGlobalNavWallet())
            }
            pbOnboardingStart.setOnClickListener {
                navController.navigateSafe(NavMainDirections.actionGlobalNavWallet())
            }
        }
    }

    private fun initViewPager() {
        with(binding) {
            vpOnboarding.adapter = onboardingAdapter
            vpOnboarding.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val isLastPage = position == onboardingAdapter.itemCount - 1
                    changeActionButton(isLastPage)
                }
            })
            TabLayoutMediator(tlOnboarding, vpOnboarding) { tab, _ ->
                tab.view.isClickable = false
            }.attach()
        }
    }

    override fun initViewModel() {}

    private fun changeActionButton(isLastPage: Boolean) {
        with(binding) {
            pbOnboardingNext.isVisible = !isLastPage
            pbOnboardingSkip.isVisible = !isLastPage
            sOnboarding.isVisible = isLastPage
            pbOnboardingStart.isVisible = isLastPage
        }
    }
}