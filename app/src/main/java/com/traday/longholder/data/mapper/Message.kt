package com.traday.longholder.data.mapper

import com.traday.longholder.domain.model.Message
import com.traday.longholder.local.entity.MessageEntity
import com.traday.longholder.remote.dto.MessageDto
import com.traday.longholder.utils.EMPTY_STRING

fun MessageDto.toEntity() = MessageEntity(
    id = id,
    name = name,
    valueOfMessage = valueOfMessage ?: EMPTY_STRING,
    linkToTheImage = linkToTheImage ?: EMPTY_STRING,
    dateOfSent = dateOfSent,
    dateOfStart = dateOfStart
)

fun MessageEntity.toDomain() = Message(
    id = id,
    name = name,
    valueOfMessage = valueOfMessage,
    linkToTheImage = linkToTheImage,
    dateOfSent = dateOfSent,
    dateOfStart = dateOfStart
)