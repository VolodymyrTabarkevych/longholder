package com.traday.longholder.data.remote.datasource.notification

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.remote.dto.NotificationDto

interface INotificationRemoteDataSource {

    suspend fun getNotifications(): Result<List<NotificationDto>>
}