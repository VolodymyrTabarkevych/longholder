package com.traday.longholder.remote.requestbody

import com.google.gson.annotations.SerializedName

data class LoginRequestBody(
    @SerializedName("userName") val userName: String,
    @SerializedName("password") val password: String
)