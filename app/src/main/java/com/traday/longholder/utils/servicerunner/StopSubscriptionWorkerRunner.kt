package com.traday.longholder.utils.servicerunner

import android.content.Context
import com.traday.longholder.domain.servicerunner.IStopSubscriptionWorkerRunner
import com.traday.longholder.utils.work.StopSubscriptionWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StopSubscriptionWorkerRunner @Inject constructor(
    @ApplicationContext context: Context
) : IStopSubscriptionWorkerRunner {

    private val context = context.applicationContext

    override fun startWorker() {
        StopSubscriptionWorker.startOneTimeWork(context)
    }
}