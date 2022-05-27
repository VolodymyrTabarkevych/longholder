package com.traday.longholder.presentation.subscription

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.usecase.SubscribeIsUserOnSubscriptionUseCase
import com.traday.longholder.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    private val subscribeIsUserOnSubscriptionUseCase: SubscribeIsUserOnSubscriptionUseCase
) : BaseViewModel() {

    val isUserOnSubscription: LiveData<Resource<Boolean>> = executeUseCase(
        subscribeIsUserOnSubscriptionUseCase,
        params = SubscribeIsUserOnSubscriptionUseCase.Params(false)
    ).asLiveData()

    private val _onboardingButtonsLiveData = MutableLiveData<Boolean>()
    val onboardingButtonsLiveData: LiveData<Boolean> get() = _onboardingButtonsLiveData

    fun changeOnboardingButtons(isOnLastOnboardingPage: Boolean) {
        _onboardingButtonsLiveData.postValue(isOnLastOnboardingPage)
    }
}