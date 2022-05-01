package com.traday.longholder.data.mapper

import com.traday.longholder.data.local.entity.CryptoEntity
import com.traday.longholder.data.remote.dto.CryptoDto
import com.traday.longholder.domain.model.Crypto
import com.traday.longholder.extensions.formatDateServerFormatToClientFormatOrEmpty
import com.traday.longholder.extensions.replaceDotWithComma

fun CryptoDto.toEntity() = CryptoEntity(
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
    percents = percents
)

fun CryptoEntity.toDomain() = Crypto(
    id = id,
    name = name,
    valueOfCrypto = valueOfCrypto,
    valueOfCryptoFormatted = valueOfCrypto.replaceDotWithComma(),
    cryptoPriceOnStart = cryptoPriceOnStart,
    cryptoPriceOnEnd = cryptoPriceOnEnd,
    priceInOtherCurrencyOnStart = priceInOtherCurrencyOnStart,
    priceInOtherCurrencyOnStartFormatted = priceInOtherCurrencyOnStart.replaceDotWithComma(),
    priceInOtherCurrencyOnEnd = priceInOtherCurrencyOnEnd,
    currentCurrencyPrice = currentCurrencyPrice,
    currentCurrencyPriceFormatted = currentCurrencyPrice.replaceDotWithComma(),
    nameOfCurrency = nameOfCurrency,
    dateOfStart = dateOfStart,
    dateOfEnd = dateOfEnd,
    comment = comment,
    linkToImage = linkToImage,
    earnedMoney = earnedMoney,
    earnedMoneyFormatted = earnedMoney.replaceDotWithComma(),
    percents = percents,
    percentsFormatted = percents.replaceDotWithComma()
)

fun Crypto.toDto() = CryptoDto(
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
    percents = percents
)