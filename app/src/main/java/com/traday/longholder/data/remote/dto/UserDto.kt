package com.traday.longholder.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("userName") val userName: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("currencyCode") val currencyCode: String?,
    @SerializedName("cryptos") val cryptos: List<CryptoDto>,
    @SerializedName("messages") val notifications: List<NotificationDto>,
    @SerializedName("reports") val reports: List<ReportDto>
)