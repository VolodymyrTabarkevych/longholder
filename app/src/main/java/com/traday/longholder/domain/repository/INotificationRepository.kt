package com.traday.longholder.domain.repository

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow

interface INotificationRepository {

    suspend fun getNotifications(): Result<List<NotificationEntity>>

    fun getNotificationsCountByIsRead(isRead: Boolean): Flow<Result<Int>>

    suspend fun setIsReadForAllNotifications(isRead: Boolean): Result<Unit>
}