package com.traday.longholder.presentation.profile

import com.traday.longholder.domain.base.Resource
import com.traday.longholder.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : BaseViewModel() {

    fun logout() {
        _unauthorizedLiveData.postValue(Resource.Success(Unit))
    }
}