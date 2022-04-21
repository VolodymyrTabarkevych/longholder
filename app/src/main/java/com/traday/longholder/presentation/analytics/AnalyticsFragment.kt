package com.traday.longholder.presentation.analytics

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
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

    override fun initView(inflatedView: View, savedInstanceState: Bundle?) {}

    override fun initViewModel() {
        viewModel.coinsLiveData.observe(viewLifecycleOwner, ::setCoins)
    }

    private fun setCoins(coins: List<Coin>) {
        val names = coins.map { it.name }
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_coin, names)
        (binding.actvAnalyticsSelectCoin as? AutoCompleteTextView)?.setAdapter(adapter)
    }
}