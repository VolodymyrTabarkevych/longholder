package com.traday.longholder.data.mapper

import com.traday.longholder.data.local.entity.ActiveEntity
import com.traday.longholder.data.remote.dto.ActiveDto
import com.traday.longholder.domain.model.Active
import com.traday.longholder.extensions.formatDateClientFormatToServerFormatOrEmpty
import com.traday.longholder.extensions.formatDateServerFormatToClientFormatOrEmpty

fun ActiveDto.toEntity() = ActiveEntity(
    id = id,
    name = name,
    valueOfCrypto = valueOfCrypto,
    cryptoPriceOnStart = cryptoPriceOnStart,
    cryptoPriceOnEnd = cryptoPriceOnEnd,
    priceInOtherCurrencyOnStart = priceInOtherCurrencyOnStart,
    priceInOtherCurrencyOnEnd = priceInOtherCurrencyOnEnd,
    currentCurrencyPrice = currentCurrencyPrice,
    nameOfCurrency = nameOfCurrency,
    dateOfStart = dateOfStart.formatDateServerFormatToClientFormatOrEmpty(),
    dateOfEnd = dateOfEnd.formatDateServerFormatToClientFormatOrEmpty(),
    comment = comment,
    linkToImage = linkToImage,
    earnedMoney = earnedMoney,
    percents = percents,
    symbol = symbol
)

fun Active.toEntity() = ActiveEntity(
    id = id,
    name = name,
    valueOfCrypto = valueOfCrypto,
    cryptoPriceOnStart = cryptoPriceOnStart,
    cryptoPriceOnEnd = cryptoPriceOnEnd,
    priceInOtherCurrencyOnStart = priceInOtherCurrencyOnStart,
    priceInOtherCurrencyOnEnd = priceInOtherCurrencyOnEnd,
    currentCurrencyPrice = currentCurrencyPrice,
    nameOfCurrency = nameOfCurrency,
    dateOfStart = dateOfStart,
    dateOfEnd = dateOfEnd,
    comment = comment,
    linkToImage = linkToImage,
    earnedMoney = earnedMoney,
    percents = percents,
    symbol = symbol
)

fun ActiveEntity.toDomain() = Active(
    id = id,
    name = name,
    valueOfCrypto = valueOfCrypto,
    cryptoPriceOnStart = cryptoPriceOnStart,
    cryptoPriceOnEnd = cryptoPriceOnEnd,
    priceInOtherCurrencyOnStart = priceInOtherCurrencyOnStart,
    priceInOtherCurrencyOnEnd = priceInOtherCurrencyOnEnd,
    currentCurrencyPrice = currentCurrencyPrice,
    nameOfCurrency = nameOfCurrency,
    dateOfStart = dateOfStart,
    dateOfEnd = dateOfEnd,
    comment = comment,
    linkToImage = linkToImage,
    earnedMoney = earnedMoney,
    percents = percents,
    symbol = symbol
)

fun ActiveDto.toDomain() = Active(
    id = id,
    name = name,
    valueOfCrypto = valueOfCrypto,
    cryptoPriceOnStart = cryptoPriceOnStart,
    cryptoPriceOnEnd = cryptoPriceOnEnd,
    priceInOtherCurrencyOnStart = priceInOtherCurrencyOnStart,
    priceInOtherCurrencyOnEnd = priceInOtherCurrencyOnEnd,
    currentCurrencyPrice = currentCurrencyPrice,
    nameOfCurrency = nameOfCurrency,
    dateOfStart = dateOfStart.formatDateServerFormatToClientFormatOrEmpty(),
    dateOfEnd = dateOfEnd.formatDateServerFormatToClientFormatOrEmpty(),
    comment = comment,
    linkToImage = linkToImage,
    earnedMoney = earnedMoney,
    percents = percents,
    symbol = symbol
)

fun Active.toDto() = ActiveDto(
    id = id,
    name = name,
    valueOfCrypto = valueOfCrypto,
    cryptoPriceOnStart = cryptoPriceOnStart,
    cryptoPriceOnEnd = cryptoPriceOnEnd,
    priceInOtherCurrencyOnStart = priceInOtherCurrencyOnStart,
    priceInOtherCurrencyOnEnd = priceInOtherCurrencyOnEnd,
    currentCurrencyPrice = currentCurrencyPrice,
    nameOfCurrency = nameOfCurrency,
    dateOfStart = dateOfStart.formatDateClientFormatToServerFormatOrEmpty(),
    dateOfEnd = dateOfEnd.formatDateClientFormatToServerFormatOrEmpty(),
    comment = comment,
    linkToImage = linkToImage,
    earnedMoney = earnedMoney,
    percents = percents,
    symbol = symbol
)

