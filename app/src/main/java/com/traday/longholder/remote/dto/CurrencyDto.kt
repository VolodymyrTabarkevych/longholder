package com.traday.longholder.remote.dto

import com.google.gson.annotations.SerializedName

data class CurrencyDto(
    @SerializedName("id") val id: Int,
    @SerializedName("linkToPhoto") val linkToPhoto: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("indexOnExchange") val indexOnExchange: Int
)