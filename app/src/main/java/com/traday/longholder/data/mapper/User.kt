package com.traday.longholder.data.mapper

import com.traday.longholder.data.local.entity.UserEntity
import com.traday.longholder.data.remote.dto.UserDto
import com.traday.longholder.domain.model.User
import com.traday.longholder.utils.EMPTY_STRING

fun UserDto.toEntity() = UserEntity(
    userName = userName ?: EMPTY_STRING,
    email = email ?: EMPTY_STRING,
    currencyCode = currencyCode ?: EMPTY_STRING,
    isOnSubscription = isOnSubscription
)

fun UserEntity.toDomain() = User(
    userName = userName,
    email = email,
    currencyCode = currencyCode,
    isOnSubscription = isOnSubscription
)