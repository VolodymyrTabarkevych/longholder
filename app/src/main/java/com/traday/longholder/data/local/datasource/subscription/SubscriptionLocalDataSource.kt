package com.traday.longholder.data.local.datasource.subscription

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.preferences.subscription.ISubscriptionPreferences
import com.traday.longholder.data.mapper.flowResult
import com.traday.longholder.data.mapper.result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubscriptionLocalDataSource @Inject constructor(
    private val subscriptionPreferences: ISubscriptionPreferences
) : ISubscriptionLocalDataSource {

    override suspend fun setUserHasSubscription(hasSubscription: Boolean): Result<Boolean> =
        result {
            subscriptionPreferences.setUserHasSubscription(hasSubscription)
            subscriptionPreferences.isUserHasSubscription()
        }

    override suspend fun isUserHasSubscription(): Result<Boolean> = result {
        subscriptionPreferences.isUserHasSubscription()
    }

    override fun subscribeOnIsUserHasSubscription(): Flow<Result<Boolean>> = flowResult {
        subscriptionPreferences.subscribeOnIsUserHasSubscription()
    }
}