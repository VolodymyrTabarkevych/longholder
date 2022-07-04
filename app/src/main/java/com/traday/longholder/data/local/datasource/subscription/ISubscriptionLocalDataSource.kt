package com.traday.longholder.data.local.datasource.subscription

import com.traday.longholder.data.base.Result
import kotlinx.coroutines.flow.Flow

interface ISubscriptionLocalDataSource {

    suspend fun setUserHasSubscription(hasSubscription: Boolean): Result<Boolean>

    suspend fun isUserHasSubscription(): Result<Boolean>

    fun subscribeOnIsUserHasSubscription(): Flow<Result<Boolean>>
}