package com.traday.longholder.domain.servicerunner

import com.traday.longholder.BuildConfig

interface ICreateSubscriptionWorkerRunner {

    fun startWorker(
        packageName: String,
        productId: String,
        purchaseToken: String,
        deviceName: String = BuildConfig.PLATFORM
    )
}