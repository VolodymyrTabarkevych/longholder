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


    fun validateFields(amount: String, date: String) {
        onValidateFields(
            ::onValidationSuccess,
            CryptoValidator.Amount.validate(amount),
            CalendarValidator.Date.validate(date)
        )
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
                name = selectedCurrency.name,
                currentCurrencyPrice = selectedCurrency.price,
                cryptoPriceOnStart = selectedCurrency.price,
                nameOfCurrency = selectedCurrency.indexOnExchange,
                valueOfCrypto = valueOfCrypto,
                dateOfEnd = dateOfEnd,
                comment = comment,
                linkToImage = selectedCurrency.linkToPhoto
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
}