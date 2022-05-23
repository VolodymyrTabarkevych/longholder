package com.traday.longholder.data.local.datasource.notification

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.database.dao.NotificationDao
import com.traday.longholder.data.local.datasource.base.BaseLocalDataSource
import com.traday.longholder.data.local.entity.NotificationEntity
import com.traday.longholder.data.mapper.flowResult
import com.traday.longholder.data.mapper.result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationLocalDataSource @Inject constructor(
    private val notificationDao: NotificationDao
) : BaseLocalDataSource(), INotificationLocalDataSource {

    override suspend fun insertOrUpdateNotifications(notifications: List<NotificationEntity>): Result<List<NotificationEntity>> =
        result {
            val oldNotifications = notificationDao.getNotifications()
            val syncedNotifications = if (oldNotifications.isEmpty()) {
                notifications.map { it.copy(isRead = true) }
            } else {
                notifications.map { newNotification ->
                    oldNotifications
                        .find { oldNotification -> oldNotification.id == newNotification.id }
                        ?.let { oldNotification -> newNotification.copy(isRead = oldNotification.isRead) }
                        ?: newNotification
                }
            }
            notificationDao.insertData(syncedNotifications)
            notificationDao.getNotifications()
        }

    override suspend fun getNotifications(): Result<List<NotificationEntity>> =
        result { notificationDao.getNotifications() }

    override suspend fun deleteNotifications(): Result<Unit> =
        result { notificationDao.deleteNotifications() }

    override fun getNotificationsCountByIsRead(isRead: Boolean): Flow<Result<Int>> =
        flowResult { notificationDao.getCountByIsRead(isRead) }

    override suspend fun setIsReadForAllNotifications(isRead: Boolean): Result<Unit> =
        result { notificationDao.setIsReadForAll(isRead) }
}