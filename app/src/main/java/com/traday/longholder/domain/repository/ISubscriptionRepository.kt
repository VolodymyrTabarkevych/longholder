package com.traday.longholder.domain.repository

import com.traday.longholder.data.base.Result
import kotlinx.coroutines.flow.Flow

interface ISubscriptionRepository {

    suspend fun create(
        packageName: String,
        productId: String,
        purchaseToken: String,
        deviceName: String
    ): Result<Unit>

    suspend fun stop(): Result<Unit>

    suspend fun isUserHasSubscription(sync: Boolean): Result<Boolean>

    fun subscribeOnIsUserHasSubscription(): Flow<Result<Boolean>>

    suspend fun getSubscriptions(): Result<List<String>>
}