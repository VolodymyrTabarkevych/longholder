package com.traday.longholder.utils.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.traday.longholder.utils.work.GetNotificationsWorker

class GetNotificationsWorkerScheduler : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        GetNotificationsWorker.startPeriodicWork(context)
    }
}