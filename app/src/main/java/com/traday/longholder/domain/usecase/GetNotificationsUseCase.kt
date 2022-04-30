package com.traday.longholder.domain.usecase

import com.traday.longholder.data.mapper.toDomain
import com.traday.longholder.data.mapper.toResource
import com.traday.longholder.domain.base.BaseUseCase
import com.traday.longholder.domain.base.EmptyParams
import com.traday.longholder.domain.base.Resource
import com.traday.longholder.domain.error.handlers.IErrorHandler
import com.traday.longholder.domain.model.Notification
import com.traday.longholder.domain.repository.INotificationRepository
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(
    private val notificationRepository: INotificationRepository,
    private val errorHandler: IErrorHandler
) : BaseUseCase<EmptyParams, List<Notification>>() {

    override suspend fun run(params: EmptyParams): Resource<List<Notification>> {
        return notificationRepository.getNotifications()
            .toResource(errorHandler) { notification ->
                notification.map { it.toDomain() }
            }
    }
}