package com.traday.longholder.data.mapper

import com.traday.longholder.data.local.entity.ActiveEntity
import com.traday.longholder.data.remote.dto.ActiveDto
import com.traday.longholder.domain.model.Active
import com.traday.longholder.extensions.formatDateServerFormatToClientFormatOrEmpty
import com.traday.longholder.extensions.replaceDotWithComma

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
    percents = percents
)

fun ActiveEntity.toDomain() = Active(
    id = id,
    name = name,
    nameFormatted = name?.replaceFirstChar(Char::titlecase),
    valueOfCrypto = valueOfCrypto,
    valueOfCryptoFormatted = valueOfCrypto.replaceDotWithComma(),
    cryptoPriceOnStart = cryptoPriceOnStart,
    cryptoPriceOnEnd = cryptoPriceOnEnd,
    priceInOtherCurrencyOnStart = priceInOtherCurrencyOnStart,
    priceInOtherCurrencyOnStartFormatted = priceInOtherCurrencyOnStart.replaceDotWithComma(),
    priceInOtherCurrencyOnEnd = priceInOtherCurrencyOnEnd,
    priceInOtherCurrencyOnEndFormatted = priceInOtherCurrencyOnEnd.replaceDotWithComma(),
    currentCurrencyPrice = currentCurrencyPrice,
    currentCurrencyPriceFormatted = currentCurrencyPrice.replaceDotWithComma(),
    currentCurrencyPriceSummaryFormatted = calculateCurrentCurrencyPriceSummary(
        valueOfCrypto = valueOfCrypto,
        currentCurrencyPrice = currentCurrencyPrice
    ).replaceDotWithComma(),
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

fun ActiveDto.toDomain() = Active(
    id = id,
    name = name,
    nameFormatted = name?.replaceFirstChar(Char::titlecase),
    valueOfCrypto = valueOfCrypto,
    valueOfCryptoFormatted = valueOfCrypto.replaceDotWithComma(),
    cryptoPriceOnStart = cryptoPriceOnStart,
    cryptoPriceOnEnd = cryptoPriceOnEnd,
    priceInOtherCurrencyOnStart = priceInOtherCurrencyOnStart,
    priceInOtherCurrencyOnStartFormatted = priceInOtherCurrencyOnStart.replaceDotWithComma(),
    priceInOtherCurrencyOnEnd = priceInOtherCurrencyOnEnd,
    priceInOtherCurrencyOnEndFormatted = priceInOtherCurrencyOnEnd.replaceDotWithComma(),
    currentCurrencyPrice = currentCurrencyPrice,
    currentCurrencyPriceFormatted = currentCurrencyPrice.replaceDotWithComma(),
    currentCurrencyPriceSummaryFormatted = calculateCurrentCurrencyPriceSummary(
        valueOfCrypto = valueOfCrypto,
        currentCurrencyPrice = currentCurrencyPrice
    ).replaceDotWithComma(),
    nameOfCurrency = nameOfCurrency,
    dateOfStart = dateOfStart.formatDateServerFormatToClientFormatOrEmpty(),
    dateOfEnd = dateOfEnd.formatDateServerFormatToClientFormatOrEmpty(),
    comment = comment,
    linkToImage = linkToImage,
    earnedMoney = earnedMoney,
    earnedMoneyFormatted = earnedMoney.replaceDotWithComma(),
    percents = percents,
    percentsFormatted = percents.replaceDotWithComma()
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
    dateOfStart = dateOfStart,
    dateOfEnd = dateOfEnd,
    comment = comment,
    linkToImage = linkToImage,
    earnedMoney = earnedMoney,
    percents = percents
)

private fun calculateCurrentCurrencyPriceSummary(
    valueOfCrypto: Double,
    currentCurrencyPrice: Double
): Double {
    return valueOfCrypto * currentCurrencyPrice
}