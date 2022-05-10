package com.traday.longholder.data.remote.requestbody

import com.google.gson.annotations.SerializedName

data class LoginRequestBody(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)