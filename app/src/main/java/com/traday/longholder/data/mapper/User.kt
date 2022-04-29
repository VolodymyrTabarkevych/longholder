package com.traday.longholder.data.mapper

import com.traday.longholder.domain.model.User
import com.traday.longholder.local.entity.UserEntity
import com.traday.longholder.remote.dto.UserDto
import com.traday.longholder.utils.EMPTY_STRING

fun UserDto.toEntity() = UserEntity(
    userName = userName ?: EMPTY_STRING,
    email = email ?: EMPTY_STRING,
    currencyCode = currencyCode ?: EMPTY_STRING/*,
    cryptos = cryptos.map { it.toEntity() },
    messages = messages.map { it.toEntity() },
    reports = reports.map { it.toEntity() }*/
)

fun UserEntity.toDomain() = User(
    userName = userName,
    email = email,
    currencyCode = currencyCode/*,
    cryptos = cryptos.map { it.toDomain() },
    messages = messages.map { it.toDomain() },
    reports = reports.map { it.toDomain() }*/
)