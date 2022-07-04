package com.traday.longholder.data.repository

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.error.exceptions.BaseException
import com.traday.longholder.data.local.datasource.subscription.ISubscriptionLocalDataSource
import com.traday.longholder.data.remote.datasource.subscription.ISubscriptionRemoteDataSource
import com.traday.longholder.domain.repository.ISubscriptionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubscriptionRepository @Inject constructor(
    private val subscriptionRemoteDataSource: ISubscriptionRemoteDataSource,
    private val subscriptionLocalDataSource: ISubscriptionLocalDataSource
) : ISubscriptionRepository {

    override suspend fun create(
        packageName: String,
        productId: String,
        purchaseToken: String,
        deviceName: String
    ): Result<Unit> {
        return subscriptionRemoteDataSource.create(
            packageName = packageName,
            productId = productId,
            purchaseToken = purchaseToken,
            deviceName = deviceName
        )
    }

    override suspend fun stop(): Result<Unit> {
        return subscriptionRemoteDataSource.stop()
    }

    override suspend fun isUserHasSubscription(sync: Boolean): Result<Boolean> {
        if (!sync) return subscriptionLocalDataSource.isUserHasSubscription()

        return when (val remoteResult = subscriptionRemoteDataSource.isUserHasSubscription()) {
            is Result.Error -> {
                if (remoteResult.error is BaseException.UserNotSubscribed) {
                    subscriptionLocalDataSource.setUserHasSubscription(false)
                }
                val localResult = subscriptionLocalDataSource.isUserHasSubscription()
                if (localResult is Result.Success) {
                    localResult
                } else {
                    remoteResult
                }
            }
            is Result.Success -> {
                subscriptionLocalDataSource.setUserHasSubscription(remoteResult.data)
            }
        }
    }

    override fun subscribeOnIsUserHasSubscription(): Flow<Result<Boolean>> {
        return subscriptionLocalDataSource.subscribeOnIsUserHasSubscription()
    }

    override suspend fun getSubscriptions(): Result<List<String>> {
        return subscriptionRemoteDataSource.getSubscriptions()
    }
}