package com.traday.longholder.presentation.analytics

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentAnalyticsBinding
import com.traday.longholder.domain.model.Coin
import com.traday.longholder.presentation.base.BaseMVVMFragment
import com.traday.longholder.presentation.base.TabBarMode

class AnalyticsFragment : BaseMVVMFragment<AnalyticsViewModel, FragmentAnalyticsBinding>(
    layoutResId = R.layout.fragment_analytics,
    tabBarMode = TabBarMode.VISIBLE
) {

    override val binding: FragmentAnalyticsBinding by viewBinding(FragmentAnalyticsBinding::bind)

    override val viewModel: AnalyticsViewModel by viewModels()

    private val subscriptionAdapter by lazy { SubscriptionAdapter() }

    override fun initView(inflatedView: View, savedInstanceState: Bundle?) {
        initActionButtons()
        initViewPager()
    }

    private fun initActionButtons() {
        with(binding) {
            pbAnalyticsNext.setOnClickListener {
                val currentPage = vpAnalytics.currentItem
                val lastPage = subscriptionAdapter.itemCount - 1
                val nextPage = if (currentPage < lastPage) currentPage + 1 else lastPage
                vpAnalytics.setCurrentItem(nextPage, true)
            }
            pbAnalyticsStart.setOnClickListener {
                viewModel.makeSubscription()
            }
        }
    }

    private fun initViewPager() {
        with(binding) {
            vpAnalytics.adapter = subscriptionAdapter
            vpAnalytics.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val isLastPage = position == subscriptionAdapter.itemCount - 1
                    changeActionButtons(isLastPage)
                }
            })
            TabLayoutMediator(tlAnalytics, vpAnalytics) { tab, _ ->
                tab.view.isClickable = false
            }.attach()
        }
    }

    override fun initViewModel() {
        with(viewModel) {
            coinsLiveData.observe(viewLifecycleOwner, ::setCoins)
            showAnalyticsScreen.observe(viewLifecycleOwner, ::showAnalyticsScreen)
        }
    }

    private fun setCoins(coins: List<Coin>) {
        val names = coins.map { it.name }
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_coin, names)
        (binding.actvAnalyticsSelectCoin as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun showAnalyticsScreen(show: Boolean) {
        with(binding) {
            llAnalyticsSubscription.isVisible = !show
            svAnalytics.isVisible = show
        }
    }

    private fun changeActionButtons(isLastPage: Boolean) {
        with(binding) {
            pbAnalyticsNext.isVisible = !isLastPage
            pbAnalyticsStart.isVisible = isLastPage
        }
    }
}