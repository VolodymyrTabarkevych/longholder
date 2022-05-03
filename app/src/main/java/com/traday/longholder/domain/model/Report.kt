package com.traday.longholder.domain.model

data class Report(
    val id: Int,
    val name: String?,
    val currencyCode: String?,
    val profit: Double,
    val profitFormatted: String,
    val allMoney: Double,
    val allMoneyFormatted: String,
    val countOfCrypto: Double,
    val countOfCryptoFormatted: String,
    val percents: Double,
    val priceNow: Double,
    val priceNowFormatted: String,
    val coinName: String?,
    val actives: List<Active>,
    val dateOfReport: String
)