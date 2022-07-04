package com.traday.longholder.utils.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.traday.longholder.domain.repository.ISubscriptionRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class CreateSubscriptionWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters,
    private val subscriptionRepository: ISubscriptionRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val packageName =
            inputData.getString(FIELD_PACKAGE_NAME) ?: error("Package name must be provided")
        val productId =
            inputData.getString(FIELD_PRODUCT_ID) ?: error("Product id must be provided")
        val purchaseToken =
            inputData.getString(FIELD_PURCHASE_TOKEN) ?: error("Purchase token must be provided")
        val deviceName =
            inputData.getString(FIELD_DEVICE_NAME) ?: error("Device name must be provided")

        val result = subscriptionRepository.create(
            packageName = packageName,
            productId = productId,
            purchaseToken = purchaseToken,
            deviceName = deviceName
        )
        if (result is com.traday.longholder.data.base.Result.Error) return Result.failure()
        subscriptionRepository.isUserHasSubscription(true)
        return Result.success()
    }

    companion object {

        private const val FIELD_PACKAGE_NAME = "PACKAGE NAME"
        private const val FIELD_PRODUCT_ID = "PRODUCT ID"
        private const val FIELD_PURCHASE_TOKEN = "PURCHASE TOKEN"
        private const val FIELD_DEVICE_NAME = "DEVICE NAME"

        fun startOneTimeWork(
            context: Context,
            packageName: String,
            productId: String,
            purchaseToken: String,
            deviceName: String
        ) {
            val data = Data.Builder().apply {
                putString(FIELD_PACKAGE_NAME, packageName)
                putString(FIELD_PRODUCT_ID, productId)
                putString(FIELD_PURCHASE_TOKEN, purchaseToken)
                putString(FIELD_DEVICE_NAME, deviceName)
            }.build()
            val request = OneTimeWorkRequestBuilder<CreateSubscriptionWorker>()
                .setConstraints(
                    Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
                )
                .setInputData(data)
                .build()
            WorkManager.getInstance(context).enqueue(request)
        }
    }
}