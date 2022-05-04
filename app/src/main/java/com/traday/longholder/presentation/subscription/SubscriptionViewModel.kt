package com.traday.longholder.presentation.subscription

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.traday.longholder.presentation.base.BaseViewModel

class SubscriptionViewModel : BaseViewModel() {

    private val _onboardingButtonsLiveData = MutableLiveData<Boolean>()
    val onboardingButtonsLiveData: LiveData<Boolean> get() = _onboardingButtonsLiveData

    private val _subscriptionLiveData = MutableLiveData(false)
    val subscriptionLiveData: LiveData<Boolean> get() = _subscriptionLiveData

    fun changeOnboardingButtons(isOnLastOnboardingPage: Boolean) {
        _onboardingButtonsLiveData.postValue(isOnLastOnboardingPage)
    }

    fun makeSubscription() {
        _subscriptionLiveData.postValue(true)
    }

    fun cancelSubscription() {
        _subscriptionLiveData.postValue(false)
    }
}