package com.traday.longholder.domain.model

data class Crypto(
    val id: Int,
    val name: String?,
    val valueOfCrypto: Double,
    val valueOfCryptoFormatted: String,
    val cryptoPriceOnStart: Double,
    val cryptoPriceOnEnd: Double,
    val priceInOtherCurrencyOnStart: Double,
    val priceInOtherCurrencyOnStartFormatted: String,
    val priceInOtherCurrencyOnEnd: Double,
    val currentCurrencyPrice: Double,
    val currentCurrencyPriceFormatted: String,
    val nameOfCurrency: String?,
    val dateOfStart: String,
    val dateOfEnd: String,
    val comment: String?,
    val linkToImage: String?,
    val earnedMoney: Double,
    val earnedMoneyFormatted: String,
    val percents: Double,
    val percentsFormatted: String
)