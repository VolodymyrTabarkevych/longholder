package com.traday.longholder.utils.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.google.common.util.concurrent.ListenableFuture
import com.traday.longholder.MainActivity
import com.traday.longholder.R
import com.traday.longholder.domain.repository.INotificationRepository
import com.traday.longholder.extensions.getColorCompat
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit

@HiltWorker
class GetNotificationsWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters,
    private val notificationRepository: INotificationRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val notificationsResult = notificationRepository.getNotifications(true)
        if (notificationsResult !is com.traday.longholder.data.base.Result.Success) return Result.failure()
        val notifications = notificationsResult.data
        notifications.forEach {
            if (!it.isRead) {
                showHoldingHasEndedNotification(it.id, it.earnedMoney)
            }
        }
        notificationRepository.setIsReadForAllNotifications(true)
        return Result.success()
    }

    private fun showHoldingHasEndedNotification(id: Int, earnedMoney: Double) {
        val intent = Intent(context, MainActivity::class.java)
        val resultPendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val contentView = RemoteViews(context.packageName, R.layout.notification_hold_ended)
        val textColor = if (earnedMoney >= 0) R.color.limeade else R.color.thunderbird
        contentView.setTextColor(
            R.id.tvNotification,
            context.getColorCompat(textColor)
        )
        contentView.setTextViewText(
            R.id.tvNotificationTitle,
            context.getString(R.string.push_notification_holding_has_ended)
        )
        contentView.setTextViewText(
            R.id.tvNotification,
            context.getString(R.string.push_notification_you_earned, earnedMoney)
        )

        val notification = NotificationCompat.Builder(
            context,
            HOLD_ENDED_CHANNEL_ID
        )
            .setContentIntent(resultPendingIntent)
            .setSmallIcon(R.drawable.ic_notification)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(contentView)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(manager)
        }
        manager.notify(id, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            HOLD_ENDED_CHANNEL_ID,
            HOLD_ENDED_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }

    companion object {

        private const val WORKER_NAME = "notifications"
        private const val HOLD_ENDED_CHANNEL_ID = "holding_ended"
        private const val HOLD_ENDED_CHANNEL_NAME = "Holding ended"
        private const val REPEAT_INTERVAL_HOURS = 1L

        fun start(context: Context) {
            val request = PeriodicWorkRequestBuilder<GetNotificationsWorker>(
                REPEAT_INTERVAL_HOURS,
                TimeUnit.HOURS
            ).setConstraints(
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            ).build()
            WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(
                    WORKER_NAME,
                    ExistingPeriodicWorkPolicy.KEEP,
                    request
                )
        }

        fun stop(context: Context) {
            WorkManager.getInstance(context).cancelUniqueWork(WORKER_NAME)
        }

        fun isScheduled(context: Context): Boolean {
            val statuses: ListenableFuture<List<WorkInfo>> =
                WorkManager.getInstance(context).getWorkInfosForUniqueWork(WORKER_NAME)
            return try {
                var running = false
                statuses.get().forEach {
                    running =
                        it.state == WorkInfo.State.RUNNING || it.state == WorkInfo.State.ENQUEUED
                }
                return running
            } catch (e: Exception) {
                false
            }
        }
    }
}