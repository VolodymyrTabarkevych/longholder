package com.traday.longholder.data.mapper

import com.traday.longholder.data.local.entity.NotificationEntity
import com.traday.longholder.data.remote.dto.NotificationDto
import com.traday.longholder.domain.model.Notification

fun NotificationDto.toEntity() = NotificationEntity(
    id = id,
    activeId = activeId,
    name = name,
    nameOfCoin = nameOfCoin,
    valueOfMessage = valueOfMessage,
    earnedMoney = earnedMoney,
    linkToTheImage = linkToTheImage,
    dateOfSent = dateOfSent,
    dateOfStart = dateOfStart,
    isRead = false
)

fun NotificationEntity.toDomain() = Notification(
    id = id,
    activeId = activeId,
    name = name,
    nameOfCoin = nameOfCoin,
    valueOfMessage = valueOfMessage,
    earnedMoney = earnedMoney,
    linkToTheImage = linkToTheImage,
    dateOfSent = dateOfSent,
    dateOfStart = dateOfStart
)