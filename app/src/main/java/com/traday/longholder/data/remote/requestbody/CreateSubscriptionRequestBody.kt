package com.traday.longholder.data.remote.requestbody

import com.google.gson.annotations.SerializedName

data class CreateSubscriptionRequestBody(
    @SerializedName("subscriptionId") val subscriptionId: String,
    @SerializedName("packageName") val packageName: String,
    @SerializedName("userToken") val userToken: String,
    @SerializedName("deviceName") val deviceName: String
)