package com.traday.longholder

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.enums.UserStatus
import com.traday.longholder.domain.servicerunner.IGetNotificationsWorkerRunner
import com.traday.longholder.domain.usecase.SubscribeOnUserStatusUseCase
import com.traday.longholder.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val subscribeOnUserStatusUseCase: SubscribeOnUserStatusUseCase,
    private val getNotificationsWorkerRunner: IGetNotificationsWorkerRunner
) : BaseViewModel() {

    val userStatus: LiveData<Resource<UserStatus>>
        get() = executeUseCase(
            subscribeOnUserStatusUseCase,
            EmptyParams()
        ).onEach(::handleNotificationWorker).asLiveData()

    private fun handleNotificationWorker(userStatusResource: Resource<UserStatus>) {
        if (userStatusResource !is Resource.Success) return
        when (userStatusResource.data) {
            UserStatus.NOT_AUTHORIZED, UserStatus.NOT_VERIFIED -> getNotificationsWorkerRunner.stopService()
            UserStatus.AUTHORIZED, UserStatus.AUTHORIZED_NOT_PASSED_ONBOARDING -> getNotificationsWorkerRunner.startService()
        }
    }
}