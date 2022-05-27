package com.traday.longholder.presentation.analytics

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.AutoCompleteTextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentAnalyticsBinding
import com.traday.longholder.databinding.ItemAnalyticsActiveBinding
import com.traday.longholder.databinding.ItemAnalyticsTotalInfoBinding
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.model.Active
import com.traday.longholder.domain.model.Currency
import com.traday.longholder.domain.model.Report
import com.traday.longholder.extensions.getColorCompat
import com.traday.longholder.extensions.navigateSafe
import com.traday.longholder.extensions.rightDrawable
import com.traday.longholder.extensions.setStartIconWithGlide
import com.traday.longholder.presentation.base.BaseMVVMFragment
import com.traday.longholder.presentation.base.TabBarMode
import com.traday.longholder.presentation.common.adapter.CurrencyAdapter
import com.traday.longholder.presentation.common.adapter.SubscriptionAdapter
import com.traday.longholder.utils.showDialog

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
            ivAnalyticsCalculate.setOnClickListener {
                navController.navigateSafe(
                    AnalyticsFragmentDirections.actionAnalyticsFragmentToCalculatorFragment()
                )
            }
            pbAnalyticsNext.setOnClickListener {
                val currentPage = vpAnalytics.currentItem
                val lastPage = subscriptionAdapter.itemCount.dec()
                val nextPage = if (currentPage < lastPage) currentPage.inc() else lastPage
                vpAnalytics.setCurrentItem(nextPage, true)
            }
            pbAnalyticsStart.setOnClickListener {
                showDialog(
                    "This feature is not yet implemented",
                    onCustomize = { dialogCommonBinding, _ ->
                        dialogCommonBinding.tvDialogTitle.gravity = Gravity.CENTER_HORIZONTAL
                    })
            }
        }
    }

    private fun initViewPager() {
        with(binding) {
            vpAnalytics.adapter = subscriptionAdapter
            vpAnalytics.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val isLastPage = position == subscriptionAdapter.itemCount.dec()
                    viewModel.changeOnboardingButtons(isLastPage)
                    setActionButtons(isLastPage)
                }
            })
            TabLayoutMediator(tlAnalytics, vpAnalytics) { tab, _ ->
                tab.view.isClickable = false
            }.attach()
        }
    }

    override fun initViewModel() {
        with(viewModel) {
            isUserOnSubscription.observe(viewLifecycleOwner) {
                if (it is Resource.Success) setAnalyticsScreen(it.data)
            }
            onboardingButtonsLiveData.observe(viewLifecycleOwner, ::setActionButtons)
            selectedCurrencyLiveData.observe(viewLifecycleOwner, ::setCurrency)
            getCurrenciesLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> setCurrenciesLoading(false)
                    is Resource.Loading -> setCurrenciesLoading(true)
                    is Resource.Success -> {
                        setCurrenciesLoading(false)
                        setCurrencies(it.data)
                    }
                }
            }
            getReportLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> setReportLoading(false)
                    is Resource.Loading -> setReportLoading(true)
                    is Resource.Success -> {
                        setReportLoading(false)
                        setActives(it.data.actives)
                        setTotalInfo(it.data)
                    }
                }
            }
        }
    }

    private fun setAnalyticsScreen(show: Boolean) {
        with(binding) {
            llAnalyticsSubscription.isVisible = !show
            svAnalytics.isVisible = show
        }
    }

    private fun setActionButtons(isLastPage: Boolean) {
        with(binding) {
            pbAnalyticsNext.isVisible = !isLastPage
            pbAnalyticsStart.isVisible = isLastPage
        }
    }

    private fun setCurrenciesLoading(isLoading: Boolean) {
        with(binding) {
            pbAnalytics.isVisible = isLoading
        }
    }

    private fun setCurrencies(currencies: List<Currency>) {
        with(binding) {
            (actvAnalyticsSelectCurrency as? AutoCompleteTextView)?.apply {
                if (text.toString().isEmpty()) {
                    setInitialCurrency(currencies.first())
                }
                val adapter = CurrencyAdapter(requireContext(), currencies)
                setAdapter(adapter)
                setOnItemClickListener { _, _, i, _ ->
                    viewModel.selectCurrencyAndLoadReport(currencies[i])
                }
            }
        }
    }

    private fun setInitialCurrency(currency: Currency) {
        (binding.actvAnalyticsSelectCurrency as? AutoCompleteTextView)?.apply {
            setText(currency.name)
            viewModel.selectCurrencyAndLoadReport(currency)
        }
    }

    private fun setCurrency(currency: Currency) {
        with(binding) {
            (actvAnalyticsSelectCurrency as? AutoCompleteTextView)?.apply {
                tilAnalyticsSelectCurrency.setStartIconWithGlide(currency.linkToPhoto)
            }
        }
    }

    private fun setReportLoading(isLoading: Boolean) {
        with(binding) {
            llAnalyticsActives.isVisible = !isLoading
            llAnalyticsTotalInfo.isVisible = !isLoading
            pbAnalytics.isVisible = isLoading
        }
    }

    private fun setActives(actives: List<Active>) {
        binding.llAnalyticsActives.removeAllViews()
        actives.forEach {
            with(ItemAnalyticsActiveBinding.inflate(LayoutInflater.from(requireContext()))) {
                tvAnalyticsActiveAmount.text = it.valueOfCryptoFormatted
                tvAnalyticsActivePriceInOtherCurrency.apply {
                    text = resources.getString(
                        R.string.common_dollar_value_with_brackets,
                        it.priceInOtherCurrencyOnStartFormatted
                    )
                }
                tvAnalyticsActiveStartDate.text = it.dateOfStart
                tvAnalyticsCurrentPrice.text = resources.getString(
                    R.string.common_crypto_price_in_other_currency,
                    it.symbolFormatted,
                    it.currentCurrencyPriceFormatted
                )
                binding.llAnalyticsActives.addView(root)
            }
        }
    }

    private fun setTotalInfo(report: Report) {
        binding.llAnalyticsTotalInfo.removeAllViews()
        with(ItemAnalyticsTotalInfoBinding.inflate(LayoutInflater.from(requireContext()))) {
            tvAnalyticsTotalInfoCount.text = report.countOfCryptoFormatted
            tvAnalyticsTotalInfoPriceNow.text = report.priceNowFormatted
            tvAnalyticsTotalInfoPriceTotal.text = report.allMoneyFormatted
            tvAnalyticsTotalInfoEarned.apply {
                setTextColor(getColorCompat(report.profitResIdColor))
                text = getString(R.string.common_earned_crypto, report.profitFormatted)
            }
            tvAnalyticsTotalInfoEarnedInPercents.apply {
                setTextColor(getColorCompat(report.profitResIdColor))
                rightDrawable(report.getPercentsDrawableResId, report.profitResIdColor)
                text = getString(R.string.common_value_with_percent, report.percents)
            }
            binding.llAnalyticsTotalInfo.addView(root)
        }
    }
}