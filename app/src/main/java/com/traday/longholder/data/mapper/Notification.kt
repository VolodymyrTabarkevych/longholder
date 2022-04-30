package com.traday.longholder.data.mapper

import com.traday.longholder.data.local.entity.NotificationEntity
import com.traday.longholder.data.remote.dto.NotificationDto
import com.traday.longholder.domain.model.Notification
import com.traday.longholder.utils.EMPTY_STRING

fun NotificationDto.toEntity() = NotificationEntity(
    id = id,
    name = name,
    valueOfMessage = valueOfMessage ?: EMPTY_STRING,
    linkToTheImage = linkToTheImage ?: EMPTY_STRING,
    dateOfSent = dateOfSent,
    dateOfStart = dateOfStart,
    isRead = false
)

fun NotificationEntity.toDomain() = Notification(
    id = id,
    name = name,
    valueOfMessage = valueOfMessage,
    linkToTheImage = linkToTheImage,
    dateOfSent = dateOfSent,
    dateOfStart = dateOfStart
)