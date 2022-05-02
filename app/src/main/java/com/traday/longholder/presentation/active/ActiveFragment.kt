package com.traday.longholder.presentation.active

import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentActiveBinding
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.model.Currency
import com.traday.longholder.extensions.*
import com.traday.longholder.presentation.active.adapter.CurrencyAdapter
import com.traday.longholder.presentation.base.BaseMVVMFragment
import com.traday.longholder.presentation.validation.validator.base.ValidateResult
import com.traday.longholder.utils.CALENDAR_FORMAT_PATTERN
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
        initMode(args.mode)
    }

    private fun initActionButtons() {
        with(binding) {
            stActive.setLeftActionOnCLickListener { navController.popBackStack() }
            pbActiveCancel.setOnClickListener { navController.popBackStack() }
        }
    }

    private fun initMode(mode: ActiveScreenMode) {
        with(binding) {
            when (mode) {
                is ActiveScreenMode.View -> {
                    val active = mode.active
                    tvActiveTitle.text = getString(R.string.active_your)
                    tvActiveSelectCurrencyTitle.text = getString(R.string.active_your_coin)
                    tilActiveSelectCurrency.apply {
                        setStartIconWithGlide(active.linkToImage)
                        endIconDrawable = null
                    }
                    actvActiveSelectCurrency.apply {
                        isEnabled = false
                        setText(active.name)
                    }
                    tvActiveAmountTitle.text = getString(R.string.active_amount)
                    tietActiveAmount.apply {
                        isEnabled = false
                        setText(active.valueOfCrypto.toString())
                    }
                    tietActiveDate.setText(active.dateOfStart)
                    tietActiveComment.apply {
                        isEnabled = false
                        setText(active.comment)
                    }
                    llActiveInfo.show()
                    tvActiveSummaryDay.text = active.priceInOtherCurrencyOnStartFormatted
                    tvActiveSummaryToday.text = active.priceInOtherCurrencyOnEndFormatted
                    tvActiveEarned.text = getString(
                        R.string.common_earned_crypto_with_percent,
                        active.earnedMoneyFormatted,
                        active.percentsFormatted
                    )

                    pbActiveHoldAction.apply {
                        setText(getString(R.string.active_stop_holding))
                        setOnClickListener {
                            showDialog(
                                title = getString(R.string.dialog_do_you_want_stop_holding),
                                message = getString(R.string.dialog_if_you_stop_holding),
                                positiveButtonText = getString(R.string.active_stop_holding),
                                negativeButtonText = getString(R.string.common_cancel),
                                onPositiveButtonClicked = {
                                    viewModel.deleteActive(active.id)
                                }
                            )
                        }
                    }
                }
                else -> {
                    tilActiveAmount.setupWithErrorClearListener()
                    tietActiveAmount.apply {
                        isEnabled = true
                        setOnFocusChangeListener { _, _ ->
                            validateFields()
                        }
                        setOnTextChangedListener(object : OnTextChangedListener {
                            override fun onTextChanged(viewId: Int?) {
                                validateFields()
                            }
                        })
                    }

                    tvActiveTitle.text = getString(R.string.active_add)
                    tvActiveSelectCurrencyTitle.text = getString(R.string.active_select_coin)
                    tilActiveSelectCurrency.endIconDrawable =
                        getDrawableCompat(R.drawable.ic_arrow_to_down)
                    actvActiveSelectCurrency.isEnabled = true
                    tvActiveAmountTitle.text = getString(R.string.active_enter_amount)
                    llActiveInfo.gone()

                    pbActiveHoldAction.apply {
                        isEnabled = false
                        setText(getString(R.string.active_put_on_hold))
                        setOnClickListener {
                            viewModel.createActive(
                                valueOfCrypto = tietActiveAmount.text.toString(),
                                dateOfEnd = tietActiveDate.text.toString(),
                                comment = tietActiveComment.text.toString()
                            )
                        }
                    }
                    tietActiveDate.setOnClickListener {
                        MaterialDatePicker.Builder
                            .datePicker()
                            .build().also {
                                it.addOnPositiveButtonClickListener { timeInMillis ->
                                    val format = SimpleDateFormat(
                                        CALENDAR_FORMAT_PATTERN,
                                        Locale.getDefault()
                                    )
                                    val formattedDate: String = format.format(timeInMillis)
                                    tietActiveDate.setText(formattedDate)
                                    validateFields()
                                }
                                it.show(childFragmentManager, TAG)
                            }
                    }
                }
            }
        }
    }

    override fun initViewModel() {
        with(viewModel) {
            selectedCurrency.observe(viewLifecycleOwner, ::setCurrency)
            buttonStateLiveData.observe(viewLifecycleOwner, ::setButtonState)
            validationErrorsLiveData.observe(viewLifecycleOwner, ::setValidationError)
            getCurrenciesLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> {
                        setCurrenciesLoading(false)
                        showDialog(it.error.msg)
                    }
                    is Resource.Loading -> setCurrenciesLoading(true)
                    is Resource.Success -> {
                        setCurrenciesLoading(false)
                        setCurrencies(it.data)
                    }
                }
            }
            createActiveLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> {
                        setCreateDeleteActiveLoading(false)
                        showDialog(it.error.msg)
                    }
                    is Resource.Loading -> setCreateDeleteActiveLoading(true)
                    is Resource.Success -> {
                        setCreateDeleteActiveLoading(false)
                        navController.popBackStack()
                    }
                }
            }
            deleteActiveLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> {
                        setCreateDeleteActiveLoading(false)
                        showDialog(it.error.msg)
                    }
                    is Resource.Loading -> setCreateDeleteActiveLoading(true)
                    is Resource.Success -> {
                        setCreateDeleteActiveLoading(false)
                        navController.popBackStack()
                    }
                }
            }
        }
    }

    private fun setCurrenciesLoading(isLoading: Boolean) {
        with(binding) {
            pbActive.isVisible = isLoading
            llActiveMain.isVisible = !isLoading
        }
    }

    private fun setCurrencies(currencies: List<Currency>) {
        with(binding) {
            (actvActiveSelectCurrency as? AutoCompleteTextView)?.apply {
                setInitialCurrency(currencies.first())
                val adapter = CurrencyAdapter(requireContext(), currencies)
                setAdapter(adapter)
                setOnItemClickListener { _, _, i, _ ->
                    viewModel.selectCurrency(currencies[i])
                }
            }
        }
    }

    private fun setInitialCurrency(currency: Currency) {
        (binding.actvActiveSelectCurrency as? AutoCompleteTextView)?.apply {
            setText(currency.name)
            viewModel.selectCurrency(currency)
        }
    }

    private fun setCurrency(currency: Currency) {
        with(binding) {
            (actvActiveSelectCurrency as? AutoCompleteTextView)?.apply {
                tilActiveSelectCurrency.setStartIconWithGlide(currency.linkToPhoto)
            }
        }
    }

    private fun setButtonState(enabled: Boolean) {
        binding.pbActiveHoldAction.isEnabled = enabled
    }

    private fun setCreateDeleteActiveLoading(isLoading: Boolean) {
        binding.pbActiveHoldAction.setLoading(isLoading)
    }

    private fun setValidationError(errorList: List<ValidateResult.Error>) {
        errorList.forEach {
/*            when (it.exception) {
                is CryptoValueNotValidException -> setEmailError(getString(it.exception.stringId))
                is CalendarDateNotValidException -> setPasswordError(getString(it.exception.stringId))
            }*/
        }
    }

    private fun validateFields() {
        with(binding) {
            val cryptoAmount = tietActiveAmount.text.toString()
            val calendarDate: String = tietActiveDate.text.toString()
            viewModel.validateFields(cryptoAmount, calendarDate)
        }
    }

    companion object {

        private val TAG = ActiveFragment::class.java.simpleName
    }
}