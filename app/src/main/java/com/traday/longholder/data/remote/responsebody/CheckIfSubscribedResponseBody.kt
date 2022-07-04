package com.traday.longholder.data.remote.responsebody

import com.google.gson.annotations.SerializedName

data class CheckIfSubscribedResponseBody(
    @SerializedName("message") val message: String,
    @SerializedName("value") val value: Boolean,
    @SerializedName("typeOfSubscription") val typeOfSubscription: String
)