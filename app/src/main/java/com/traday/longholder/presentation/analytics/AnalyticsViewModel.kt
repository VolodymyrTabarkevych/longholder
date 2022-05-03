package com.traday.longholder.presentation.analytics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.model.Currency
import com.traday.longholder.domain.model.Report
import com.traday.longholder.domain.usecase.GetCurrenciesUseCase
import com.traday.longholder.domain.usecase.GetReportUseCase
import com.traday.longholder.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val getReportUseCase: GetReportUseCase
) : BaseViewModel() {

    private val _onboardingButtonsLiveData = MutableLiveData<Boolean>()
    val onboardingButtonsLiveData: LiveData<Boolean> get() = _onboardingButtonsLiveData

    private val _makeSubscriptionLiveData = MutableLiveData(false)
    val makeSubscriptionLiveData: LiveData<Boolean> get() = _makeSubscriptionLiveData

    private val _getCurrenciesLiveData =
        executeUseCase(getCurrenciesUseCase, EmptyParams()).asLiveData()
    val getCurrenciesLiveData: LiveData<Resource<List<Currency>>>
        get() = _getCurrenciesLiveData

    private val _selectedCurrencyLiveData = MutableLiveData<Currency>()
    val selectedCurrencyLiveData: LiveData<Currency> get() = _selectedCurrencyLiveData

    private val _getReportLiveData = MutableLiveData<Resource<Report>>()
    val getReportLiveData: LiveData<Resource<Report>> get() = _getReportLiveData


    fun changeOnboardingButtons(isOnLastOnboardingPage: Boolean) {
        _onboardingButtonsLiveData.postValue(isOnLastOnboardingPage)
    }

    fun makeSubscription() {
        _makeSubscriptionLiveData.postValue(true)
    }

    fun selectCurrencyAndLoadReport(currency: Currency) {
        _selectedCurrencyLiveData.postValue(currency)
        currency.name?.let { currencyName ->
            executeUseCase(getReportUseCase, GetReportUseCase.Params(currencyId = currencyName)) {
                _getReportLiveData.postValue(it)
            }
        }
    }
}