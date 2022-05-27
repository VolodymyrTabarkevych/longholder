package com.traday.longholder.presentation.analytics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.model.Currency
import com.traday.longholder.domain.model.Report
import com.traday.longholder.domain.usecase.GetCurrenciesUseCase
import com.traday.longholder.domain.usecase.GetReportUseCase
import com.traday.longholder.domain.usecase.SubscribeIsUserOnSubscriptionUseCase
import com.traday.longholder.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val subscribeIsUserOnSubscriptionUseCase: SubscribeIsUserOnSubscriptionUseCase,
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val getReportUseCase: GetReportUseCase
) : BaseViewModel() {

    val isUserOnSubscription: LiveData<Resource<Boolean>> = executeUseCase(
        subscribeIsUserOnSubscriptionUseCase,
        params = SubscribeIsUserOnSubscriptionUseCase.Params(false)
    ).asLiveData()

    private val _onboardingButtonsLiveData = MutableLiveData<Boolean>()
    val onboardingButtonsLiveData: LiveData<Boolean> get() = _onboardingButtonsLiveData

    private val _getCurrenciesLiveData = MutableLiveData<Resource<List<Currency>>>()
    val getCurrenciesLiveData: LiveData<Resource<List<Currency>>>
        get() = _getCurrenciesLiveData

    private val _selectedCurrencyLiveData = MutableLiveData<Currency>()
    val selectedCurrencyLiveData: LiveData<Currency> get() = _selectedCurrencyLiveData

    private val _getReportLiveData = MutableLiveData<Resource<Report>>()
    val getReportLiveData: LiveData<Resource<Report>> get() = _getReportLiveData


    init {
        getCurrencies()
    }

    private fun getCurrencies() {
        executeUseCase(getCurrenciesUseCase, GetCurrenciesUseCase.Params(true)) {
            _getCurrenciesLiveData.postValue(it)
        }
    }

    fun changeOnboardingButtons(isOnLastOnboardingPage: Boolean) {
        _onboardingButtonsLiveData.postValue(isOnLastOnboardingPage)
    }

    fun selectCurrencyAndLoadReport(currency: Currency) {
        _selectedCurrencyLiveData.postValue(currency)
        executeUseCase(
            getReportUseCase,
            GetReportUseCase.Params(currencyId = currency.indexOnExchange)
        ) {
            _getReportLiveData.postValue(it)
        }
    }
}