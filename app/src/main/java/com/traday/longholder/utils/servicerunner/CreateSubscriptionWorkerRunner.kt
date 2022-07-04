package com.traday.longholder.utils.servicerunner

import android.content.Context
import com.traday.longholder.domain.servicerunner.ICreateSubscriptionWorkerRunner
import com.traday.longholder.utils.work.CreateSubscriptionWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateSubscriptionWorkerRunner @Inject constructor(
    @ApplicationContext context: Context
) : ICreateSubscriptionWorkerRunner {

    private val context = context.applicationContext

    override fun startWorker(
        packageName: String,
        productId: String,
        purchaseToken: String,
        deviceName: String
    ) {
        CreateSubscriptionWorker.startOneTimeWork(
            context = context,
            packageName = packageName,
            productId = productId,
            purchaseToken = purchaseToken,
            deviceName = deviceName
        )
    }
}