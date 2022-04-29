package com.traday.longholder.domain.model

data class Report(
    val id: Int,
    val name: String,
    val currencyCode: String,
    val profit: Double,
    val allMoney: Double,
    val countOfCrypto: Double,
    val percents: Double,
    val priceNow: Double,
    val coinName: String,
    val cryptoMoney: Crypto,
    val dateOfReport: String
)