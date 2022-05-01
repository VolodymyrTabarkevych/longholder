package com.traday.longholder.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CryptoDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("valueOfCrypto") val valueOfCrypto: Double,
    @SerializedName("cryptoPriceOnStart") val cryptoPriceOnStart: Double,
    @SerializedName("cryptoPriceOnEnd") val cryptoPriceOnEnd: Double,
    @SerializedName("priceInOtherCurrencyOnStart") val priceInOtherCurrencyOnStart: Double,
    @SerializedName("priceInOtherCurrencyOnEnd") val priceInOtherCurrencyOnEnd: Double,
    @SerializedName("currentCurrencyPrice") val currentCurrencyPrice: Double,
    @SerializedName("nameOfCurrency") val nameOfCurrency: String?,
    @SerializedName("dateOfStart") val dateOfStart: String,
    @SerializedName("dateOfEnd") val dateOfEnd: String,
    @SerializedName("comment") val comment: String?,
    @SerializedName("linkToImage") val linkToImage: String?,
    @SerializedName("earnedMoney") val earnedMoney: Double,
    @SerializedName("percents") val percents: Double
)