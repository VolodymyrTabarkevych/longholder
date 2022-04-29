package com.traday.longholder.domain.model

data class Crypto(
    val id: Int,
    val name: String,
    val valueOfCrypto: Double,
    val cryptoPriceOnStart: Double,
    val cryptoPriceOnEnd: Double,
    val priceInOtherCurrencyOnStart: Double,
    val priceInOtherCurrencyOnEnd: Double,
    val currencyPrice: Double,
    val nameOfCurrency: String,
    val dateOfStart: String,
    val dateOfEnd: String,
    val comment: String
)