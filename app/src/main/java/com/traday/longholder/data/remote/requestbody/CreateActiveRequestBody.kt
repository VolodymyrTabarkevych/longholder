package com.traday.longholder.data.remote.requestbody

import com.google.gson.annotations.SerializedName

data class CreateActiveRequestBody(
    @SerializedName("name") val name: String?,
    @SerializedName("valueOfCrypto") val valueOfCrypto: Double,
    @SerializedName("wantedPercents") val wantedPercents: Double,
    @SerializedName("currentCurrencyPrice") val currentCurrencyPrice: Double,
    @SerializedName("cryptoPriceOnStart") val cryptoPriceOnStart: Double,
    @SerializedName("dateOfEnd") val dateOfEnd: String,
    @SerializedName("comment") val comment: String?,
    @SerializedName("linkToImage") val linkToImage: String?,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("IndexOnExchange") val indexOnExchange: String
)