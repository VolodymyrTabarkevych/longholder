package com.traday.longholder.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ReportDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("currencyCode") val currencyCode: String?,
    @SerializedName("profit") val profit: Double,
    @SerializedName("allMoney") val allMoney: Double,
    @SerializedName("countOfCrypto") val countOfCrypto: Double,
    @SerializedName("percents") val percents: Double,
    @SerializedName("priceNow") val priceNow: Double,
    @SerializedName("coinName") val coinName: String?,
    @SerializedName("cryptoMoney") val actives: List<ActiveDto>?,
    @SerializedName("dateOfReport") val dateOfReport: String
)