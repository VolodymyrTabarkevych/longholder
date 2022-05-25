package com.traday.longholder.presentation.active

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.model.Currency
import com.traday.longholder.domain.usecase.CreateActiveUseCase
import com.traday.longholder.domain.usecase.DeleteActiveUseCase
import com.traday.longholder.domain.usecase.GetCurrenciesUseCase
import com.traday.longholder.domain.usecase.UpdateActiveUseCase
import com.traday.longholder.presentation.base.BaseValidationViewModel
import com.traday.longholder.presentation.validation.validator.CalendarValidator
import com.traday.longholder.presentation.validation.validator.CryptoValidator
import com.traday.longholder.presentation.validation.validator.base.ValidateResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class ActiveViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val createActiveUseCase: CreateActiveUseCase,
    private val deleteActiveUseCase: DeleteActiveUseCase,
    private val updateActiveUseCase: UpdateActiveUseCase,
    state: SavedStateHandle
) : BaseValidationViewModel() {

    private val mode: ActiveScreenMode =
        ActiveFragmentArgs.fromSavedStateHandle(state).mode

    private val _selectedCurrencyLiveData = MutableLiveData<Currency>()
    val selectedCurrencyLiveData: LiveData<Currency> get() = _selectedCurrencyLiveData

    private val _buttonStateLiveData = MutableLiveData<Boolean>()
    val buttonStateLiveData: LiveData<Boolean> get() = _buttonStateLiveData

    private val _getCurrenciesLiveData = if (mode is ActiveScreenMode.Create) {
        executeUseCase(getCurrenciesUseCase, EmptyParams())
    } else {
        emptyFlow()
    }
    val getCurrenciesLiveData: LiveData<Resource<List<Currency>>> get() = _getCurrenciesLiveData.asLiveData()

    private val _createActiveLiveData = MutableLiveData<Resource<Unit>>()
    val createActiveLiveData: LiveData<Resource<Unit>> get() = _createActiveLiveData

    private val _deleteActiveLiveData = MutableLiveData<Resource<Unit>>()
    val deleteActiveLiveData: LiveData<Resource<Unit>> get() = _deleteActiveLiveData

    private val _updateActiveLiveData = MutableLiveData<Resource<Unit>>()
    val updateActiveLiveData: LiveData<Resource<Unit>> get() = _updateActiveLiveData


    fun validateFields(amount: String, date: String) {
        when (mode) {
            is ActiveScreenMode.Create -> {
                onValidateFields(
                    ::onValidationSuccess,
                    CryptoValidator.Amount.validate(amount),
                    CalendarValidator.Date.validate(date)
                )
            }
            is ActiveScreenMode.Update -> {
                val amountWasChanged = CryptoValidator.AmountsNotSame.validate(
                    oldValue = mode.active.valueOfCrypto.toString(),
                    newValue = amount
                ) !is ValidateResult.Error

                val dateWasChanged = CalendarValidator.DatesNotSame.validate(
                    oldValue = mode.active.dateOfEnd,
                    newValue = date
                ) !is ValidateResult.Error

                if (amountWasChanged || dateWasChanged) {
                    onValidateFields(
                        ::onValidationSuccess,
                        CryptoValidator.Amount.validate(amount),
                        CalendarValidator.Date.validate(date)
                    )
                } else {
                    _buttonStateLiveData.postValue(false)
                }
            }
        }
    }

    private fun onValidationSuccess() {
        _buttonStateLiveData.postValue(true)
    }

    override fun onValidateError(errorList: List<ValidateResult.Error>) {
        super.onValidateError(errorList)
        _buttonStateLiveData.postValue(false)
    }

    fun selectCurrency(currency: Currency) {
        _selectedCurrencyLiveData.postValue(currency)
    }

    fun createActive(
        valueOfCrypto: String,
        dateOfEnd: String,
        comment: String?
    ) {
        val selectedCurrency = _selectedCurrencyLiveData.value ?: return
        executeUseCase(
            createActiveUseCase, CreateActiveUseCase.Params(
                name = selectedCurrency.indexOnExchange,
                currentCurrencyPrice = selectedCurrency.price,
                cryptoPriceOnStart = selectedCurrency.price,
                valueOfCrypto = valueOfCrypto,
                dateOfEnd = dateOfEnd,
                comment = comment,
                linkToImage = selectedCurrency.linkToPhoto,
                symbol = selectedCurrency.symbol
            )
        ) {
            _createActiveLiveData.postValue(it)
        }
    }

    fun deleteActive(activeId: Int) {
        executeUseCase(deleteActiveUseCase, DeleteActiveUseCase.Params(activeId)) {
            _deleteActiveLiveData.postValue(it)
        }
    }

    fun updateActive(valueOfCrypto: String, dateOfEnd: String) {
        if (mode !is ActiveScreenMode.Update) return
        val currentActive =
            mode.active.copy(valueOfCrypto = valueOfCrypto.toDouble(), dateOfEnd = dateOfEnd)
        executeUseCase(updateActiveUseCase, UpdateActiveUseCase.Params(currentActive)) {
            _updateActiveLiveData.postValue(it)
        }
    }
}