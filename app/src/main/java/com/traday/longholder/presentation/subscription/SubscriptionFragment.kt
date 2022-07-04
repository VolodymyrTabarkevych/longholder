package com.traday.longholder.presentation.subscription

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentSubscriptionBinding
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.presentation.base.BaseMVVMFragment
import com.traday.longholder.presentation.base.TabBarMode
import com.traday.longholder.presentation.base.WindowBackgroundMode
import com.traday.longholder.presentation.common.adapter.SubscriptionAdapter

class SubscriptionFragment :
    BaseMVVMFragment<SubscriptionViewModel, FragmentSubscriptionBinding>(
        layoutResId = R.layout.fragment_subscription
    ) {

    override val binding: FragmentSubscriptionBinding by viewBinding(FragmentSubscriptionBinding::bind)

    override val viewModel: SubscriptionViewModel by viewModels()

    private val subscriptionAdapter by lazy { SubscriptionAdapter() }

    override fun initView(inflatedView: View, savedInstanceState: Bundle?) {
        initActionButtons()
        initViewPager()
    }

    private fun initActionButtons() {
        with(binding) {
            stSubscription.setLeftActionOnCLickListener { navController.popBackStack() }
            pbSubscriptionNext.setOnClickListener {
                val currentPage = vpSubscription.currentItem
                val lastPage = subscriptionAdapter.itemCount.dec()
                val nextPage = if (currentPage < lastPage) currentPage.inc() else lastPage
                vpSubscription.setCurrentItem(nextPage, true)
            }
            pbSubscriptionCancel.setOnClickListener {
                viewModel.stopSubscription()
                navController.popBackStack()
            }
        }
    }

    private fun initViewPager() {
        with(binding) {
            vpSubscription.adapter = subscriptionAdapter
            vpSubscription.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val isLastPage = position == subscriptionAdapter.itemCount.dec()
                    viewModel.changeOnboardingButtons(isLastPage)
                    setActionButtons(isLastPage)
                }
            })
            TabLayoutMediator(tlSubscription, vpSubscription) { tab, _ ->
                tab.view.isClickable = false
            }.attach()
        }
    }

    override fun initViewModel() {
        with(viewModel) {
            isUserOnSubscription.observe(viewLifecycleOwner) {
                if (it is Resource.Success) {
                    if (it.data) {
                        viewModel.getActiveSubscription()
                    } else {
                        viewModel.getSubscriptions()
                    }
                    setSubscriptionScreen(it.data)
                }
            }
            onboardingButtonsLiveData.observe(viewLifecycleOwner, ::setActionButtons)
            getSubscriptionsLiveData.observe(viewLifecycleOwner) {
                if (it is Resource.Success) {
                    val billingClient = it.data.first
                    val billingFlowParams = it.data.second

                    binding.pbSubscriptionStart.setOnClickListener {
                        billingClient.launchBillingFlow(requireActivity(), billingFlowParams)
                    }
                }
            }
            getActiveSubscriptionLiveData.observe(viewLifecycleOwner) {
                if (it is Resource.Success) {
                    println(it.data.purchaseTime)
                }
            }
        }
    }

    private fun setActionButtons(isLastPage: Boolean) {
        with(binding) {
            pbSubscriptionNext.isVisible = !isLastPage
            pbSubscriptionStart.isVisible = isLastPage
        }
    }

    private fun setSubscriptionScreen(show: Boolean) {
        with(binding) {
            if (show) {
                WindowBackgroundMode.Secondary.onFragmentResumed(
                    this@SubscriptionFragment,
                    TabBarMode.INVISIBLE
                )
            } else {
                WindowBackgroundMode.Primary.onFragmentResumed(
                    this@SubscriptionFragment,
                    TabBarMode.INVISIBLE
                )
            }
            llSubscriptionOnboarding.isVisible = !show
            llSubscription.isVisible = show
        }
    }
}