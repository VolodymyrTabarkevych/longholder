package com.traday.longholder.data.mapper

import com.traday.longholder.data.local.entity.NotificationEntity
import com.traday.longholder.data.remote.dto.NotificationDto
import com.traday.longholder.domain.model.Notification
import com.traday.longholder.extensions.formatDateServerFormatToClientFormatOrEmpty
import com.traday.longholder.extensions.replaceDotWithComma

fun NotificationDto.toEntity() = NotificationEntity(
    id = id,
    name = name,
    valueOfMessage = valueOfMessage,
    earnedMoney = earnedMoney,
    linkToTheImage = linkToTheImage,
    dateOfSent = dateOfSent.formatDateServerFormatToClientFormatOrEmpty(),
    dateOfStart = dateOfStart.formatDateServerFormatToClientFormatOrEmpty(),
    isRead = false
)

fun NotificationEntity.toDomain() = Notification(
    id = id,
    name = name,
    valueOfMessage = valueOfMessage,
    earnedMoney = earnedMoney,
    earnedMoneyFormatted = earnedMoney.replaceDotWithComma(),
    linkToTheImage = linkToTheImage,
    dateOfSent = dateOfSent,
    dateOfStart = dateOfStart
)