package com.traday.longholder.data.remote.requestbody

import com.google.gson.annotations.SerializedName

data class UpdateActiveRequestBody(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("valueOfCrypto") val valueOfCrypto: Double,
    @SerializedName("currentCurrencyPrice") val currentCurrencyPrice: Double,
    @SerializedName("cryptoPriceOnStart") val cryptoPriceOnStart: Double,
    @SerializedName("dateOfEnd") val dateOfEnd: String,
    @SerializedName("comment") val comment: String?,
    @SerializedName("linkToImage") val linkToImage: String?,
    @SerializedName("symbol") val symbol: String
)