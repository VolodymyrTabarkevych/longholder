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

    override suspend fun getNotifications(): Result<List<NotificationEntity>> {
        val remoteNotificationsResult = notificationRemoteDataSource.getNotifications()
        val localNotificationsResult = notificationLocalDataSource.getNotifications()

        when (remoteNotificationsResult) {
            is Result.Error -> {
                return if (localNotificationsResult is Result.Success && localNotificationsResult.data.isNotEmpty()) {
                    localNotificationsResult
                } else {
                    remoteNotificationsResult
                }
            }
            is Result.Success -> {
                val remoteNotifications = remoteNotificationsResult.data.map { it.toEntity() }
                val localNotifications =
                    if (localNotificationsResult is Result.Success) localNotificationsResult.data else emptyList()
                val syncedNotifications =
                    syncNotifications(
                        oldNotifications = localNotifications,
                        newNotifications = remoteNotifications
                    )
                notificationLocalDataSource.setNotifications(syncedNotifications)
                return notificationLocalDataSource.getNotifications()
            }
        }
    }

    private fun syncNotifications(
        oldNotifications: List<NotificationEntity>,
        newNotifications: List<NotificationEntity>
    ): List<NotificationEntity> {
        if (oldNotifications.isEmpty()) return newNotifications.map { it.copy(isRead = true) }
        return newNotifications.map { newNotification ->
            oldNotifications
                .find { oldNotification -> oldNotification.id == newNotification.id }
                ?.let { oldNotification -> newNotification.copy(isRead = oldNotification.isRead) }
                ?: newNotification
        }
    }

    override fun getNotificationsCountByIsRead(isRead: Boolean): Flow<Result<Int>> {
        return notificationLocalDataSource.getNotificationsCountByIsRead(isRead)
    }

    override suspend fun setIsReadForAllNotifications(isRead: Boolean): Result<Unit> {
        return notificationLocalDataSource.setIsReadForAllNotifications(isRead)
    }
}