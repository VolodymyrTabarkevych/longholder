package com.traday.longholder.utils.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.traday.longholder.domain.repository.ISubscriptionRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class StopSubscriptionWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters,
    private val subscriptionRepository: ISubscriptionRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val result = subscriptionRepository.stop()
        if (result is com.traday.longholder.data.base.Result.Error) return Result.failure()
        subscriptionRepository.isUserHasSubscription(true)
        return Result.success()
    }

    companion object {

        fun startOneTimeWork(context: Context) {
            val request = OneTimeWorkRequestBuilder<StopSubscriptionWorker>()
                .setConstraints(
                    Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
                )
                .build()
            WorkManager.getInstance(context).enqueue(request)
        }
    }
}