package com.traday.longholder.presentation.onboarding

import androidx.lifecycle.LiveData
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.usecase.SetPassedOnboardingUseCase
import com.traday.longholder.presentation.base.BaseViewModel
import com.traday.longholder.presentation.base.EventLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val setPassedOnboardingUseCase: SetPassedOnboardingUseCase
) : BaseViewModel() {

    private val _setPassedOnboardingLiveData = EventLiveData<Unit>()
    val setPassedOnboardingLiveData: LiveData<Unit> get() = _setPassedOnboardingLiveData

    fun notifyOnboardingPassed() {
        executeUseCase(setPassedOnboardingUseCase, SetPassedOnboardingUseCase.Params(true)) {
            when (it) {
                is Resource.Error -> _setPassedOnboardingLiveData.postValue(Unit)
                is Resource.Success -> _setPassedOnboardingLiveData.postValue(Unit)
            }
        }
    }
}