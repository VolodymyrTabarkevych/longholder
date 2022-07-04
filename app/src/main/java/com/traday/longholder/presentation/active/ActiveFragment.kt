package com.traday.longholder.presentation.active

import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.CalendarConstraints.DateValidator
import com.google.android.material.datepicker.CompositeDateValidator
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.traday.longholder.R
import com.traday.longholder.databinding.FragmentActiveBinding
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.model.Active
import com.traday.longholder.domain.model.Currency
import com.traday.longholder.extensions.*
import com.traday.longholder.presentation.base.BaseMVVMFragment
import com.traday.longholder.presentation.common.adapter.CurrencyAdapter
import com.traday.longholder.presentation.customviews.ProgressButton
import com.traday.longholder.presentation.validation.validator.base.ValidateResult
import com.traday.longholder.utils.showDialog

class ActiveFragment : BaseMVVMFragment<ActiveViewModel, FragmentActiveBinding>(
    R.layout.fragment_active
) {

    override val binding: FragmentActiveBinding by viewBinding(FragmentActiveBinding::bind)

    override val viewModel: ActiveViewModel by viewModels()

    override fun initView(inflatedView: View, savedInstanceState: Bundle?) {
        initActionButtons()
    }

    private fun initActionButtons() {
        with(binding) {
            tilActiveAmount.setupWithDefaultConfiguration(onStateChanged = ::validateFields)
            tilActiveWantedPercents.setupWithDefaultConfiguration(onStateChanged = ::validateFields)
            tietActiveDate.setOnClickListener {
                val dateValidatorMin: DateValidator =
                    DateValidatorPointForward.from(System.currentTimeMillis())
                val validators = CompositeDateValidator.allOf(listOf(dateValidatorMin))

                MaterialDatePicker.Builder
                    .datePicker()
                    .setCalendarConstraints(
                        CalendarConstraints.Builder().setValidator(validators).build()
                    )
                    .build().also {
                        it.addOnPositiveButtonClickListener { timeInMillis ->
                            viewModel.setCalendarDate(
                                timeInMillis
                            )
                        }
                        it.show(childFragmentManager, TAG)
                    }
            }
            stActive.setLeftActionOnCLickListener { navController.popBackStack() }
        }
    }

    override fun initViewModel() {
        with(viewModel) {
            modeLiveData.observe(viewLifecycleOwner, ::setMode)
            selectedCurrencyLiveData.observe(viewLifecycleOwner, ::setCurrency)
            buttonStateLiveData.observe(viewLifecycleOwner, ::setButtonState)
            validationErrorsLiveData.observe(viewLifecycleOwner, ::setValidationError)
            activeLiveData.observe(viewLifecycleOwner, ::setActive)
            getCurrenciesLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> setMainLoading(false)
                    is Resource.Loading -> setMainLoading(true)
                    is Resource.Success -> {
                        setMainLoading(false)
                        setCurrencies(it.data)
                    }
                }
            }
            createActiveLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> setCreateDeleteActiveLoading(false)
                    is Resource.Loading -> setCreateDeleteActiveLoading(true)
                    is Resource.Success -> {
                        setCreateDeleteActiveLoading(false)
                        showSnackOverBottomNavigationView()
                        navController.popBackStack()
                    }
                }
            }
            deleteActiveLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> setDeleteActiveLoading(false)
                    is Resource.Loading -> setDeleteActiveLoading(true)
                    is Resource.Success -> {
                        setDeleteActiveLoading(false)
                        navController.popBackStack()
                    }
                }
            }
            updateActiveLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> setUpdateActiveLoading(false)
                    is Resource.Loading -> setUpdateActiveLoading(true)
                    is Resource.Success -> {
                        setUpdateActiveLoading(false)
                        navController.popBackStack()
                    }
                }
            }
            getEndedActiveLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> setMainLoading(false)
                    is Resource.Loading -> setMainLoading(true)
                    is Resource.Success -> {
                        setMainLoading(false)
                        setActive(it.data)
                    }
                }
            }
            calendarDateLiveData.observe(viewLifecycleOwner, ::setCalendarDate)
        }
    }

    private fun setMode(mode: ActiveScreenMode) {
        when (mode) {
            is ActiveScreenMode.Create -> setCreateMode()
            is ActiveScreenMode.Update -> setUpdateMode()
            is ActiveScreenMode.ViewEndedActive -> setViewEndedActiveMode()
        }
    }

    private fun setUpdateMode() {
        with(binding) {
            tvActiveTitle.text = getString(R.string.active_update)
            tvActiveSelectCurrencyTitle.text = getString(R.string.active_your_coin)
            tilActiveSelectCurrency.endIconDrawable = null
            actvActiveSelectCurrency.isEnabled = false
            tvActiveAmountTitle.text = getString(R.string.active_enter_amount)
            tietActiveDate.setTextColor(getColorCompat(R.color.black))
            tvActiveCommentTitle.text = getString(R.string.active_your_comment)
            tvActiveWantedPercentsTitle.text = getString(R.string.active_enter_wanted_percents)
            tietActiveComment.isEnabled = false
            llActiveInfo.show()

            pbActiveFirstAction.apply {
                setType(ProgressButton.Type.PRIMARY)
                setText(getString(R.string.common_update))
                setOnClickListener {
                    viewModel.updateActive(
                        valueOfCrypto = tietActiveAmount.text.toString(),
                        wantedPercents = tietActiveWantedPercents.text.toString(),
                        dateOfEnd = tietActiveDate.text.toString()
                    )
                }
            }

            pbActiveSecondAction.apply {
                setType(ProgressButton.Type.EMPTY_WITH_BORDER)
                setText(getString(R.string.active_stop_holding))
                setOnClickListener {
                    showDialog(
                        title = getString(R.string.dialog_do_you_want_stop_holding),
                        message = getString(R.string.dialog_if_you_stop_holding),
                        positiveButtonText = getString(R.string.active_stop_holding),
                        negativeButtonText = getString(R.string.common_cancel),
                        onPositiveButtonClicked = {
                            viewModel.deleteActive()
                        }
                    )
                }
            }
        }
    }

    private fun setCreateMode() {
        with(binding) {
            llActiveMain.gone()
            tvActiveTitle.text = getString(R.string.active_add)
            tvActiveSelectCurrencyTitle.text = getString(R.string.active_select_coin)
            tilActiveSelectCurrency.endIconDrawable =
                getDrawableCompat(R.drawable.ic_small_arrow_to_bottom)
            actvActiveSelectCurrency.isEnabled = true
            tvActiveAmountTitle.text = getString(R.string.active_enter_amount)
            tvActiveCommentTitle.text = getString(R.string.active_leave_comment)
            tvActiveWantedPercentsTitle.text = getString(R.string.active_enter_wanted_percents)
            llActiveInfo.gone()

            pbActiveFirstAction.apply {
                setText(getString(R.string.active_put_on_hold))
                setOnClickListener {
                    viewModel.createActive(
                        valueOfCrypto = tietActiveAmount.text.toString(),
                        wantedPercents = tietActiveWantedPercents.text.toString(),
                        dateOfEnd = tietActiveDate.text.toString(),
                        comment = tietActiveComment.text.toString()
                    )
                }
            }
            pbActiveSecondAction.apply {
                setType(ProgressButton.Type.EMPTY_WITH_BORDER_RED)
                setText(getString(R.string.common_cancel))
                setOnClickListener { navController.popBackStack() }
            }
        }
    }

    private fun setViewEndedActiveMode() {
        with(binding) {
            tvActiveTitle.text = getString(R.string.active_your)
            tvActiveSelectCurrencyTitle.text = getString(R.string.active_your_coin)
            tilActiveSelectCurrency.endIconDrawable = null
            actvActiveSelectCurrency.isEnabled = false
            tvActiveAmountTitle.text = getString(R.string.active_amount)
            tietActiveAmount.isEnabled = false
            tietActiveDate.apply {
                isEnabled = false
                setTextColor(getColorCompat(R.color.black))
            }
            tvActiveWantedPercentsTitle.text = getString(R.string.active_wanted_percents)
            tietActiveWantedPercents.isEnabled = false
            tvActiveCommentTitle.text = getString(R.string.active_your_comment)
            tietActiveComment.isEnabled = false
            llActiveInfo.show()
            pbActiveFirstAction.gone()
            pbActiveSecondAction.gone()
        }
    }

    private fun setActive(active: Active) {
        with(binding) {
            tilActiveSelectCurrency.setStartIconWithGlide(active.linkToImage)
            actvActiveSelectCurrency.setText(active.nameFormatted)
            tietActiveAmount.setText(active.valueOfCrypto.toString())
            tietActiveWantedPercents.setText(active.wantedPercents.toString())
            tietActiveDate.setText(active.dateOfEnd)
            tietActiveComment.setText(active.comment)
            tvActiveSummaryDay.text = active.priceInOtherCurrencyOnStartFormatted
            tvActiveSummaryToday.text = active.currentCurrencyPriceSummaryFormatted

            tvActiveEarned.apply {
                setTextColor(getColorCompat(active.earnedMoneyResIdColor))
                text = getString(
                    R.string.common_earned_crypto_with_percent,
                    active.earnedMoneyFormatted,
                    active.percentsFormatted
                )
            }
        }
    }

    private fun setCalendarDate(date: String) {
        with(binding) {
            tietActiveDate.setText(date)
            tietActiveDate.setTextColor(getColorCompat(R.color.black))
            validateFields()
        }
    }

    private fun setMainLoading(isLoading: Boolean) {
        with(binding) {
            pbActive.isVisible = isLoading
            llActiveMain.isVisible = !isLoading
        }
    }

    private fun setCurrencies(currencies: List<Currency>) {
        with(binding) {
            (actvActiveSelectCurrency as? AutoCompleteTextView)?.apply {
                if (text.toString().isEmpty()) {
                    setInitialCurrency(currencies.first())
                }
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
        binding.pbActiveFirstAction.isEnabled = enabled
    }

    private fun setCreateDeleteActiveLoading(isLoading: Boolean) {
        binding.pbActiveFirstAction.setLoading(isLoading)
    }

    private fun setDeleteActiveLoading(isLoading: Boolean) {
        with(binding) {
            pbActiveFirstAction.isEnabled = !isLoading
            pbActiveSecondAction.setLoading(isLoading)
        }
    }

    private fun setUpdateActiveLoading(isLoading: Boolean) {
        with(binding) {
            pbActiveFirstAction.setLoading(isLoading)
            pbActiveSecondAction.isEnabled = !isLoading
        }
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
            val wantedPercents = tietActiveWantedPercents.text.toString()
            val calendarDate: String = tietActiveDate.text.toString()
            viewModel.validateFields(cryptoAmount, wantedPercents, calendarDate)
        }
    }

    companion object {

        private val TAG = ActiveFragment::class.java.simpleName
    }
}