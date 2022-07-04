package com.traday.longholder.utils.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.navigation.NavDeepLinkBuilder
import androidx.work.*
import com.google.common.util.concurrent.ListenableFuture
import com.traday.longholder.R
import com.traday.longholder.domain.repository.INotificationRepository
import com.traday.longholder.extensions.getColorCompat
import com.traday.longholder.presentation.active.ActiveScreenMode
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
                showHoldingHasEndedNotification(it.id, it.activeId, it.earnedMoney)
            }
        }
        notificationRepository.setIsReadForAllNotifications(true)
        return Result.success()
    }

    private fun showHoldingHasEndedNotification(
        notificationId: Int,
        activeId: Int,
        earnedMoney: Double
    ) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(manager)
        }
        createNotification(
            notificationId = notificationId,
            notificationManager = manager,
            activeId = activeId,
            earnedMoney = earnedMoney
        )
    }

    private fun createNotification(
        notificationId: Int,
        notificationManager: NotificationManager,
        activeId: Int,
        earnedMoney: Double
    ) {
        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.nav_main)
            .addDestination(R.id.activeFragment)
            .setArguments(Bundle().apply {
                putParcelable("mode", ActiveScreenMode.ViewEndedActive(activeId))
            })
            .createPendingIntent()

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
        val notification = NotificationCompat.Builder(context, HOLD_ENDED_CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_notification)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(contentView)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(notificationId, notification)
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

        fun startPeriodicWork(context: Context) {
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