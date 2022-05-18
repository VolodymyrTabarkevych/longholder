package com.traday.longholder.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CurrencyDto(
    @SerializedName("id") val id: Int,
    @SerializedName("linkToPhoto") val linkToPhoto: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("indexOnExchange") val indexOnExchange: String,
    @SerializedName("price") val price: Double,
    @SerializedName("dateOfUpdate") val dateOfUpdate: String,
    @SerializedName("symbol") val symbol: String
)