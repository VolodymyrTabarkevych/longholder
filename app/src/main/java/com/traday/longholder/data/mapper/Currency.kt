package com.traday.longholder.data.mapper

import com.traday.longholder.data.local.entity.CurrencyEntity
import com.traday.longholder.data.remote.dto.CurrencyDto
import com.traday.longholder.domain.model.Currency
import com.traday.longholder.extensions.formatDateServerFormatToClientFormatOrEmpty

fun CurrencyDto.toEntity(id: Int) = CurrencyEntity(
    id = id,
    linkToPhoto = linkToPhoto,
    name = name,
    indexOnExchange = indexOnExchange,
    price = price,
    dateOfUpdate = dateOfUpdate.formatDateServerFormatToClientFormatOrEmpty(),
    symbol = symbol
)

fun CurrencyEntity.toDomain() = Currency(
    id = id,
    linkToPhoto = linkToPhoto,
    name = name,
    indexOnExchange = indexOnExchange,
    price = price,
    dateOfUpdate = dateOfUpdate,
    symbol = symbol
)