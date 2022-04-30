package com.traday.longholder.data.local.datasource.notification

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow

interface INotificationLocalDataSource {

    suspend fun setNotifications(notifications: List<NotificationEntity>): Result<Unit>

    suspend fun getNotifications(): Result<List<NotificationEntity>>

    suspend fun deleteNotifications(): Result<Unit>

    fun getNotificationsCountByIsRead(isRead: Boolean): Flow<Result<Int>>

    suspend fun setIsReadForAllNotifications(isRead: Boolean): Result<Unit>
}