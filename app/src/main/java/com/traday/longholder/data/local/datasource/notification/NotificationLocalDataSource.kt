package com.traday.longholder.data.local.datasource.notification

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.database.dao.NotificationDao
import com.traday.longholder.data.local.datasource.base.BaseLocalDataSource
import com.traday.longholder.data.local.entity.NotificationEntity
import com.traday.longholder.extensions.safeFlow
import com.traday.longholder.extensions.safeOperation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationLocalDataSource @Inject constructor(
    private val notificationDao: NotificationDao
) : BaseLocalDataSource(), INotificationLocalDataSource {

    override suspend fun setNotifications(notifications: List<NotificationEntity>): Result<Unit> =
        safeOperation { notificationDao.insertData(notifications) }

    override suspend fun getNotifications(): Result<List<NotificationEntity>> =
        safeOperation { notificationDao.getNotifications() }

    override suspend fun deleteNotifications(): Result<Unit> =
        safeOperation { notificationDao.deleteNotifications() }

    override fun getNotificationsCountByIsRead(isRead: Boolean): Flow<Result<Int>> =
        safeFlow { notificationDao.getCountByIsRead(isRead) }

    override suspend fun setIsReadForAllNotifications(isRead: Boolean): Result<Unit> =
        safeOperation {
            println("SET is read $isRead")
            notificationDao.setIsReadForAll(isRead)
        }
}