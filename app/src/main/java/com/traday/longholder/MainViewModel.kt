package com.traday.longholder

import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.enums.Language
import com.traday.longholder.domain.enums.UserStatus
import com.traday.longholder.domain.servicerunner.IBillingClientRunner
import com.traday.longholder.domain.servicerunner.ICreateSubscriptionWorkerRunner
import com.traday.longholder.domain.servicerunner.IGetNotificationsWorkerRunner
import com.traday.longholder.domain.servicerunner.IStopSubscriptionWorkerRunner
import com.traday.longholder.domain.usecase.GetSubscriptionsUseCase
import com.traday.longholder.domain.usecase.IsUserHasSubscriptionUseCase
import com.traday.longholder.domain.usecase.SubscribeOnSelectedLanguageUseCase
import com.traday.longholder.domain.usecase.SubscribeOnUserStatusUseCase
import com.traday.longholder.presentation.base.SubscriptionViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val subscribeOnUserStatusUseCase: SubscribeOnUserStatusUseCase,
    private val getNotificationsWorkerRunner: IGetNotificationsWorkerRunner,
    private val subscribeOnSelectedLanguageUseCase: SubscribeOnSelectedLanguageUseCase,
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

    val userStatus: Flow<Resource<UserStatus>>
        get() = executeUseCase(
            subscribeOnUserStatusUseCase,
            EmptyParams()
        ).onEach(::handleNotificationWorker)


    val selectedLanguage: Flow<Resource<Language>>
        get() = executeUseCase(subscribeOnSelectedLanguageUseCase, EmptyParams())

    private fun handleNotificationWorker(userStatusResource: Resource<UserStatus>) {
        if (userStatusResource !is Resource.Success) return
        when (userStatusResource.data) {
            UserStatus.NOT_AUTHORIZED, UserStatus.NOT_VERIFIED -> {
                getNotificationsWorkerRunner.stopWorker()
            }
            UserStatus.AUTHORIZED, UserStatus.AUTHORIZED_NOT_PASSED_ONBOARDING -> {
                //syncBillingServiceWithServer()
                getNotificationsWorkerRunner.startWorker()
            }
        }
    }

}