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
import com.traday.longholder.utils.EMPTY_STRING
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val getUserCurrenciesUseCase: GetUserCurrenciesUseCase,
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

    fun getUserCurrencies() {
        executeUseCase(getUserCurrenciesUseCase, EmptyParams()) {
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
            GetReportUseCase.Params(currencyId = currency.indexOnExchange),
            showDialogOnError = false
        ) {
            when (it) {
                is Resource.Error -> _getReportLiveData.postValue(Resource.Success(getDummyReport()))
                is Resource.Success -> _getReportLiveData.postValue(it)
            }
        }
    }

    fun getDummyReport(): Report {
        return Report(
            id = 0,
            name = EMPTY_STRING,
            currencyCode = EMPTY_STRING,
            profit = 0.0,
            allMoney = 0.0,
            countOfCrypto = 0.0,
            percents = 0.0,
            priceNow = 0.0,
            coinName = EMPTY_STRING,
            actives = emptyList(),
            dateOfReport = EMPTY_STRING
        )
    }
}