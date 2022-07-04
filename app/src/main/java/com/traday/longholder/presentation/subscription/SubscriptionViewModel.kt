package com.traday.longholder.presentation.subscription

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.servicerunner.IBillingClientRunner
import com.traday.longholder.domain.servicerunner.ICreateSubscriptionWorkerRunner
import com.traday.longholder.domain.servicerunner.IStopSubscriptionWorkerRunner
import com.traday.longholder.domain.usecase.GetSubscriptionsUseCase
import com.traday.longholder.domain.usecase.IsUserHasSubscriptionUseCase
import com.traday.longholder.domain.usecase.SubscribeIsUserOnSubscriptionUseCase
import com.traday.longholder.presentation.base.SubscriptionViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    subscribeIsUserOnSubscriptionUseCase: SubscribeIsUserOnSubscriptionUseCase,
    billingClientRunner: IBillingClientRunner,
    createSubscriptionWorkerRunner: ICreateSubscriptionWorkerRunner,
    stopSubscriptionWorkerRunner: IStopSubscriptionWorkerRunner,
    getSubscriptionsUseCase: GetSubscriptionsUseCase,
    isUserHasSubscriptionUseCase: IsUserHasSubscriptionUseCase
) : SubscriptionViewModel(
    billingClientRunner,
    createSubscriptionWorkerRunner,
    stopSubscriptionWorkerRunner,
    getSubscriptionsUseCase,
    isUserHasSubscriptionUseCase
) {

    val isUserOnSubscription: LiveData<Resource<Boolean>> = executeUseCase(
        subscribeIsUserOnSubscriptionUseCase,
        EmptyParams()
    ).asLiveData()

    private val _onboardingButtonsLiveData = MutableLiveData<Boolean>()
    val onboardingButtonsLiveData: LiveData<Boolean> get() = _onboardingButtonsLiveData

    fun changeOnboardingButtons(isOnLastOnboardingPage: Boolean) {
        _onboardingButtonsLiveData.postValue(isOnLastOnboardingPage)
    }
}