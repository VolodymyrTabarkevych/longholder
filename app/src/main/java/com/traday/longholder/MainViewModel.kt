package com.traday.longholder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.enums.UserStatus
import com.traday.longholder.domain.usecase.GetUserStatusUseCase
import com.traday.longholder.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserStatusUseCase: GetUserStatusUseCase
) : BaseViewModel() {

    private val _userStatusLiveData = MutableLiveData<UserStatus>()
    val userStatus: LiveData<UserStatus> get() = _userStatusLiveData

    init {
        getUserStatus()
    }

    private fun getUserStatus() {
        executeUseCase(getUserStatusUseCase, EmptyParams()) {
            if (it is Resource.Success) {
                _userStatusLiveData.postValue(it.data)
            }
        }
    }
}