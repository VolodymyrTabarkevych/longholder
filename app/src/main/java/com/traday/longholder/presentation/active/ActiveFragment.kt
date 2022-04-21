package com.traday.longholder.presentation.active

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentActiveBinding
import com.traday.longholder.domain.model.Active
import com.traday.longholder.domain.model.Coin
import com.traday.longholder.extensions.navigateSafe
import com.traday.longholder.presentation.base.BaseMVVMFragment
import java.text.SimpleDateFormat
import java.util.*

class ActiveFragment : BaseMVVMFragment<ActiveViewModel, FragmentActiveBinding>(
    R.layout.fragment_active
) {

    override val binding: FragmentActiveBinding by viewBinding(FragmentActiveBinding::bind)

    override val viewModel: ActiveViewModel by viewModels()

    private val args: ActiveFragmentArgs by navArgs()

    override fun initView(inflatedView: View, savedInstanceState: Bundle?) {
        initActionButtons()
        initActive(args.active)
    }

    private fun initActionButtons() {
        with(binding) {
            stActive.setLeftActionOnCLickListener { navController.popBackStack() }
            pbActiveCancel.setOnClickListener { navController.popBackStack() }
        }
    }

    private fun initActive(active: Active?) {
        with(binding) {
            if (active == null) {
                pbActiveHoldAction.setOnClickListener {
                    navController.navigateSafe(
                        ActiveFragmentDirections.actionActiveFragmentToWalletFragment(
                            Active(
                                icon = R.drawable.img_eth,
                                name = "Bitcoin",
                                amount = "114,2069",
                                amountInDollars = "(\$146 094,43)",
                                date = "22/02/2022",
                                summaryDayOfPurchase = "983 143 523,22",
                                summaryToday = "992 143 523,22",
                                profit = "+8 323,13 (0,66 %) ",
                                cryptoPrice = "\$3,242.86 "
                            )
                        )
                    )
                }
                tvActiveTitle.text = getString(R.string.active_add)
                actvActiveSelectCoin.isEnabled = true
                tietActiveAmount.isEnabled = true
                tvActiveSummaryDayCurrency.isEnabled = true
                tvActiveSummaryTodayCurrency.isEnabled = true
                tietActiveComment.isEnabled = true
                pbActiveHoldAction.setText(getString(R.string.active_put_on_hold))
                tietActiveDate.setOnClickListener {
                    MaterialDatePicker.Builder
                        .datePicker()
                        .build().also {
                            it.addOnPositiveButtonClickListener { timeInMillis ->
                                val format = SimpleDateFormat("dd.mm.yyyy", Locale.US)
                                val formattedDate: String = format.format(timeInMillis)
                                tietActiveDate.setText(formattedDate)
                            }
                            it.show(childFragmentManager, TAG)
                        }
                }
            } else {
                pbActiveHoldAction.setOnClickListener {
                    navController.navigateSafe(
                        ActiveFragmentDirections.actionActiveFragmentToWalletFragment(null)
                    )
                }
                tvActiveTitle.text = getString(R.string.active_your)
                actvActiveSelectCoin.isEnabled = false
                tietActiveAmount.isEnabled = false
                tvActiveSummaryDayCurrency.isEnabled = false
                tvActiveSummaryTodayCurrency.isEnabled = false
                tietActiveComment.isEnabled = false
                pbActiveHoldAction.setText(getString(R.string.active_stop_holding))
            }
        }
    }

    override fun initViewModel() {
        viewModel.coinsLiveData.observe(viewLifecycleOwner, ::setCoins)
    }

    private fun setCoins(coins: List<Coin>) {
        val names = coins.map { it.name }
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_coin, names)
        (binding.actvActiveSelectCoin as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    companion object {

        private val TAG = ActiveFragment::class.java.simpleName
    }
}