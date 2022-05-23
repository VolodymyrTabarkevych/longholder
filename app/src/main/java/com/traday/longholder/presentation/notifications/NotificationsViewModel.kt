package com.traday.longholder.presentation.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.model.Notification
import com.traday.longholder.domain.usecase.GetNotificationsUseCase
import com.traday.longholder.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase
) : BaseViewModel() {

    private val _getNotificationsLiveData = MutableLiveData<Resource<List<Notification>>>()
    val getNotificationsLiveData: LiveData<Resource<List<Notification>>> get() = _getNotificationsLiveData


    init {
        getNotifications()
    }

    private fun getNotifications() {
        executeUseCase(getNotificationsUseCase, GetNotificationsUseCase.Params(false)) {
            _getNotificationsLiveData.postValue(it)
        }
    }
}