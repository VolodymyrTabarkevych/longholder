package com.traday.longholder.data.remote.dto

import com.google.gson.annotations.SerializedName

data class NotificationDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("valueOfMessage") val valueOfMessage: String?,
    @SerializedName("earnedMoney") val earnedMoney: Double,
    @SerializedName("linkToTheImage") val linkToTheImage: String?,
    @SerializedName("dateOfSent") val dateOfSent: String,
    @SerializedName("dateOfStart") val dateOfStart: String
)