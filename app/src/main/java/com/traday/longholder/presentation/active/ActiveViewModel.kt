package com.traday.longholder.presentation.active

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.model.Active
import com.traday.longholder.domain.model.Currency
import com.traday.longholder.domain.usecase.*
import com.traday.longholder.presentation.base.BaseValidationViewModel
import com.traday.longholder.presentation.validation.validator.CalendarValidator
import com.traday.longholder.presentation.validation.validator.CryptoValidator
import com.traday.longholder.presentation.validation.validator.base.ValidateResult
import com.traday.longholder.utils.CALENDAR_FORMAT_PATTERN
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ActiveViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val createActiveUseCase: CreateActiveUseCase,
    private val deleteActiveUseCase: DeleteActiveUseCase,
    private val updateActiveUseCase: UpdateActiveUseCase,
    private val getEndedActiveByIdUseCase: GetEndedActiveByIdUseCase,
    state: SavedStateHandle
) : BaseValidationViewModel() {

    private val mode: ActiveScreenMode =
        ActiveFragmentArgs.fromSavedStateHandle(state).mode

    private val _modeLiveData = MutableLiveData(mode)
    val modeLiveData: LiveData<ActiveScreenMode> get() = _modeLiveData

    private val _selectedCurrencyLiveData = MutableLiveData<Currency>()
    val selectedCurrencyLiveData: LiveData<Currency> get() = _selectedCurrencyLiveData

    private val _buttonStateLiveData = MutableLiveData<Boolean>()
    val buttonStateLiveData: LiveData<Boolean> get() = _buttonStateLiveData

    private val _getCurrenciesLiveData = MutableLiveData<Resource<List<Currency>>>()
    val getCurrenciesLiveData: LiveData<Resource<List<Currency>>> get() = _getCurrenciesLiveData

    private val _createActiveLiveData = MutableLiveData<Resource<Unit>>()
    val createActiveLiveData: LiveData<Resource<Unit>> get() = _createActiveLiveData

    private val _deleteActiveLiveData = MutableLiveData<Resource<Unit>>()
    val deleteActiveLiveData: LiveData<Resource<Unit>> get() = _deleteActiveLiveData

    private val _updateActiveLiveData = MutableLiveData<Resource<Unit>>()
    val updateActiveLiveData: LiveData<Resource<Unit>> get() = _updateActiveLiveData

    private val _calendarDateLiveData = MutableLiveData<String>()
    val calendarDateLiveData: LiveData<String> get() = _calendarDateLiveData

    private val _getEndedActiveLiveData = MutableLiveData<Resource<Active>>()
    val getEndedActiveLiveData: LiveData<Resource<Active>> get() = _getEndedActiveLiveData

    private val _activeLiveData = MutableLiveData<Active>()
    val activeLiveData: LiveData<Active> get() = _activeLiveData


    init {
        when (mode) {
            is ActiveScreenMode.Create -> getCurrencies()
            is ActiveScreenMode.Update -> setActive(mode.active)
            is ActiveScreenMode.ViewEndedActive -> getEndedActiveById(mode.activeId)
        }
    }

    private fun setActive(active: Active) {
        _activeLiveData.postValue(active)
    }

    private fun getEndedActiveById(activeId: Int) {
        executeUseCase(getEndedActiveByIdUseCase, GetEndedActiveByIdUseCase.Params(activeId)) {
            _getEndedActiveLiveData.postValue(it)
        }
    }

    private fun getCurrencies() {
        executeUseCase(getCurrenciesUseCase, GetCurrenciesUseCase.Params(true)) {
            _getCurrenciesLiveData.postValue(it)
        }
    }

    fun validateFields(amount: String, wantedPercents: String, date: String) {
        when (mode) {
            is ActiveScreenMode.Create -> {
                onValidateFields(
                    ::onValidationSuccess,
                    CryptoValidator.Amount.validate(amount),
                    CryptoValidator.Amount.validate(wantedPercents),
                    CalendarValidator.Date.validate(date)
                )
            }
            is ActiveScreenMode.Update -> {
                val amountWasChanged = CryptoValidator.AmountsNotSame.validate(
                    oldValue = mode.active.valueOfCrypto.toString(),
                    newValue = amount
                ) !is ValidateResult.Error

                val wantedPercentsWasChanged = CryptoValidator.AmountsNotSame.validate(
                    oldValue = mode.active.wantedPercents.toString(),
                    newValue = wantedPercents
                ) !is ValidateResult.Error

                val dateWasChanged = CalendarValidator.DatesNotSame.validate(
                    oldValue = mode.active.dateOfEnd,
                    newValue = date
                ) !is ValidateResult.Error

                if (amountWasChanged || wantedPercentsWasChanged || dateWasChanged) {
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
        wantedPercents: String,
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
                wantedPercents = wantedPercents,
                dateOfEnd = dateOfEnd,
                comment = comment,
                linkToImage = selectedCurrency.linkToPhoto,
                symbol = selectedCurrency.symbol,
                indexOnExchange = selectedCurrency.indexOnExchange
            )
        ) {
            _createActiveLiveData.postValue(it)
        }
    }

    fun deleteActive() {
        if (mode !is ActiveScreenMode.Update) return
        executeUseCase(deleteActiveUseCase, DeleteActiveUseCase.Params(mode.active.id)) {
            _deleteActiveLiveData.postValue(it)
        }
    }

    fun updateActive(valueOfCrypto: String, wantedPercents: String, dateOfEnd: String) {
        if (mode !is ActiveScreenMode.Update) return
        val currentActive =
            mode.active.copy(
                valueOfCrypto = valueOfCrypto.toDouble(),
                wantedPercents = wantedPercents.toDouble(),
                dateOfEnd = dateOfEnd
            )
        executeUseCase(updateActiveUseCase, UpdateActiveUseCase.Params(currentActive)) {
            _updateActiveLiveData.postValue(it)
        }
    }

    fun setCalendarDate(timeInMillis: Long) {
        val format = SimpleDateFormat(
            CALENDAR_FORMAT_PATTERN,
            Locale.getDefault()
        )
        val formattedDate: String = format.format(timeInMillis)
        _calendarDateLiveData.postValue(formattedDate)
    }
}