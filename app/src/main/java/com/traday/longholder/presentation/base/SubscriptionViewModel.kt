package com.traday.longholder.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.Purchase
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.servicerunner.IBillingClientRunner
import com.traday.longholder.domain.servicerunner.ICreateSubscriptionWorkerRunner
import com.traday.longholder.domain.servicerunner.IStopSubscriptionWorkerRunner
import com.traday.longholder.domain.usecase.GetSubscriptionsUseCase
import com.traday.longholder.domain.usecase.IsUserHasSubscriptionUseCase
import kotlinx.coroutines.launch

abstract class SubscriptionViewModel(
    private val billingClientRunner: IBillingClientRunner,
    private val createSubscriptionWorkerRunner: ICreateSubscriptionWorkerRunner,
    private val stopSubscriptionWorkerRunner: IStopSubscriptionWorkerRunner,
    private val getSubscriptionsUseCase: GetSubscriptionsUseCase,
    private val isUserHasSubscriptionUseCase: IsUserHasSubscriptionUseCase
) : BaseViewModel() {

    private val _getSubscriptionsLiveData =
        EventLiveData<Resource<Pair<BillingClient, BillingFlowParams>>>()
    val getSubscriptionsLiveData: LiveData<Resource<Pair<BillingClient, BillingFlowParams>>> get() = _getSubscriptionsLiveData

    private val _getActiveSubscriptionLiveData = MutableLiveData<Resource<Purchase>>()
    val getActiveSubscriptionLiveData: LiveData<Resource<Purchase>> get() = _getActiveSubscriptionLiveData

    init {
        initBillingClient()
    }

    private fun initBillingClient() {
        viewModelScope.launch {
            billingClientRunner.initBillingClient { _, purchases ->
                purchases?.firstOrNull()?.let {
                    createSubscription(
                        packageName = it.packageName,
                        productId = it.products.first(),
                        purchaseToken = it.purchaseToken
                    )
                }
            }
        }
    }

    fun getActiveSubscription() {
        viewModelScope.launch {
            _getActiveSubscriptionLiveData.postValue(billingClientRunner.getSubscription())
        }
    }

    fun getSubscriptions() {
        executeUseCase(getSubscriptionsUseCase, EmptyParams()) {
            when (it) {
                is Resource.Error -> _getSubscriptionsLiveData.postValue(it)
                is Resource.Loading -> _getSubscriptionsLiveData.postValue(Resource.Loading)
                is Resource.Success -> {
                    _getSubscriptionsLiveData.postValue(
                        billingClientRunner.getProductDetailsParams(
                            it.data
                        )
                    )
                }
            }
        }
    }

    fun syncBillingServiceWithServer() {
        viewModelScope.launch {
            when (val subscriptionResult = billingClientRunner.getSubscription()) {
                is Resource.Error -> stopSubscription()
                is Resource.Success -> {
                    val isSubscriptionAutoRenewing = subscriptionResult.data.isAutoRenewing
                    if (isSubscriptionAutoRenewing) {
                        isUserHasSubscriptionUseCase.execute(
                            IsUserHasSubscriptionUseCase.Params(
                                true
                            )
                        ) {
                            if (it is Resource.Success) {
                                val isUserOnSubscription = it.data
                                if (!isUserOnSubscription) {
                                    createSubscription(
                                        packageName = subscriptionResult.data.packageName,
                                        productId = subscriptionResult.data.products.first(),
                                        purchaseToken = subscriptionResult.data.purchaseToken
                                    )
                                }
                            }
                        }
                    } else {
                        stopSubscription()
                    }
                }
            }
        }
    }

    private fun createSubscription(packageName: String, productId: String, purchaseToken: String) {
        createSubscriptionWorkerRunner.startWorker(
            packageName = packageName,
            productId = productId,
            purchaseToken = purchaseToken
        )
    }

    fun stopSubscription() {
        stopSubscriptionWorkerRunner.startWorker()
    }

    override fun onCleared() {
        viewModelScope.launch {
            billingClientRunner.disconnect()
        }
        super.onCleared()
    }
}