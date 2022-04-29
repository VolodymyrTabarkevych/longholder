package com.traday.longholder.data.mapper

import com.traday.longholder.domain.model.Crypto
import com.traday.longholder.local.entity.CryptoEntity
import com.traday.longholder.remote.dto.CryptoDto
import com.traday.longholder.utils.EMPTY_STRING

fun CryptoDto.toEntity() = CryptoEntity(
    id = id,
    name = name ?: EMPTY_STRING,
    valueOfCrypto = valueOfCrypto,
    cryptoPriceOnStart = cryptoPriceOnStart,
    cryptoPriceOnEnd = cryptoPriceOnEnd,
    priceInOtherCurrencyOnStart = priceInOtherCurrencyOnStart,
    priceInOtherCurrencyOnEnd = priceInOtherCurrencyOnEnd,
    currencyPrice = currencyPrice,
    nameOfCurrency = nameOfCurrency ?: EMPTY_STRING,
    dateOfStart = dateOfStart,
    dateOfEnd = dateOfEnd,
    comment = comment ?: EMPTY_STRING
)

fun CryptoEntity.toDomain() = Crypto(
    id = id,
    name = name,
    valueOfCrypto = valueOfCrypto,
    cryptoPriceOnStart = cryptoPriceOnStart,
    cryptoPriceOnEnd = cryptoPriceOnEnd,
    priceInOtherCurrencyOnStart = priceInOtherCurrencyOnStart,
    priceInOtherCurrencyOnEnd = priceInOtherCurrencyOnEnd,
    currencyPrice = currencyPrice,
    nameOfCurrency = nameOfCurrency,
    dateOfStart = dateOfStart,
    dateOfEnd = dateOfEnd,
    comment = comment
)