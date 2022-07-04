package com.traday.longholder.presentation.analytics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.model.Currency
import com.traday.longholder.domain.model.Report
import com.traday.longholder.domain.servicerunner.IBillingClientRunner
import com.traday.longholder.domain.servicerunner.ICreateSubscriptionWorkerRunner
import com.traday.longholder.domain.servicerunner.IStopSubscriptionWorkerRunner
import com.traday.longholder.domain.usecase.*
import com.traday.longholder.presentation.base.SubscriptionViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val getReportUseCase: GetReportUseCase,
    subscribeIsUserOnSubscriptionUseCase: SubscribeIsUserOnSubscriptionUseCase,
    billingClientRunner: IBillingClientRunner,
    createSubscriptionWorkerRunner: ICreateSubscriptionWorkerRunner,
    stopSubscriptionWorkerRunner: IStopSubscriptionWorkerRunner,
    getSubscriptionsUseCase: GetSubscriptionsUseCase,
    isUserOnSubscriptionUseCase: IsUserHasSubscriptionUseCase
) : SubscriptionViewModel(
    billingClientRunner,
    createSubscriptionWorkerRunner,
    stopSubscriptionWorkerRunner,
    getSubscriptionsUseCase,
    isUserOnSubscriptionUseCase
) {

    val isUserOnSubscription: LiveData<Resource<Boolean>> = executeUseCase(
        subscribeIsUserOnSubscriptionUseCase, EmptyParams()
    ).asLiveData(timeoutInMs = 10000)

    private val _onboardingButtonsLiveData = MutableLiveData<Boolean>()
    val onboardingButtonsLiveData: LiveData<Boolean> get() = _onboardingButtonsLiveData

    private val _getCurrenciesLiveData = MutableLiveData<Resource<List<Currency>>>()
    val getCurrenciesLiveData: LiveData<Resource<List<Currency>>>
        get() = _getCurrenciesLiveData

    private val _selectedCurrencyLiveData = MutableLiveData<Currency>()
    val selectedCurrencyLiveData: LiveData<Currency> get() = _selectedCurrencyLiveData

    private val _getReportLiveData = MutableLiveData<Resource<Report>>()
    val getReportLiveData: LiveData<Resource<Report>> get() = _getReportLiveData

    fun getCurrencies() {
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