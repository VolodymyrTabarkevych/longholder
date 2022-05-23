package com.traday.longholder.data.repository

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.datasource.notification.INotificationLocalDataSource
import com.traday.longholder.data.local.entity.NotificationEntity
import com.traday.longholder.data.mapper.toEntity
import com.traday.longholder.data.remote.datasource.notification.INotificationRemoteDataSource
import com.traday.longholder.domain.repository.INotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationRepository @Inject constructor(
    private val notificationRemoteDataSource: INotificationRemoteDataSource,
    private val notificationLocalDataSource: INotificationLocalDataSource
) : INotificationRepository {

    override suspend fun getNotifications(sync: Boolean): Result<List<NotificationEntity>> {
        if (!sync) return notificationLocalDataSource.getNotifications()
        val remoteNotificationsResult = notificationRemoteDataSource.getNotifications()
        val localNotificationsResult = notificationLocalDataSource.getNotifications()

        return when (remoteNotificationsResult) {
            is Result.Error -> {
                if (localNotificationsResult is Result.Success && localNotificationsResult.data.isNotEmpty()) {
                    localNotificationsResult
                } else {
                    remoteNotificationsResult
                }
            }
            is Result.Success -> {
                val mappedRemoteNotifications = remoteNotificationsResult.data.map { it.toEntity() }
                notificationLocalDataSource.insertOrUpdateNotifications(mappedRemoteNotifications)
            }
        }
    }

    override fun getNotificationsCountByIsRead(isRead: Boolean): Flow<Result<Int>> {
        return notificationLocalDataSource.getNotificationsCountByIsRead(isRead)
    }

    override suspend fun setIsReadForAllNotifications(isRead: Boolean): Result<Unit> {
        return notificationLocalDataSource.setIsReadForAllNotifications(isRead)
    }
}