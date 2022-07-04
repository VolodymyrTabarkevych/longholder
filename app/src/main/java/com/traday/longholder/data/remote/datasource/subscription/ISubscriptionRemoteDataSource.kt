package com.traday.longholder.data.remote.datasource.subscription

import com.traday.longholder.data.base.Result

interface ISubscriptionRemoteDataSource {

    suspend fun create(
        packageName: String,
        productId: String,
        purchaseToken: String,
        deviceName: String
    ): Result<Unit>

    suspend fun stop(): Result<Unit>

    suspend fun isUserHasSubscription(): Result<Boolean>

    suspend fun getSubscriptions(): Result<List<String>>
}