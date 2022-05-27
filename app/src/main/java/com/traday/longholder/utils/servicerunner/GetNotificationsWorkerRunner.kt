package com.traday.longholder.utils.servicerunner

import android.content.Context
import com.traday.longholder.domain.servicerunner.IGetNotificationsWorkerRunner
import com.traday.longholder.utils.work.GetNotificationsWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GetNotificationsWorkerRunner @Inject constructor(
    @ApplicationContext context: Context
) : IGetNotificationsWorkerRunner {

    private val context = context.applicationContext

    override fun startService() {
        GetNotificationsWorker.start(context)
/*        val isScheduled = GetNotificationsWorker.isScheduled(context)
        if (isScheduled) return
        val intent = Intent(context, GetNotificationsWorkerScheduler::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val calendar = Calendar.getInstance(TimeZone.getDefault()).apply {
            val currentHour = get(Calendar.HOUR_OF_DAY)
            if (currentHour >= GET_NOTIFICATIONS_WORKER_SCHEDULE_HOUR) add(Calendar.DAY_OF_YEAR, +1)
            set(Calendar.HOUR_OF_DAY, GET_NOTIFICATIONS_WORKER_SCHEDULE_HOUR)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )*/
    }

    override fun stopService() {
        GetNotificationsWorker.stop(context)
    }

    companion object {

        private const val GET_NOTIFICATIONS_WORKER_SCHEDULE_HOUR = 9
    }
}