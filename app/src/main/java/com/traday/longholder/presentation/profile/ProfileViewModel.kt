package com.traday.longholder.presentation.profile

import androidx.lifecycle.LiveData
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.usecase.LogoutUseCase
import com.traday.longholder.presentation.base.BaseViewModel
import com.traday.longholder.presentation.base.EventLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel() {

    private val _logoutLiveData = EventLiveData<Unit>()
    val logoutLiveData: LiveData<Unit> get() = _logoutLiveData

    fun logout() {
        executeUseCase(logoutUseCase, EmptyParams()) {
            when (it) {
                is Resource.Error -> _logoutLiveData.postValue(Unit)
                is Resource.Success -> _logoutLiveData.postValue(Unit)
            }
        }
    }
}